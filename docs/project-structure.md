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
│   │   │   └── card.tsx
│   │   ├── theme/           # Theme components
│   │   │   ├── theme-provider.tsx    # ✓ Implemented
│   │   │   └── theme-toggle.tsx      # ✓ Implemented
│   │   ├── analysis/        # Analysis components
│   │   │   ├── TechnicalIndicators.tsx  # 🔄 In Progress
│   │   │   └── SignalDisplay.tsx        # 📋 Planned
│   │   └── common/          # Shared components
│   │       ├── LoadingSpinner.tsx
│   │       └── ErrorBoundary.tsx
│   ├── hooks/               # Custom hooks
│   │   ├── useWebSocket.ts          # ✓ Implemented
│   │   ├── useTheme.ts             # ✓ Implemented
│   │   └── useAnalysis.ts          # 🔄 In Progress
│   ├── types/               # TypeScript types
│   │   ├── index.ts         # Common types
│   │   ├── market.ts        # Market data types
│   │   └── analysis.ts      # Analysis types
│   ├── utils/              # Utility functions
│   │   ├── theme-utils.ts
│   │   ├── data-utils.ts
│   │   └── analysis-utils.ts
│   ├── services/           # API services
│   │   ├── websocket.ts
│   │   ├── market-data.ts
│   │   └── analysis.ts
│   ├── config/            # Configuration
│   │   ├── chart-config.ts
│   │   └── websocket-config.ts
│   ├── App.tsx           
│   ├── main.tsx
│   └── index.css         
├── public/
├── tests/
│   ├── unit/
│   │   ├── components/
│   │   └── hooks/
│   └── integration/
├── .env
├── .env.development
├── .env.production
├── vite.config.ts
├── tailwind.config.js
├── tsconfig.json
└── package.json
```

## Backend Structure
```
trading-bot/
├── market-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/tradingbot/market
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── model/
│   │   │   │       └── config/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── analysis-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/tradingbot/analysis
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── model/
│   │   │   │       └── config/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── common/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/tradingbot/common
│   │   │   │       ├── model/
│   │   │   │       ├── util/
│   │   │   │       └── config/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
└── pom.xml
```

## Status Legend
✓ - Implemented and tested
🔄 - In Progress
📋 - Planned
❌ - Deprecated/Removed

## File Status Overview
Total Files: 47
- ✓ Implemented: 18
- 🔄 In Progress: 12
- 📋 Planned: 15
- ❌ Deprecated: 2

## Critical Files
These files require special attention during development:
1. TimeSeriesChart.tsx - Core visualization component
2. theme-provider.tsx - Theme management
3. useWebSocket.ts - Real-time data handling
4. analysis-utils.ts - Technical analysis calculations

## Recent Changes
1. Added BaseChart.tsx component
2. Updated theme handling in TimeSeriesChart.tsx
3. Implemented WebSocket hook
4. Fixed chart label spacing issues

[End of Project Structure Documentation]