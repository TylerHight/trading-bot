// src/components/FourierAnalysisDashboard.tsx
import React, { useState, useEffect, useReducer } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Button } from "./ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card"
import { Download } from 'lucide-react';
import { useTheme } from '@/components/theme/theme-provider';
import { useTimeSeriesWebSocket } from '@/hooks/useTimeSeriesWebSocket';
import { ConnectionStatusBadge } from './status/ConnectionStatusBadge';
import { fetchFrequencyData } from '@/services/dataService';

interface TimeSeriesPoint {
  timestamp: string;
  value: number;
}

interface FrequencyPoint {
  frequency: string;
  magnitude: number;
}

const FourierAnalysisDashboard = () => {
  const { theme } = useTheme();
  // Updated to use data ingestion service WebSocket
  const { data: timeSeriesData, status, error: wsError } = useTimeSeriesWebSocket({
    url: 'ws://localhost:8082/api/v1/data/ws'
  });
  const [frequencyData, setFrequencyData] = useState<FrequencyPoint[]>([]);
  const [error, setError] = useState<string | null>(null);

  const getEffectiveTheme = () => {
    if (theme === 'system') {
      return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
    }
    return theme;
  };

  const themeColors = {
    text: getEffectiveTheme() === 'dark' ? 'rgba(255, 255, 255, 0.87)' : 'rgba(0, 0, 0, 0.87)',
    grid: getEffectiveTheme() === 'dark' ? 'rgba(255, 255, 255, 0.1)' : 'rgba(0, 0, 0, 0.1)',
    tooltip: {
      background: getEffectiveTheme() === 'dark' ? '#1a1a1a' : '#ffffff',
      border: getEffectiveTheme() === 'dark' ? 'rgba(255, 255, 255, 0.1)' : 'rgba(0, 0, 0, 0.1)',
    }
  };

  useEffect(() => {
    const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
    const handler = () => {
      forceUpdate();
    };
    
    mediaQuery.addListener(handler);
    return () => mediaQuery.removeListener(handler);
  }, []);

  // Updated to fetch frequency data from data ingestion service
  useEffect(() => {
    const getFrequencyData = async () => {
      try {
        const data = await fetchFrequencyData();
        setFrequencyData(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Failed to fetch frequency data');
      }
    };

    if (timeSeriesData.length > 0) {
      getFrequencyData();
    }
  }, [timeSeriesData]);

  const [, forceUpdate] = useReducer(x => x + 1, 0);

  const downloadData = () => {
    const data = {
      timeSeries: timeSeriesData,
      frequency: frequencyData
    };
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'fourier-analysis-data.json';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
  };

  const axisCommonProps = {
    tick: { 
      fill: themeColors.text,
      fontSize: 12
    },
    tickMargin: 10
  };

  const formatTimestamp = (timestamp: string) => {
    return new Date(timestamp).toLocaleTimeString();
  };

  return (
    <div className="min-h-screen bg-background text-foreground p-4">
      <div className="w-full max-w-7xl mx-auto space-y-6">
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
          <h1 className="text-2xl font-bold text-foreground">Fourier Analysis Dashboard</h1>
          <div className="flex items-center gap-4">
            <ConnectionStatusBadge status={status} />
            <Button
              onClick={downloadData}
              variant="outline"
              className="flex items-center gap-2 border-primary text-primary"
            >
              <Download className="h-4 w-4" />
              Download Data
            </Button>
          </div>
        </div>

        {(error || wsError) && (
          <div className="bg-destructive/20 border border-destructive text-destructive-foreground px-4 py-3 rounded">
            Error: {error || wsError}
          </div>
        )}

        <div className="grid md:grid-cols-2 gap-6">
          <Card className="bg-card border-border">
            <CardHeader>
              <CardTitle className="text-card-foreground">Time Series Data</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="h-[400px]">
                <ResponsiveContainer width="100%" height="100%">
                  <LineChart
                    data={timeSeriesData}
                    margin={{ top: 20, right: 30, left: 50, bottom: 85 }}
                    className="mt-4"
                  >
                    <CartesianGrid strokeDasharray="3 3" stroke={themeColors.grid} />
                    <XAxis 
                      dataKey="timestamp" 
                      height={60}
                      angle={-45}
                      textAnchor="end"
                      {...axisCommonProps}
                      tickFormatter={formatTimestamp}
                      label={{ 
                        value: 'Time', 
                        position: 'insideBottom', 
                        offset: -25,
                        fill: themeColors.text,
                        dy: 10
                      }}
                    />
                    <YAxis 
                      {...axisCommonProps}
                      label={{ 
                        value: 'Value', 
                        angle: -90, 
                        position: 'insideLeft',
                        offset: 0,
                        fill: themeColors.text
                      }}
                    />
                    <Tooltip 
                      contentStyle={{ 
                        backgroundColor: themeColors.tooltip.background,
                        border: `1px solid ${themeColors.tooltip.border}`,
                        color: themeColors.text
                      }}
                      labelStyle={{ color: themeColors.text }}
                      itemStyle={{ color: themeColors.text }}
                      labelFormatter={formatTimestamp}
                    />
                    <Legend 
                      verticalAlign="top"
                      height={36}
                      wrapperStyle={{ 
                        color: themeColors.text,
                        paddingTop: '10px'
                      }}
                    />
                    <Line 
                      type="monotone" 
                      dataKey="value" 
                      stroke="#8884d8" 
                      name="Signal"
                      dot={false}
                      isAnimationActive={false}
                    />
                  </LineChart>
                </ResponsiveContainer>
              </div>
            </CardContent>
          </Card>

          <Card className="bg-card border-border">
            <CardHeader>
              <CardTitle className="text-card-foreground">Frequency Domain</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="h-[400px]">
                <ResponsiveContainer width="100%" height="100%">
                  <LineChart
                    data={frequencyData}
                    margin={{ top: 20, right: 30, left: 50, bottom: 65 }}
                    className="mt-4"
                  >
                    <CartesianGrid strokeDasharray="3 3" stroke={themeColors.grid} />
                    <XAxis 
                      dataKey="frequency" 
                      height={60}
                      {...axisCommonProps}
                      label={{ 
                        value: 'Frequency (Hz)', 
                        position: 'insideBottom',
                        offset: -5,
                        fill: themeColors.text
                      }}
                    />
                    <YAxis 
                      {...axisCommonProps}
                      label={{ 
                        value: 'Magnitude', 
                        angle: -90, 
                        position: 'insideLeft',
                        offset: 0,
                        fill: themeColors.text
                      }}
                    />
                    <Tooltip 
                      contentStyle={{ 
                        backgroundColor: themeColors.tooltip.background,
                        border: `1px solid ${themeColors.tooltip.border}`,
                        color: themeColors.text
                      }}
                      labelStyle={{ color: themeColors.text }}
                      itemStyle={{ color: themeColors.text }}
                    />
                    <Legend 
                      verticalAlign="top"
                      height={36}
                      wrapperStyle={{ 
                        color: themeColors.text,
                        paddingTop: '10px'
                      }}
                    />
                    <Line 
                      type="monotone" 
                      dataKey="magnitude" 
                      stroke="#82ca9d" 
                      name="Magnitude"
                      dot={false}
                    />
                  </LineChart>
                </ResponsiveContainer>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default FourierAnalysisDashboard;