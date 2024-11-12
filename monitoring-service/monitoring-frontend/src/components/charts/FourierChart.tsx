import React from 'react';
import { useTheme } from '@/components/theme/theme-provider';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';
import { cn } from '@/lib/utils';

interface FourierChartProps {
  data: any[];
  className?: string;
}

export function FourierChart({ data, className }: FourierChartProps) {
  const { theme } = useTheme();
  
  const themeColors = {
    text: theme === 'dark' ? 'rgb(229, 231, 235)' : 'rgb(55, 65, 81)',
    grid: theme === 'dark' ? 'rgb(55, 65, 81)' : 'rgb(229, 231, 235)',
    tooltip: {
      background: theme === 'dark' ? 'rgb(31, 41, 55)' : 'rgb(255, 255, 255)',
      border: theme === 'dark' ? 'rgb(55, 65, 81)' : 'rgb(229, 231, 235)',
    }
  };

  return (
    <div className={cn("w-full h-[400px]", className)}>
      <ResponsiveContainer width="100%" height="100%">
        <LineChart
          data={data}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid 
            strokeDasharray="3 3" 
            stroke={themeColors.grid} 
            opacity={0.5}
          />
          <XAxis
            dataKey="frequency"
            stroke={themeColors.text}
            tick={{ fill: themeColors.text }}
            label={{ 
              value: "Frequency", 
              position: "bottom",
              fill: themeColors.text,
              offset: -5
            }}
          />
          <YAxis
            stroke={themeColors.text}
            tick={{ fill: themeColors.text }}
            label={{ 
              value: "Amplitude", 
              angle: -90, 
              position: "insideLeft",
              fill: themeColors.text,
              offset: -5
            }}
          />
          <Tooltip
            contentStyle={{
              backgroundColor: themeColors.tooltip.background,
              border: `1px solid ${themeColors.tooltip.border}`,
              borderRadius: '6px',
              color: themeColors.text,
            }}
            labelStyle={{ color: themeColors.text }}
            itemStyle={{ color: themeColors.text }}
            cursor={{ stroke: themeColors.grid }}
          />
          <Legend
            wrapperStyle={{
              color: themeColors.text,
            }}
            formatter={(value) => (
              <span style={{ color: themeColors.text }}>{value}</span>
            )}
          />
          <Line
            name="Amplitude"
            type="monotone"
            dataKey="amplitude"
            stroke="rgb(37, 99, 235)"
            strokeWidth={2}
            dot={false}
            activeDot={{ r: 4 }}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}