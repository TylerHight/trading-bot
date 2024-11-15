import { type Theme } from '@/components/theme/theme-provider';

export const getChartColors = (theme: Theme) => ({
  text: theme === 'dark' ? 'rgb(229, 231, 235)' : 'rgb(55, 65, 81)', // gray-200 : gray-700
  grid: theme === 'dark' ? 'rgb(55, 65, 81)' : 'rgb(229, 231, 235)', // gray-700 : gray-200
  tooltip: {
    background: theme === 'dark' ? 'rgb(31, 41, 55)' : 'rgb(255, 255, 255)', // gray-800 : white
    border: theme === 'dark' ? 'rgb(55, 65, 81)' : 'rgb(229, 231, 235)', // gray-700 : gray-200
  },
  primary: 'rgb(37, 99, 235)', // blue-600
  secondary: 'rgb(99, 102, 241)', // indigo-600
  success: 'rgb(34, 197, 94)', // green-600
  warning: 'rgb(234, 179, 8)', // yellow-600
  error: 'rgb(239, 68, 68)', // red-600
});