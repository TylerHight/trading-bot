import React, { useState, useEffect } from 'react';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
  ComposedChart,
  Bar
} from 'recharts';

const FourierTransformPlot = () => {
  const [timeSeriesData, setTimeSeriesData] = useState([]);
  const [frequencyData, setFrequencyData] = useState([]);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true);
        
        // Use the monitoring service endpoints
        const timeSeriesResponse = await fetch('http://localhost:8083/api/monitoring/timeseries');
        if (!timeSeriesResponse.ok) {
          throw new Error(`HTTP error! status: ${timeSeriesResponse.status}`);
        }
        const timeSeriesResult = await timeSeriesResponse.json();
        
        const frequencyResponse = await fetch('http://localhost:8083/api/monitoring/frequency');
        if (!frequencyResponse.ok) {
          throw new Error(`HTTP error! status: ${frequencyResponse.status}`);
        }
        const frequencyResult = await frequencyResponse.json();

        setTimeSeriesData(timeSeriesResult);
        setFrequencyData(frequencyResult);
        setError(null);
      } catch (err) {
        console.error('Error fetching data:', err);
        setError(err.message);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
    const interval = setInterval(fetchData, 5000);
    return () => clearInterval(interval);
  }, []);

  if (isLoading) {
    return <div className="loading-state">Loading...</div>;
  }

  if (error) {
    return <div className="error-state">Error: {error}</div>;
  }

  return (
    <div className="grid gap-6">
      <div className="p-4 bg-white rounded-lg shadow">
        <h2 className="chart-title">Time Series Data</h2>
        <div className="chart-container">
          <ResponsiveContainer>
            <LineChart data={timeSeriesData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="timestamp" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="value" stroke="#2563eb" name="Price" />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </div>

      <div className="p-4 bg-white rounded-lg shadow">
        <h2 className="chart-title">Frequency Domain</h2>
        <div className="chart-container">
          <ResponsiveContainer>
            <ComposedChart data={frequencyData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="frequency" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="magnitude" fill="#2563eb" name="Magnitude" barSize={5} />
              <Line type="monotone" dataKey="magnitude" stroke="#dc2626" dot={false} name="Trend" />
            </ComposedChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
};

export default FourierTransformPlot;