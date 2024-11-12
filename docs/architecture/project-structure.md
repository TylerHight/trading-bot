# Trading Bot Project Documentation

## Overview
A microservices-based trading bot focusing on technical analysis using Fourier transforms. The system processes market data, performs advanced technical analysis, and provides real-time visualization through a React frontend with a comprehensive theme system and dark mode dashboard.

## Current Implementation Status

### Completed Components
- Basic project structure and module setup
- Maven configuration
- TimeSeriesAnalysis implementation
- FourierTransformer service
- Core service communication framework
- Frontend with enhanced theme system:
   - Manual light/dark mode toggle
   - System theme detection and sync
   - Theme persistence
   - Animated theme transitions
- Dark mode optimized chart components

### In Progress
- Frontend layout structure and components
- Real-time data visualization
- Error handling and loading states
- Chart responsiveness and interactions

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
- Recharts 2.10.3 with theme support
- Tailwind CSS 3.3.6
- Lucide React 0.263.1
- Shadcn/ui Components with dark mode
- ESLint 8.56.0
- Node.js LTS

## Project Structure
```
monitoring-frontend/    # React frontend
├── src/
│   ├── components/
│   │   ├── layout/           # Layout components
│   │   │   ├── Header.tsx
│   │   │   ├── Sidebar.tsx
│   │   │   └── Footer.tsx
│   │   ├── charts/           # Chart components
│   │   │   ├── TimeSeriesChart.tsx    # Theme-aware charts
│   │   │   └── FourierChart.tsx
│   │   ├── ui/              # Shadcn components
│   │   │   ├── button.tsx   # Updated with icon support
│   │   │   └── card.tsx
│   │   └── theme/           # Theme components
│   │       ├── theme-provider.tsx    # Enhanced theme provider
│   │       └── theme-toggle.tsx      # Animated theme toggle
│   ├── hooks/               # Custom hooks
│   │   └── useWebSocket.ts
│   ├── types/               # TypeScript types
│   │   └── index.ts         # Updated with theme types
│   ├── utils/              # Utility functions
│   │   ├── theme-utils.ts
│   │   └── data-utils.ts
│   ├── App.tsx             # Updated with ThemeProvider
│   ├── main.tsx
│   └── index.css           # Updated with theme variables
```

## Immediate Development Priorities

### 1. Layout Structure
- Implement responsive layout with header, sidebar, and main content area
- Add navigation components
- Create consistent spacing and grid system

### 2. Theme System (Completed)
- ✓ Three-way theme switching (Light/Dark/System)
- ✓ System theme detection and synchronization
- ✓ Theme preference persistence
- ✓ Ensure consistent styling across components
- ✓ Implement smooth theme transitions

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

## Theme System Implementation Details

### Theme Provider
- Context-based theme management
- Three-way theme switching (Light/Dark/System)
- System theme detection and synchronization
- Theme preference persistence in localStorage
- Automatic system theme updates
- Smooth theme transitions

### Theme Toggle Component
- Circular toggle button with icon transitions
- Three-state cycling (Light → Dark → System)
- Animated icon transitions
- Current theme indication:
   - Sun icon for light mode
   - Moon icon for dark mode
   - Laptop icon for system preference
- Accessible design with ARIA labels
- Smooth state transitions

### Theme Integration
- System theme detection and monitoring
- Real-time theme updates
- Persistent theme preferences
- CSS variable-based theming
- Dark mode optimized components
- Smooth theme transitions

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

### Theme Components (Completed)
- ✓ Theme provider with system preference detection
- ✓ Three-state theme toggle with animations
- ✓ Theme persistence service
- ✓ Dark mode optimized components

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
4. Theme System (Resolved)
   - ✓ Implemented system theme detection
   - ✓ Added smooth icon transitions
   - ✓ Fixed theme persistence
   - ✓ Resolved icon visibility issues
   - ✓ Optimized theme switching performance

## Development Progress

### Completed
1. Theme System
   - Three-way theme toggle (Light/Dark/System)
   - System theme synchronization
   - Theme persistence
   - Animated theme transitions
2. Chart Components
   - Theme-aware styling
   - Dark mode optimization
   - Responsive design
3. Component Styling
   - Enhanced button components
   - Icon animations
   - Consistent theming

## Next Development Steps
1. Set up WebSocket connection
2. Add error boundaries
3. Implement loading states
4. Create chart interactions
5. Add data controls
6. Implement settings persistence
7. Add technical indicators
8. Enhance accessibility
9. Add more theme-aware components

## Recent Improvements
1. Theme System
   - Three-way theme switching
   - System theme synchronization
   - Animated theme transitions
   - Improved icon visibility
2. Chart Components
   - Enhanced theme awareness
   - Better dark mode support
3. Component Styling
   - Improved button animations
   - Better icon transitions
   - More consistent theming