import React, { useState, useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

const FourierAnalysisDashboard: React.FC = () => {
  const [timeSeriesData, setTimeSeriesData] = useState<any[]>([]);
  const [frequencyData, setFrequencyData] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchData = async () => {
    setLoading(true);
    setError(null);
    try {
      const timeSeriesResponse = await fetch('http://localhost:8083/api/monitoring/timeseries');
      const frequencyResponse = await fetch('http://localhost:8083/api/monitoring/frequency');
      
      if (!timeSeriesResponse.ok || !frequencyResponse.ok) {
        throw new Error('Failed to fetch data');
      }

      const timeSeriesData = await timeSeriesResponse.json();
      const frequencyData = await frequencyResponse.json();

      setTimeSeriesData(timeSeriesData);
      setFrequencyData(frequencyData);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    const interval = setInterval(fetchData, 5000);
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="w-full max-w-7xl mx-auto p-4 space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">Fourier Analysis Dashboard</h1>
        <button
          onClick={fetchData}
          disabled={loading}
          className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 disabled:opacity-50"
        >
          {loading ? 'Loading...' : 'Refresh'}
        </button>
      </div>

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
          Error: {error}
        </div>
      )}

      <div className="grid md:grid-cols-2 gap-6">
        <div className="bg-white p-4 rounded-lg shadow">
          <h2 className="text-xl font-semibold mb-4">Time Series Data</h2>
          <div className="w-full h-[400px]">
            <LineChart
              width={500}
              height={400}
              data={timeSeriesData}
              margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
            >
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="timestamp" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="value" stroke="#8884d8" name="Time Series" />
            </LineChart>
          </div>
        </div>

        <div className="bg-white p-4 rounded-lg shadow">
          <h2 className="text-xl font-semibold mb-4">Frequency Domain</h2>
          <div className="w-full h-[400px]">
            <LineChart
              width={500}
              height={400}
              data={frequencyData}
              margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
            >
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="frequency" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="magnitude" stroke="#82ca9d" name="Frequency Magnitude" />
            </LineChart>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FourierAnalysisDashboard;