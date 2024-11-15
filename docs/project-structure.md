# Trading Bot Project Structure Documentation
[Last Updated: 2024-11-15]

## LLM INSTRUCTIONS
When updating this document:
- Always maintain the full path structure
- Use consistent status indicators (✓, 🔄, 📋, ❌)
- Preserve comments about implementation status
- Keep the hierarchy and indentation consistent
- Update the File Status Overview counts
- Add dates to Recent Changes
- Include parent directories in file paths
- Use explicit TypeScript types in examples
- Note any security-sensitive files
- Keep Critical Files section updated
- Document any new dependencies
- Maintain alphabetical ordering within directories

## Frontend Structure
```
dashboard-service/    # React frontend with TypeScript
├── src/
│   ├── components/
│   │   ├── layout/           # Layout components
│   │   │   ├── Header.tsx
│   │   │   ├── Sidebar.tsx   # Navigation and controls
│   │   │   └── Footer.tsx
│   │   ├── charts/           # Chart components
│   │   │   ├── TimeSeriesChart.tsx    # ✓ Implemented - Real-time data visualization
│   │   │   ├── FourierChart.tsx       # ✓ Implemented - Frequency domain analysis
│   │   │   └── BaseChart.tsx          # ✓ Implemented - Base chart configuration
│   │   ├── ui/              # Shadcn components (v0.4.1)
│   │   │   ├── button.tsx   # Updated with icon support
│   │   │   ├── card.tsx     # Theme-aware container
│   │   │   ├── badge.tsx    # ✓ Implemented for WebSocket status
│   │   │   └── alert.tsx    # ✓ Implemented for error handling
│   │   ├── theme/           # Theme components
│   │   │   ├── theme-provider.tsx    # ✓ Implemented - System/Light/Dark
│   │   │   └── theme-toggle.tsx      # ✓ Implemented - Theme switching
│   │   ├── analysis/        # Analysis components
│   │   │   ├── TechnicalIndicators.tsx  # 🔄 In Progress - Moving to data-ingestion
│   │   │   └── SignalDisplay.tsx        # 📋 Planned
│   │   ├── status/          # Status components
│   │   │   └── ConnectionStatusBadge.tsx # ✓ Implemented - WebSocket status
│   │   └── common/          # Shared components
│   │       ├── LoadingSpinner.tsx      # ✓ Implemented
│   │       └── ErrorBoundary.tsx       # ✓ Implemented
│   ├── hooks/               # Custom hooks
│   │   ├── useTimeSeriesWebSocket.ts  # ✓ Implemented - Data ingestion connection
│   │   ├── useTheme.ts              # ✓ Implemented
│   │   └── useAnalysis.ts           # 🔄 In Progress
│   ├── types/               # TypeScript types
│   │   ├── index.ts         # Common types
│   │   ├── market.ts        # Market data types
│   │   └── websocket.ts     # ✓ Implemented WebSocket types
│   ├── utils/              # Utility functions
│   │   ├── theme-utils.ts   # ✓ Implemented
│   │   ├── data-utils.ts    # ✓ Implemented
│   │   └── analysis-utils.ts # 🔄 In Progress
│   ├── services/           # API services
│   │   └── websocket.ts    # ✓ Implemented - Data ingestion connection
│   ├── config/            # Configuration
│   │   ├── chart-config.ts  # ✓ Implemented
│   │   └── websocket-config.ts  # ✓ Implemented - Updated endpoints
│   ├── App.tsx           # ✓ Implemented
│   ├── main.tsx         # ✓ Implemented
│   └── index.css        # ✓ Implemented - Tailwind directives
├── public/
├── tests/
│   ├── unit/
│   │   ├── components/
│   │   └── hooks/
│   └── integration/
├── .env                   # ✓ Updated with data ingestion endpoints
├── .env.development      # Local development settings
├── .env.production       # Production settings
├── vite.config.ts        # ✓ Updated with WebSocket proxy
├── tailwind.config.js    # ✓ Updated with shadcn/ui colors
├── tsconfig.json         # ✓ Updated with path aliases
└── package.json         # ✓ Updated with WebSocket dependencies
```

## Status Legend
✓ - Implemented and tested
🔄 - In Progress
📋 - Planned
❌ - Deprecated/Removed

## File Status Overview
Total Files: 56
- ✓ Implemented: 32 (+4 since last update)
- 🔄 In Progress: 10 (-2 since last update)
- 📋 Planned: 14 (unchanged)
- ❌ Deprecated/Removed: 0

## Critical Files
These files require special attention during development:
1. useTimeSeriesWebSocket.ts - WebSocket connection to data-ingestion-service
2. TimeSeriesChart.tsx - Real-time data visualization
3. websocket-config.ts - Service endpoints and connection settings
4. .env files - Service configuration (review for security)

## Security-Sensitive Files
- .env.* - Environment configurations
- websocket-config.ts - Service endpoints
- package.json - Dependency versions
- vite.config.ts - Proxy configuration

## Recent Changes
1. [2024-11-15] Updated WebSocket connection to use data-ingestion-service
2. [2024-11-15] Migrated from monitoring-frontend to dashboard-service
3. [2024-11-15] Added WebSocket reconnection logic
4. [2024-11-15] Updated proxy configuration for data-ingestion-service

## Dependencies
Key frontend dependencies:
- react-use-websocket: ^4.5.0
- recharts: ^2.10.1
- shadcn/ui: ^0.4.1
- tailwindcss: ^3.3.5
- typescript: ^5.2.2
- vite: ^5.0.0

[End of Document]