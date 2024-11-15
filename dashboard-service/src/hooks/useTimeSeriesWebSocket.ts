// useTimeSeriesWebSocket.ts
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
    url = 'ws://localhost:8080/ws/timeseries',
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
        console.log('WebSocket connected successfully');
        setStatus('connected');
        setError(null);
        reconnectCount.current = 0;
        backoffTime.current = initialBackoff;
      };

      ws.current.onmessage = (event) => {
        try {
          const newPoint: TimeSeriesPoint = JSON.parse(event.data);
          setData(currentData => {
            const updatedData = [...currentData, newPoint];
            return updatedData.slice(-maxPoints);
          });
        } catch (e) {
          console.error('Error parsing WebSocket message:', e);
        }
      };

      ws.current.onerror = (event) => {
        console.error('WebSocket error:', event);
        setStatus('error');
        setError('WebSocket connection error');
      };

      ws.current.onclose = (event) => {
        console.log(`WebSocket connection closed${event.wasClean ? ' cleanly' : ''}`);
        setStatus('disconnected');

        // Only attempt reconnection if it wasn't a clean close and we haven't exceeded attempts
        if (!event.wasClean && reconnectCount.current < reconnectAttempts) {
          const timeout = Math.min(backoffTime.current * Math.pow(2, reconnectCount.current), maxBackoff);
          console.log(`Scheduling reconnection in ${timeout}ms`);

          reconnectTimeout.current = setTimeout(() => {
            reconnectCount.current += 1;
            backoffTime.current = timeout;
            connect();
          }, timeout);
        } else if (reconnectCount.current >= reconnectAttempts) {
          setError(`Failed to connect after ${reconnectAttempts} attempts`);
          setStatus('error');
        }
      };
    } catch (err) {
      console.error('Error establishing WebSocket connection:', err);
      setError(err instanceof Error ? err.message : 'Failed to connect');
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