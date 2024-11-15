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
│   │   ├── useTimeSeriesWebSocket.ts  # 🔄 In Progress (connection issues)
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
│   │   └── websocket.ts    # 🔄 In Progress (needs configuration)
│   ├── config/            # Configuration
│   │   ├── chart-config.ts
│   │   └── websocket-config.ts  # 🔄 In Progress (needs update)
│   ├── App.tsx           
│   ├── main.tsx
│   └── index.css         
├── public/
├── tests/
│   ├── unit/
│   │   ├── components/
│   │   └── hooks/
│   └── integration/
├── .env                   # Needs WebSocket configuration
├── .env.development
├── .env.production
├── vite.config.ts         # Updated with path aliases
├── tailwind.config.js     # Updated with shadcn/ui colors
├── tsconfig.json          # Updated with path configurations
└── package.json          # Updated with new dependencies

## Backend Structure
```
monitoring-service/       # Spring Boot backend
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/monitoring
│   │   │       ├── config/
│   │   │       │   └── WebSocketConfig.java        # 🔄 In Progress (CORS issues)
│   │   │       ├── controller/
│   │   │       │   └── WebSocketController.java    # 📋 Planned
│   │   │       ├── dto/
│   │   │       │   └── TimeSeriesPoint.java        # ✓ Implemented
│   │   │       ├── service/
│   │   │       │   └── TimeSeriesGeneratorService.java # ✓ Implemented
│   │   │       └── visualization/
│   │   │           └── TimeSeriesWebSocketHandler.java # 🔄 In Progress
│   │   └── resources/
│   │       └── application.properties    # Needs WebSocket configuration
│   └── test/
└── pom.xml              # Updated with WebSocket dependencies
```

## Status Legend
✓ - Implemented and tested
🔄 - In Progress
📋 - Planned
❌ - Deprecated/Removed

## File Status Overview
Total Files: 52
- ✓ Implemented: 20
- 🔄 In Progress: 15
- 📋 Planned: 15
- ❌ Deprecated: 2

## Critical Files
These files require special attention during development:
1. useTimeSeriesWebSocket.ts - WebSocket connection issues
2. WebSocketConfig.java - CORS and endpoint configuration
3. TimeSeriesWebSocketHandler.java - WebSocket session management
4. ConnectionStatusBadge.tsx - WebSocket status visualization
5. TimeSeriesChart.tsx - Real-time data visualization
6. theme-provider.tsx - Theme management

## Recent Changes
1. Added WebSocket communication structure
2. Added connection status components
3. Updated TimeSeriesChart for real-time data
4. Added badge and alert components for status handling
5. Updated project configuration for WebSocket support

## Known Issues
1. WebSocket connection failing from frontend to backend
2. CORS configuration needs verification
3. Port and endpoint configuration needs review
4. Error handling needs improvement

[End of Project Structure Documentation]