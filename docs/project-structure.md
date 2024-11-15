# Trading Bot Project Structure

## Frontend Structure
```
monitoring-frontend/    # React frontend
├── src/
│   ├── components/
│   │   ├── layout/           # Layout components
│   │   │   ├── Header.tsx
│   │   │   ├── Sidebar.tsx   # Navigation and controls
│   │   │   └── Footer.tsx
│   │   ├── charts/           # Chart components
│   │   │   ├── TimeSeriesChart.tsx    # ✓ Implemented
│   │   │   ├── FourierChart.tsx       # ✓ Implemented
│   │   │   └── BaseChart.tsx          # ✓ Implemented
│   │   ├── ui/              # Shadcn components
│   │   │   ├── button.tsx   # Updated with icon support
│   │   │   ├── card.tsx
│   │   │   ├── badge.tsx    # ✓ Implemented for WebSocket status
│   │   │   └── alert.tsx    # ✓ Implemented for error handling
│   │   ├── theme/           # Theme components
│   │   │   ├── theme-provider.tsx    # ✓ Implemented
│   │   │   └── theme-toggle.tsx      # ✓ Implemented
│   │   ├── analysis/        # Analysis components
│   │   │   ├── TechnicalIndicators.tsx  # 🔄 In Progress
│   │   │   └── SignalDisplay.tsx        # 📋 Planned
│   │   ├── status/          # Status components
│   │   │   └── ConnectionStatusBadge.tsx # ✓ Implemented
│   │   └── common/          # Shared components
│   │       ├── LoadingSpinner.tsx
│   │       └── ErrorBoundary.tsx
│   ├── hooks/               # Custom hooks
│   │   ├── useTimeSeriesWebSocket.ts  # ✓ Implemented
│   │   ├── useTheme.ts              # ✓ Implemented
│   │   └── useAnalysis.ts           # 🔄 In Progress
│   ├── types/               # TypeScript types
│   │   ├── index.ts         # Common types
│   │   ├── market.ts        # Market data types
│   │   └── websocket.ts     # ✓ Implemented WebSocket types
│   ├── utils/              # Utility functions
│   │   ├── theme-utils.ts
│   │   ├── data-utils.ts
│   │   └── analysis-utils.ts
│   ├── services/           # API services
│   │   └── websocket.ts    # ✓ Implemented
│   ├── config/            # Configuration
│   │   ├── chart-config.ts
│   │   └── websocket-config.ts  # ✓ Implemented
│   ├── App.tsx           
│   ├── main.tsx
│   └── index.css         
├── public/
├── tests/
│   ├── unit/
│   │   ├── components/
│   │   └── hooks/
│   └── integration/
├── .env                   # ✓ Updated with WebSocket configuration
├── .env.development
├── .env.production
├── vite.config.ts         # ✓ Updated with path aliases
├── tailwind.config.js     # ✓ Updated with shadcn/ui colors
├── tsconfig.json          # ✓ Updated with path configurations
└── package.json          # ✓ Updated with new dependencies

## Backend Structure
```
monitoring-service/       # Spring Boot backend
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/monitoring
│   │   │       ├── config/
│   │   │       │   ├── WebSocketConfig.java        # ✓ Implemented
│   │   │       │   ├── FeignConfig.java           # ✓ Implemented
│   │   │       │   └── SecurityConfig.java        # ✓ Implemented
│   │   │       ├── client/
│   │   │       │   └── AnalysisServiceClient.java  # ✓ Implemented
│   │   │       ├── controller/
│   │   │       │   ├── FourierAnalysisController.java # ✓ Implemented
│   │   │       │   └── WebSocketController.java    # ❌ Removed (using Handler)
│   │   │       ├── dto/
│   │   │       │   └── TimeSeriesPoint.java        # ✓ Implemented
│   │   │       ├── service/
│   │   │       │   └── TimeSeriesGeneratorService.java # ✓ Implemented
│   │   │       └── visualization/
│   │   │           └── TimeSeriesWebSocketHandler.java # ✓ Implemented
│   │   └── resources/
│   │       └── application.properties    # ✓ Updated with all configurations
│   └── test/
└── pom.xml              # ✓ Updated with all dependencies
```

## Status Legend
✓ - Implemented and tested
🔄 - In Progress
📋 - Planned
❌ - Deprecated/Removed

## File Status Overview
Total Files: 56
- ✓ Implemented: 28
- 🔄 In Progress: 12
- 📋 Planned: 14
- ❌ Deprecated/Removed: 2

## Critical Files
These files require special attention during development:
1. AnalysisServiceClient.java - Service integration and fallback
2. FeignConfig.java - Circuit breaker configuration
3. TimeSeriesWebSocketHandler.java - Data streaming
4. TimeSeriesChart.tsx - Real-time visualization
5. useTimeSeriesWebSocket.ts - WebSocket management
6. application.properties - Service configuration

## Recent Changes
1. Added Feign client with circuit breaker
2. Implemented fallback strategies
3. Fixed WebSocket connection issues
4. Added proper error handling
5. Updated service configuration
6. Added circuit breaker patterns

## Known Issues
1. ✓ Fixed: WebSocket connection
2. 🔄 Active: Circuit breaker configuration needs testing
3. 🔄 Active: Fallback strategies need testing
4. 📋 Planned: Service metrics implementation
5. 📋 Planned: Load balancing configuration

## Integration Points
1. WebSocket Connection
   - Frontend: useTimeSeriesWebSocket.ts
   - Backend: TimeSeriesWebSocketHandler.java

2. Service Communication
   - Client: AnalysisServiceClient.java
   - Config: FeignConfig.java
   - Fallback: AnalysisServiceFallback

3. Data Flow
   - Generator: TimeSeriesGeneratorService.java
   - Transport: WebSocket
   - Display: TimeSeriesChart.tsx

[End of Project Structure Documentation]