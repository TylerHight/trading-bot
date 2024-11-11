export interface TimeSeriesDataPoint {
  timestamp: string;
  value: number;
}

export interface TimeSeriesProps {
  data: TimeSeriesDataPoint[];
  onRefresh: () => void;
  onDownload: () => void;
  title?: string;
}

export interface CustomTickProps {
  x?: number;
  y?: number;
  payload?: {
    value: string;
  };
}