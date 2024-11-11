# Trading Bot Project Documentation

## Overview
A microservices-based trading bot focusing on technical analysis using Fourier transforms. The system processes market data, performs advanced technical analysis, and provides real-time visualization through a React frontend with a system-aware dark mode dashboard.

## Current Implementation Status

### Completed Components
- Basic project structure and module setup
- Maven configuration
- TimeSeriesAnalysis implementation
- FourierTransformer service
- Core service communication framework
- Frontend with system-aware dark mode
- Basic chart components

### In Progress
- Frontend layout structure and components
- Theme system implementation
- Real-time data visualization
- Error handling and loading states

### Next Phase
- Interactive chart features
- WebSocket integration for real-time updates
- User preferences management
- Advanced technical indicators

### Future Plans
- Strategy implementation
- Order execution
- Risk management
- Backtesting framework
- Performance optimization

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Apache Commons Math 3.6.1
- WebSocket for real-time data
- Project Lombok
- Maven

### Frontend
- React 18.2.0
- TypeScript 5.3.3
- Vite 5.0.10
- Recharts 2.10.3
- Tailwind CSS 3.3.6
- Lucide React 0.263.1
- Shadcn/ui Components
- ESLint 8.56.0
- Node.js LTS

## Project Structure
```
monitoring-frontend/    # React frontend
├── src/
│   ├── components/
│   │   ├── layout/           # New: Layout components
│   │   │   ├── Header.tsx
│   │   │   ├── Sidebar.tsx
│   │   │   └── Footer.tsx
│   │   ├── charts/           # New: Chart components
│   │   │   ├── TimeSeriesChart.tsx
│   │   │   └── FourierChart.tsx
│   │   ├── ui/              # Shadcn components
│   │   │   ├── button.tsx
│   │   │   └── card.tsx
│   │   └── theme/           # New: Theme components
│   │       └── ThemeToggle.tsx
│   ├── hooks/               # New: Custom hooks
│   │   ├── useTheme.ts
│   │   └── useWebSocket.ts
│   ├── types/               # New: TypeScript types
│   │   └── index.ts
│   ├── utils/              # Utility functions
│   │   ├── theme-utils.ts
│   │   └── data-utils.ts
│   ├── App.tsx
│   ├── main.tsx
│   └── index.css
```

## Immediate Development Priorities

### 1. Layout Structure
- Implement responsive layout with header, sidebar, and main content area
- Add navigation components
- Create consistent spacing and grid system

### 2. Theme System
- Complete system-aware dark mode implementation
- Add theme toggle component
- Implement theme persistence
- Ensure consistent styling across all components

### 3. Data Visualization
- Create reusable chart components
- Implement real-time data updates
- Add interactive features (zoom, pan, time range selection)
- Improve chart tooltips and legends

### 4. Error Handling & Loading States
- Add global error boundary
- Implement loading skeletons
- Add error states for charts
- Improve error messages and recovery

## Component Implementation Plan

### Layout Components
- Header with theme toggle and navigation
- Collapsible sidebar with analysis options
- Responsive main content area
- Footer with status information

### Chart Components
- Base chart wrapper with error handling
- Time series chart with real-time updates
- Fourier analysis visualization
- Technical indicator overlays

### Theme Components
- Theme provider with system preference detection
- Theme toggle with animations
- Theme persistence service
- Dark mode optimized components

## Testing Strategy
- Unit tests for utility functions
- Component testing with React Testing Library
- Integration tests for data flow
- End-to-end tests for critical paths

## Performance Considerations
- Implement React.memo for heavy components
- Optimize chart rerendering
- Use web workers for calculations
- Implement proper data pagination

## Known Issues and Solutions
1. Frontend Development Server CORS issues
   - Configure Vite proxy settings
   - Update Spring Boot CORS configuration
2. Chart Rendering
   - Optimize for performance
   - Implement proper resize handling
3. Type Definitions
   - Create comprehensive type system
   - Add proper error types

## Next Development Steps
1. Implement layout structure
2. Add theme toggle component
3. Create base chart components
4. Set up WebSocket connection
5. Add error boundaries
6. Implement loading states
7. Create chart interactions
8. Add data controls
9. Implement settings persistence
10. Add technical indicators

Would you like me to provide detailed implementations for any of these components or focus on a specific area first?