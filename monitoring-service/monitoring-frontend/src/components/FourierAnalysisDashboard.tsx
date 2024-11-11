import React, { useState, useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Button } from "./ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card"
import { RefreshCw, Download } from 'lucide-react';

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
  const [timeSeriesData, setTimeSeriesData] = useState<TimeSeriesPoint[]>([]);
  const [frequencyData, setFrequencyData] = useState<FrequencyPoint[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

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

  return (
    <div className="min-h-screen bg-background text-foreground p-4">
      <div className="w-full max-w-7xl mx-auto space-y-6">
        <div className="flex justify-between items-center">
          <h1 className="text-2xl font-bold text-foreground">Fourier Analysis Dashboard</h1>
          <div className="space-x-4">
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
                    margin={{ top: 5, right: 30, left: 20, bottom: 25 }}
                  >
                    <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.1)" />
                    <XAxis 
                      dataKey="timestamp" 
                      label={{ value: 'Time', position: 'bottom', fill: 'rgba(255,255,255,0.87)' }}
                      angle={-45}
                      textAnchor="end"
                      tick={{ fill: 'rgba(255,255,255,0.87)' }}
                    />
                    <YAxis 
                      label={{ 
                        value: 'Value', 
                        angle: -90, 
                        position: 'insideLeft',
                        fill: 'rgba(255,255,255,0.87)' 
                      }}
                      tick={{ fill: 'rgba(255,255,255,0.87)' }}
                    />
                    <Tooltip 
                      contentStyle={{ 
                        backgroundColor: '#1a1a1a',
                        border: '1px solid rgba(255,255,255,0.1)',
                      }}
                      labelStyle={{ color: 'rgba(255,255,255,0.87)' }}
                    />
                    <Legend wrapperStyle={{ color: 'rgba(255,255,255,0.87)' }} />
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
                    margin={{ top: 5, right: 30, left: 20, bottom: 25 }}
                  >
                    <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.1)" />
                    <XAxis 
                      dataKey="frequency" 
                      label={{ 
                        value: 'Frequency (Hz)', 
                        position: 'bottom',
                        fill: 'rgba(255,255,255,0.87)' 
                      }}
                      tick={{ fill: 'rgba(255,255,255,0.87)' }}
                    />
                    <YAxis 
                      label={{ 
                        value: 'Magnitude', 
                        angle: -90, 
                        position: 'insideLeft',
                        fill: 'rgba(255,255,255,0.87)' 
                      }}
                      tick={{ fill: 'rgba(255,255,255,0.87)' }}
                    />
                    <Tooltip 
                      contentStyle={{ 
                        backgroundColor: '#1a1a1a',
                        border: '1px solid rgba(255,255,255,0.1)',
                      }}
                      labelStyle={{ color: 'rgba(255,255,255,0.87)' }}
                    />
                    <Legend wrapperStyle={{ color: 'rgba(255,255,255,0.87)' }} />
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