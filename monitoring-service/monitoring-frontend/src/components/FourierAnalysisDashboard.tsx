import React, { useState, useEffect, useReducer } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Button } from "./ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card"
import { RefreshCw, Download } from 'lucide-react';
import { useTheme } from '@/components/theme/theme-provider';

interface TimeSeriesPoint {
  timestamp: string;
  value: number;
}

interface FrequencyPoint {
  frequency: string;
  magnitude: number;
}

// Sample data generator
const generateSampleData = () => {
  const timeSeriesData: TimeSeriesPoint[] = [];
  const frequencyData: FrequencyPoint[] = [];
  
  // Generate time series data
  for (let i = 0; i < 100; i++) {
    const time = new Date(2024, 0, 1, 0, i);
    const value = 
      50 + // baseline
      30 * Math.sin(i * 0.1) + // main wave
      10 * Math.sin(i * 0.5) + // secondary wave
      5 * (Math.random() - 0.5); // noise
    
    timeSeriesData.push({
      timestamp: time.toLocaleTimeString(),
      value: value
    });
  }

  // Generate frequency domain data
  for (let freq = 0; freq < 2; freq += 0.05) {
    frequencyData.push({
      frequency: freq.toFixed(2),
      magnitude: 50 * Math.exp(-Math.pow(freq - 0.2, 2) / 0.1) +
                 30 * Math.exp(-Math.pow(freq - 0.8, 2) / 0.2)
    });
  }

  return { timeSeriesData, frequencyData };
};

const FourierAnalysisDashboard = () => {
  const { theme } = useTheme();
  const [timeSeriesData, setTimeSeriesData] = useState<TimeSeriesPoint[]>([]);
  const [frequencyData, setFrequencyData] = useState<FrequencyPoint[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // Get the actual theme considering system preference
  const getEffectiveTheme = () => {
    if (theme === 'system') {
      return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
    }
    return theme;
  };

  // Theme-aware colors
  const themeColors = {
    text: getEffectiveTheme() === 'dark' ? 'rgba(255, 255, 255, 0.87)' : 'rgba(0, 0, 0, 0.87)',
    grid: getEffectiveTheme() === 'dark' ? 'rgba(255, 255, 255, 0.1)' : 'rgba(0, 0, 0, 0.1)',
    tooltip: {
      background: getEffectiveTheme() === 'dark' ? '#1a1a1a' : '#ffffff',
      border: getEffectiveTheme() === 'dark' ? 'rgba(255, 255, 255, 0.1)' : 'rgba(0, 0, 0, 0.1)',
    }
  };

  const refreshData = () => {
    setLoading(true);
    setError(null);
    try {
      const { timeSeriesData, frequencyData } = generateSampleData();
      setTimeSeriesData(timeSeriesData);
      setFrequencyData(frequencyData);
    } catch (err) {
      setError('Failed to generate data');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    refreshData();
    // Refresh every 5 seconds
    const interval = setInterval(refreshData, 5000);
    return () => clearInterval(interval);
  }, []);

  // Listen for system theme changes
  useEffect(() => {
    const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
    const handler = () => {
      // Force a re-render when system theme changes
      forceUpdate();
    };
    
    mediaQuery.addListener(handler);
    return () => mediaQuery.removeListener(handler);
  }, []);

  // Force update helper
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

  const chartCommonProps = {
    margin: { top: 20, right: 30, left: 50, bottom: 85 }, // Increased bottom margin
    className: "mt-4"
  };

  const axisCommonProps = {
    tick: { 
      fill: themeColors.text,
      fontSize: 12
    },
    tickMargin: 10
  };

  return (
    <div className="min-h-screen bg-background text-foreground p-4">
      <div className="w-full max-w-7xl mx-auto space-y-6">
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
          <h1 className="text-2xl font-bold text-foreground">Fourier Analysis Dashboard</h1>
          <div className="flex flex-wrap gap-2">
            <Button 
              onClick={refreshData} 
              disabled={loading}
              className="flex items-center gap-2 bg-primary text-primary-foreground"
            >
              <RefreshCw className={`h-4 w-4 ${loading ? 'animate-spin' : ''}`} />
              Refresh
            </Button>
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

        {error && (
          <div className="bg-destructive/20 border border-destructive text-destructive-foreground px-4 py-3 rounded">
            Error: {error}
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