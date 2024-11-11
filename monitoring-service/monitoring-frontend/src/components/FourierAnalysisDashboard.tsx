import React, { useState, useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Button } from "../components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "../components/ui/card"
import { RefreshCw, Download } from 'lucide-react';

const FourierAnalysisDashboard = () => {
  const [timeSeriesData, setTimeSeriesData] = useState([]);
  const [frequencyData, setFrequencyData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const generateSampleData = async () => {
    try {
      await fetch('http://localhost:8082/api/v1/analysis/sample');
      fetchData();
    } catch (err) {
      setError('Failed to generate sample data');
    }
  };

  const fetchData = async () => {
    setLoading(true);
    setError(null);
    try {
      // Fetch time series data
      const timeSeriesResponse = await fetch('http://localhost:8083/api/monitoring/timeseries');
      const frequencyResponse = await fetch('http://localhost:8083/api/monitoring/frequency');
      
      if (!timeSeriesResponse.ok || !frequencyResponse.ok) {
        throw new Error('Failed to fetch data');
      }

      const timeSeriesData = await timeSeriesResponse.json();
      const frequencyData = await frequencyResponse.json();

      // Format timestamps for display
      const formattedTimeSeriesData = timeSeriesData.map(point => ({
        ...point,
        timestamp: new Date(point.timestamp).toLocaleTimeString(),
      }));

      setTimeSeriesData(formattedTimeSeriesData);
      setFrequencyData(frequencyData);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    const interval = setInterval(fetchData, 5000);
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
    <div className="w-full max-w-7xl mx-auto p-4 space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">Fourier Analysis Dashboard</h1>
        <div className="space-x-4">
          <Button 
            onClick={generateSampleData} 
            disabled={loading}
            variant="outline"
          >
            Generate Sample Data
          </Button>
          <Button 
            onClick={fetchData} 
            disabled={loading}
            className="flex items-center gap-2"
          >
            <RefreshCw className={`h-4 w-4 ${loading ? 'animate-spin' : ''}`} />
            Refresh
          </Button>
          <Button
            onClick={downloadData}
            variant="outline"
            className="flex items-center gap-2"
          >
            <Download className="h-4 w-4" />
            Download Data
          </Button>
        </div>
      </div>

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
          Error: {error}
        </div>
      )}

      <div className="grid md:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle>Time Series Data</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-[400px]">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart
                  data={timeSeriesData}
                  margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
                >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis 
                    dataKey="timestamp" 
                    label={{ value: 'Time', position: 'bottom' }}
                    angle={-45}
                    textAnchor="end"
                  />
                  <YAxis label={{ value: 'Value', angle: -90, position: 'insideLeft' }} />
                  <Tooltip />
                  <Legend />
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

        <Card>
          <CardHeader>
            <CardTitle>Frequency Domain</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-[400px]">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart
                  data={frequencyData}
                  margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
                >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis 
                    dataKey="frequency" 
                    label={{ value: 'Frequency (Hz)', position: 'bottom' }}
                  />
                  <YAxis label={{ value: 'Magnitude', angle: -90, position: 'insideLeft' }} />
                  <Tooltip />
                  <Legend />
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
  );
};

export default FourierAnalysisDashboard;