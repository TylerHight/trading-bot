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
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';

const FourierTransformPlot = ({ symbol = 'AAPL' }) => {
  const [timeSeriesData, setTimeSeriesData] = useState([]);
  const [frequencyData, setFrequencyData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const [timeSeriesResponse, frequencyResponse] = await Promise.all([
          fetch(`/api/analysis/timeseries/${symbol}`),
          fetch(`/api/analysis/frequency/${symbol}`)
        ]);

        if (!timeSeriesResponse.ok || !frequencyResponse.ok) {
          throw new Error('Failed to fetch data');
        }

        const timeSeriesData = await timeSeriesResponse.json();
        const frequencyData = await frequencyResponse.json();

        setTimeSeriesData(timeSeriesData);
        setFrequencyData(frequencyData);
        setError(null);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
    // Set up polling interval
    const interval = setInterval(fetchData, 60000); // Update every minute

    return () => clearInterval(interval);
  }, [symbol]);

  if (loading) {
    return (
      <Card className="w-full max-w-4xl">
        <CardContent className="p-6">
          <div className="flex items-center justify-center h-96">
            <p>Loading...</p>
          </div>
        </CardContent>
      </Card>
    );
  }

  if (error) {
    return (
      <Card className="w-full max-w-4xl">
        <CardContent className="p-6">
          <div className="flex items-center justify-center h-96 text-red-500">
            <p>Error: {error}</p>
          </div>
        </CardContent>
      </Card>
    );
  }

  return (
    <Card className="w-full max-w-4xl">
      <CardHeader>
        <CardTitle>Fourier Transform Analysis - {symbol}</CardTitle>
      </CardHeader>
      <CardContent>
        <Tabs defaultValue="time" className="w-full">
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="time">Time Domain</TabsTrigger>
            <TabsTrigger value="frequency">Frequency Domain</TabsTrigger>
          </TabsList>

          <TabsContent value="time" className="space-y-4">
            <div className="h-96">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={timeSeriesData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis
                    dataKey="timestamp"
                    label={{ value: 'Time', position: 'bottom' }}
                  />
                  <YAxis
                    label={{ value: 'Price', angle: -90, position: 'left' }}
                  />
                  <Tooltip
                    formatter={(value) => [`$${value}`, 'Price']}
                    labelFormatter={(label) => `Time: ${label}`}
                  />
                  <Legend />
                  <Line
                    type="monotone"
                    dataKey="price"
                    stroke="#2563eb"
                    strokeWidth={2}
                    dot={{ r: 4 }}
                    activeDot={{ r: 6 }}
                    name="Price"
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </TabsContent>

          <TabsContent value="frequency" className="space-y-4">
            <div className="h-96">
              <ResponsiveContainer width="100%" height="100%">
                <ComposedChart data={frequencyData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis
                    dataKey="frequency"
                    label={{ value: 'Frequency (Hz)', position: 'bottom' }}
                  />
                  <YAxis
                    label={{ value: 'Magnitude', angle: -90, position: 'left' }}
                  />
                  <Tooltip
                    formatter={(value) => [value, 'Magnitude']}
                    labelFormatter={(label) => `Frequency: ${label} Hz`}
                  />
                  <Legend />
                  <Bar
                    dataKey="magnitude"
                    fill="#2563eb"
                    name="Magnitude"
                    barSize={20}
                  />
                  <Line
                    type="monotone"
                    dataKey="magnitude"
                    stroke="#dc2626"
                    strokeWidth={2}
                    dot={false}
                    name="Trend"
                  />
                </ComposedChart>
              </ResponsiveContainer>
            </div>
          </TabsContent>
        </Tabs>
      </CardContent>
    </Card>
  );
};

export default FourierTransformPlot;