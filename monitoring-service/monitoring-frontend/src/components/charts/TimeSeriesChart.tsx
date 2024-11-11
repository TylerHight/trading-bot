import React, { useRef, useEffect, useState } from 'react';
import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend
} from 'recharts';
import { RefreshCw, Download } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { TimeSeriesProps } from '@/types';

const CustomTick = (props: any) => {
  const { x, y, payload } = props;
  return (
    <g transform={`translate(${x},${y})`}>
      <text
        x={0}
        y={0}
        dy={16}
        textAnchor="end"
        fill="#666"
        transform="rotate(-45)"
        className="tick-text" // Add class for easier selection
      >
        {payload.value}
      </text>
    </g>
  );
};

export const TimeSeriesChart: React.FC<TimeSeriesProps> = ({
  data,
  onRefresh,
  onDownload,
  title = "Time Series Data"
}) => {
  const chartRef = useRef<HTMLDivElement>(null);
  const [dimensions, setDimensions] = useState({
    bottomMargin: 60,
    xAxisHeight: 60,
    labelOffset: 40
  });

  useEffect(() => {
    const calculateDimensions = () => {
      if (!chartRef.current) return;

      // Get all tick labels
      const tickLabels = chartRef.current.querySelectorAll('.tick-text');
      let maxTickHeight = 0;

      // Calculate the maximum rotated height of tick labels
      tickLabels.forEach((label) => {
        const bbox = (label as SVGGraphicsElement).getBBox();
        // For 45-degree rotation
        const rotatedHeight = Math.ceil(
          (bbox.width + bbox.height) * Math.sin(Math.PI / 4)
        );
        maxTickHeight = Math.max(maxTickHeight, rotatedHeight);
      });

      const basePadding = 20;
      const titleHeight = 20;
      const labelPadding = 30; // Space between ticks and axis label

      // Calculate dimensions
      const newDimensions = {
        xAxisHeight: maxTickHeight + basePadding,
        labelOffset: maxTickHeight + labelPadding,
        bottomMargin: maxTickHeight + titleHeight + labelPadding + basePadding
      };

      setDimensions(newDimensions);
    };

    // Initial calculation
    calculateDimensions();

    // Set up observers for resize and content changes
    const resizeObserver = new ResizeObserver(calculateDimensions);
    if (chartRef.current) {
      resizeObserver.observe(chartRef.current);
    }

    // Cleanup
    return () => resizeObserver.disconnect();
  }, [data]);

  return (
    <Card className="w-full">
      <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-4">
        <CardTitle className="text-xl font-bold">{title}</CardTitle>
        <div className="flex space-x-2">
          <Button
            variant="outline"
            size="sm"
            className="h-8"
            onClick={onRefresh}
          >
            <RefreshCw className="h-4 w-4 mr-2" />
            Refresh
          </Button>
          <Button
            variant="outline"
            size="sm"
            className="h-8"
            onClick={onDownload}
          >
            <Download className="h-4 w-4 mr-2" />
            Download
          </Button>
        </div>
      </CardHeader>
      <CardContent>
        <div ref={chartRef} className="h-[400px] w-full">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart
              data={data}
              margin={{
                top: 20,
                right: 30,
                left: 20,
                bottom: dimensions.bottomMargin
              }}
            >
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis
                dataKey="timestamp"
                height={dimensions.xAxisHeight}
                tick={<CustomTick />}
                tickMargin={10}
                label={{
                  value: 'Time',
                  position: 'insideBottom',
                  offset: dimensions.labelOffset,
                  style: { textAnchor: 'middle' }
                }}
              />
              <YAxis
                label={{
                  value: 'Value',
                  angle: -90,
                  position: 'insideLeft',
                  dx: -10
                }}
              />
              <Tooltip />
              <Legend
                verticalAlign="top"
                height={36}
                wrapperStyle={{
                  paddingTop: '10px'
                }}
              />
              <Line
                type="monotone"
                dataKey="value"
                stroke="#8884d8"
                dot={false}
              />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </CardContent>
    </Card>
  );
};

export default TimeSeriesChart;