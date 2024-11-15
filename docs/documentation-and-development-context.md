# Trading Bot Project: Development Context and Documentation
[Last Updated: 2024-11-14]

## ASSISTANT CONTEXT SECTION

Current Development Focus:
- Active Task: "Fixing WebSocket connection issues between frontend and backend"
- Last Change: "Implemented WebSocket communication structure"
- Next Task: "Implement error handling and reconnection logic"

Recent Code Changes (Last 3 conversations):
1. Added WebSocket hook and components (2024-11-14)
2. Added connection status indicator (2024-11-14)
3. Migrated from local data generation to WebSocket (2024-11-14)

Known Context Gaps:
- WebSocket connection failing - investigation needed
- Need to verify backend CORS configuration
- Need to confirm port numbers and WebSocket endpoints

## IMPLEMENTATION STATUS

### Currently Working Features ✓
- Theme system (light/dark/system)
- Base chart components
- Time series visualization
- Theme-aware tooltips
- UI components for connection status

### In Progress 🔄
- WebSocket real-time updates (currently failing)
- Connection error handling
- Error boundary implementation

### Planned Features 📋
- Advanced technical indicators
- Strategy implementation
- Order execution system

## TECHNICAL SPECIFICATIONS

### Core Data Types
```typescript
// Currently in use
interface TimeSeriesPoint {
  timestamp: string;
  value: number;
}

interface FrequencyPoint {
  frequency: string;
  magnitude: number;
}

interface ThemeContextType {
  theme: Theme;
  setTheme: (theme: Theme) => void;
}

type Theme = 'light' | 'dark' | 'system';

type WebSocketStatus = 'connecting' | 'connected' | 'disconnected' | 'error';

// Planned/In Progress
interface TechnicalIndicators {
  movingAverages: {
    sma: number[];
    ema: number[];
  };
  momentum: {
    rsi: number[];
    macd: {
      line: number[];
      signal: number[];
      histogram: number[];
    };
  };
}
```

### WebSocket Implementation Files
```
Frontend:
src/
├── hooks/
│   └── useTimeSeriesWebSocket.ts    # WebSocket connection management
├── components/
│   ├── status/
│   │   └── ConnectionStatusBadge.tsx # Connection status indicator
│   └── FourierAnalysisDashboard.tsx  # Main component using WebSocket

Backend:
src/main/java/com/example/monitoring/
├── config/
│   └── WebSocketConfig.java         # WebSocket endpoint configuration
├── dto/
│   └── TimeSeriesPoint.java         # Data transfer object
├── service/
│   └── TimeSeriesGeneratorService.java # Data generation
└── visualization/
    └── TimeSeriesWebSocketHandler.java # WebSocket session handling
```

### Current WebSocket Issues
1. Frontend unable to connect to backend WebSocket
2. Error displayed: "WebSocket connection error"
3. Possible causes:
    - CORS configuration
    - Port mismatch (Frontend: 5173, Backend: 8080)
    - Endpoint path mismatch
    - WebSocket handshake failing

### Required Configuration
```java
// Backend WebSocket Config
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(timeSeriesWebSocketHandler, "/ws/timeseries")
               .setAllowedOrigins("http://localhost:5173");
    }
}
```

```typescript
// Frontend WebSocket Hook
const useTimeSeriesWebSocket = (options: UseTimeSeriesWebSocketOptions = {}) => {
  const { 
    url = 'ws://localhost:8080/ws/timeseries'
  } = options;
  // ... implementation
};
```

## DEVELOPMENT NOTES

### Project Structure
```
monitoring-frontend/
├── src/
│   ├── components/
│   │   ├── charts/
│   │   │   ├── TimeSeriesChart.tsx     # ✓ Implemented
│   │   │   └── FourierChart.tsx        # ✓ Implemented
│   │   ├── status/
│   │   │   └── ConnectionStatusBadge.tsx # ✓ Implemented
│   │   └── theme/
│   │       ├── theme-provider.tsx      # ✓ Implemented
│   │       └── theme-toggle.tsx        # ✓ Implemented
│   └── hooks/
│       └── useTimeSeriesWebSocket.ts   # ✓ Implemented
```

### Current Dependencies
```json
{
  "dependencies": {
    "react": "18.2.0",
    "recharts": "2.10.3",
    "lucide-react": "0.263.1",
    "class-variance-authority": "^0.7.0",
    "clsx": "^2.0.0",
    "tailwind-merge": "^2.0.0"
  }
}
```

### Known Issues
1. ✓ Fixed: Chart text colors in system theme
2. ✓ Fixed: Time series X-axis label spacing
3. 🔄 Active: WebSocket connection failing
4. 🔄 Active: Connection error handling
5. 📋 Planned: Chart performance optimization

### Style Guidelines
- Using Tailwind CSS for styling
- Following shadcn/ui component patterns
- Theme-aware color system implementation

## CONVERSATION HISTORY NOTES

Last Conversation Summary:
- Date: 2024-11-14
- Major Decisions:
    - Implemented WebSocket communication structure
    - Added connection status indicator
    - Updated data flow from backend to frontend
- Changes Implemented:
    - Added WebSocket hook
    - Added connection status component
    - Updated chart components for real-time data
- Questions Resolved:
    - WebSocket implementation structure
    - Component organization
    - Data flow architecture

## VERSION CONTROL
Document Version: 1.1
Last Editor: Assistant
Last Edit Date: 2024-11-14

[End of Document]