import { useState, useRef, useCallback, useEffect } from 'react';

export interface TimeSeriesPoint {
  timestamp: string;
  value: number;
}

export type WebSocketStatus = 'connecting' | 'connected' | 'disconnected' | 'error';

interface UseTimeSeriesWebSocketOptions {
  maxPoints?: number;
  url?: string;
}

export const useTimeSeriesWebSocket = (options: UseTimeSeriesWebSocketOptions = {}) => {
  const { 
    maxPoints = 100,
    url = 'ws://localhost:8080/ws/timeseries'
  } = options;

  const [data, setData] = useState<TimeSeriesPoint[]>([]);
  const [status, setStatus] = useState<WebSocketStatus>('connecting');
  const [error, setError] = useState<string | null>(null);
  const ws = useRef<WebSocket | null>(null);
  const reconnectTimeout = useRef<NodeJS.Timeout>();

  const connect = useCallback(() => {
    try {
      ws.current = new WebSocket(url);

      ws.current.onopen = () => {
        setStatus('connected');
        setError(null);
        if (reconnectTimeout.current) {
          clearTimeout(reconnectTimeout.current);
        }
      };

      ws.current.onmessage = (event) => {
        const newPoint: TimeSeriesPoint = JSON.parse(event.data);
        setData(currentData => {
          const updatedData = [...currentData, newPoint];
          return updatedData.slice(-maxPoints);
        });
      };

      ws.current.onerror = () => {
        setStatus('error');
        setError('WebSocket connection error');
      };

      ws.current.onclose = () => {
        setStatus('disconnected');
        reconnectTimeout.current = setTimeout(() => {
          setStatus('connecting');
          connect();
        }, 3000);
      };
    } catch (error) {
      setStatus('error');
      setError('Failed to connect to WebSocket');
    }
  }, [url, maxPoints]);

  useEffect(() => {
    connect();

    return () => {
      if (ws.current) {
        ws.current.close();
      }
      if (reconnectTimeout.current) {
        clearTimeout(reconnectTimeout.current);
      }
    };
  }, [connect]);

  return {
    data,
    status,
    error
  };
};

export default useTimeSeriesWebSocket;