import React from 'react';
import { Badge } from "@/components/ui/badge";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Button } from "@/components/ui/button";
import { Loader2, RefreshCw } from 'lucide-react';
import type { WebSocketStatus } from "@/hooks/useTimeSeriesWebSocket";

interface ConnectionStatusBadgeProps {
  status: WebSocketStatus;
  onReconnect?: () => void;
  error?: string | null;
}

export const ConnectionStatusBadge: React.FC<ConnectionStatusBadgeProps> = ({ 
  status, 
  onReconnect,
  error 
}) => {
  switch (status) {
    case 'connecting':
      return (
        <Badge variant="outline" className="flex items-center gap-2">
          <Loader2 className="h-3 w-3 animate-spin" />
          Connecting to server...
        </Badge>
      );
    case 'connected':
      return (
        <Badge variant="outline" className="flex items-center gap-2 bg-green-500/15 text-green-600 dark:text-green-400">
          <div className="h-2 w-2 rounded-full bg-green-500 animate-pulse" />
          Connected
        </Badge>
      );
    case 'disconnected':
      return (
        <div className="flex items-center gap-2">
          <Badge variant="outline" className="flex items-center gap-2 bg-yellow-500/15 text-yellow-600 dark:text-yellow-400">
            <Loader2 className="h-3 w-3 animate-spin" />
            Reconnecting...
          </Badge>
          {onReconnect && (
            <Button 
              variant="outline" 
              size="sm" 
              onClick={onReconnect}
              className="flex items-center gap-2 hover:bg-yellow-500/10"
            >
              <RefreshCw className="h-3 w-3" />
              Force Reconnect
            </Button>
          )}
        </div>
      );
    case 'error':
      return (
        <Alert variant="destructive">
          <div className="flex items-center justify-between">
            <AlertDescription>
              {error || 'Connection error occurred'}
            </AlertDescription>
            {onReconnect && (
              <Button 
                variant="outline"
                size="sm" 
                onClick={onReconnect}
                className="flex items-center gap-2 border-red-600 hover:bg-red-500/10"
              >
                <RefreshCw className="h-3 w-3" />
                Try Again
              </Button>
            )}
          </div>
        </Alert>
      );
  }
};