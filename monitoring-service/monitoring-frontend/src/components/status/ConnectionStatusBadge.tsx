import React from 'react';
import { Badge } from "@/components/ui/badge";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Loader2 } from 'lucide-react';
import { WebSocketStatus } from "@/hooks/useTimeSeriesWebSocket";

interface ConnectionStatusBadgeProps {
  status: WebSocketStatus;
}

export const ConnectionStatusBadge: React.FC<ConnectionStatusBadgeProps> = ({ status }) => {
  switch (status) {
    case 'connecting':
      return (
        <Badge variant="outline" className="flex items-center gap-2">
          <Loader2 className="h-3 w-3 animate-spin" />
          Connecting
        </Badge>
      );
    case 'connected':
      return <Badge variant="success">Connected</Badge>;
    case 'disconnected':
      return <Badge variant="warning">Disconnected - Reconnecting...</Badge>;
    case 'error':
      return (
        <Alert variant="destructive">
          <AlertDescription>
            Connection error. Attempting to reconnect...
          </AlertDescription>
        </Alert>
      );
  }
};