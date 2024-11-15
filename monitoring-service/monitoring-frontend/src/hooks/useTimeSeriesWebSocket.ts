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

  useEffect(() => {
    console.log('Attempting to connect to WebSocket:', url);
    
    ws.current = new WebSocket(url);

    ws.current.onopen = () => {
      console.log('WebSocket connected');
      setStatus('connected');
      setError(null);
    };

    ws.current.onmessage = (event) => {
      try {
        const newPoint: TimeSeriesPoint = JSON.parse(event.data);
        console.log('Received data:', newPoint);
        setData(currentData => {
          const updatedData = [...currentData, newPoint];
          return updatedData.slice(-maxPoints);
        });
      } catch (e) {
        console.error('Error parsing WebSocket message:', e);
      }
    };

    ws.current.onerror = (error) => {
      console.error('WebSocket error:', error);
      setStatus('error');
      setError('WebSocket connection error');
    };

    ws.current.onclose = () => {
      console.log('WebSocket connection closed');
      setStatus('disconnected');
    };

    return () => {
      if (ws.current) {
        console.log('Cleaning up WebSocket connection');
        ws.current.close();
      }
    };
  }, [url, maxPoints]);

  return {
    data,
    status,
    error
  };
};