// src/hooks/useTimeSeriesWebSocket.ts
import { useState, useRef, useCallback, useEffect } from 'react';

export interface TimeSeriesPoint {
  timestamp: string;
  value: number;
}

export type WebSocketStatus = 'connecting' | 'connected' | 'disconnected' | 'error';

interface UseTimeSeriesWebSocketOptions {
  maxPoints?: number;
  url?: string;
  reconnectAttempts?: number;
  initialBackoff?: number;
  maxBackoff?: number;
}

export const useTimeSeriesWebSocket = (options: UseTimeSeriesWebSocketOptions = {}) => {
  const { 
    maxPoints = 100,
    url = '/api/v1/data/ws',  // Updated to match backend endpoint
    reconnectAttempts = 5,
    initialBackoff = 1000,
    maxBackoff = 30000
  } = options;

  const [data, setData] = useState<TimeSeriesPoint[]>([]);
  const [status, setStatus] = useState<WebSocketStatus>('connecting');
  const [error, setError] = useState<string | null>(null);
  
  const ws = useRef<WebSocket | null>(null);
  const reconnectCount = useRef(0);
  const backoffTime = useRef(initialBackoff);
  const reconnectTimeout = useRef<NodeJS.Timeout>();

  const connect = useCallback(() => {
    try {
      if (ws.current?.readyState === WebSocket.OPEN) {
        return;
      }

      console.log(`Attempting to connect to WebSocket: ${url} (Attempt ${reconnectCount.current + 1})`);
      setStatus('connecting');

      ws.current = new WebSocket(url);

      ws.current.onopen = () => {
        console.log('WebSocket connected successfully to data ingestion service');
        setStatus('connected');
        setError(null);
        reconnectCount.current = 0;
        backoffTime.current = initialBackoff;
      };

      ws.current.onmessage = (event) => {
        try {
          const newPoint: TimeSeriesPoint = JSON.parse(event.data);
          // Validate the data structure matches TimeSeriesPoint
          if ('timestamp' in newPoint && 'value' in newPoint) {
            setData(currentData => {
              const updatedData = [...currentData, newPoint];
              return updatedData.slice(-maxPoints);
            });
          } else {
            console.warn('Received malformed data from WebSocket:', newPoint);
          }
        } catch (e) {
          console.error('Error parsing WebSocket message:', e);
          setError('Error processing data from server');
        }
      };

      ws.current.onerror = (event) => {
        console.error('WebSocket error with data ingestion service:', event);
        setStatus('error');
        setError('Connection error with data ingestion service');
      };

      ws.current.onclose = (event) => {
        console.log(`Data ingestion WebSocket connection closed${event.wasClean ? ' cleanly' : ''}`);
        setStatus('disconnected');

        // Only attempt reconnection if it wasn't a clean close and we haven't exceeded attempts
        if (!event.wasClean && reconnectCount.current < reconnectAttempts) {
          const timeout = Math.min(backoffTime.current * Math.pow(2, reconnectCount.current), maxBackoff);
          console.log(`Scheduling reconnection to data ingestion service in ${timeout}ms`);

          reconnectTimeout.current = setTimeout(() => {
            reconnectCount.current += 1;
            backoffTime.current = timeout;
            connect();
          }, timeout);
        } else if (reconnectCount.current >= reconnectAttempts) {
          setError(`Failed to connect to data ingestion service after ${reconnectAttempts} attempts`);
          setStatus('error');
        }
      };
    } catch (err) {
      console.error('Error establishing connection to data ingestion service:', err);
      setError(err instanceof Error ? err.message : 'Failed to connect to data service');
      setStatus('error');
    }
  }, [url, maxPoints, reconnectAttempts, initialBackoff, maxBackoff]);

  const disconnect = useCallback(() => {
    if (reconnectTimeout.current) {
      clearTimeout(reconnectTimeout.current);
    }
    
    if (ws.current) {
      ws.current.close();
      ws.current = null;
    }
  }, []);

  const reconnect = useCallback(() => {
    disconnect();
    reconnectCount.current = 0;
    backoffTime.current = initialBackoff;
    connect();
  }, [disconnect, connect, initialBackoff]);

  useEffect(() => {
    connect();
    return () => {
      disconnect();
    };
  }, [connect, disconnect]);

  return {
    data,
    status,
    error,
    reconnect
  };
};