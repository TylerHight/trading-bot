# Trading Bot Project Documentation

## Overview
A microservices-based trading bot focusing on technical analysis using Fourier transforms. The system processes market data, performs advanced technical analysis, and provides real-time visualization through a React frontend.

## Current Implementation Status

### Completed Components
- Basic project structure and module setup
- Maven configuration
- TimeSeriesAnalysis implementation
- FourierTransformer service
- Core service communication framework

### In Progress
- Service-to-service communication
- Frontend visualization
- Real-time data updates
- Fourier transform visualization

### Pending
- Strategy implementation
- Order execution
- Risk management
- Backtesting framework

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Apache Commons Math 3.6.1
- Project Lombok
- Maven

### Frontend
- React 18
- TypeScript 5
- Vite
- Recharts
- Tailwind CSS
- Lucide React

### Development Tools
- IntelliJ IDEA Community Edition (Backend)
- Visual Studio Code (Frontend)
- Git

## Project Structure
```
trading-bot/                      # Root project directory
├── docs/                        # Project documentation
│   ├── architecture/           
│   ├── api/                   
│   └── setup/                 
│
├── shared-lib/                  # Shared library module
│   ├── src/main/java/com/example/shared/
│   │   ├── dto/               
│   │   │   ├── TimeSeriesDto.java
│   │   │   └── FourierAnalysisDto.java
│   │   ├── model/            
│   │   └── util/              
│   └── pom.xml
│
├── analysis-service/            # Technical Analysis Service
│   ├── src/main/java/com/example/analysis/
│   │   ├── AnalysisApplication.java
│   │   ├── controller/    
│   │   │   └── FourierAnalysisController.java
│   │   ├── service/       
│   │   │   ├── TimeSeriesAnalysis.java
│   │   │   └── FourierTransformer.java
│   │   └── model/         
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── monitoring-service/          # Visualization Service
│   ├── src/main/java/com/example/monitoring/
│   │   ├── MonitoringApplication.java
│   │   ├── controller/    
│   │   ├── service/       
│   │   └── client/        
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── monitoring-frontend/    # React frontend
│   │   ├── src/
│   │   │   ├── components/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── button.tsx
│   │   │   │   │   └── card.tsx
│   │   │   │   └── FourierAnalysisDashboard.tsx
│   │   │   ├── App.tsx
│   │   │   └── main.tsx
│   │   ├── package.json
│   │   └── vite.config.ts
│   └── pom.xml
│
└── pom.xml                    # Parent POM
```

## Key Configurations

### Parent pom.xml Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>3.2.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        <version>2023.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-math3</artifactId>
        <version>3.6.1</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.30</version>
    </dependency>
</dependencies>
```

### Application Configuration (application.yml)
```yaml
# Analysis Service (port: 8082)
spring:
  application:
    name: analysis-service
  cloud:
    loadbalancer:
      ribbon:
        enabled: false
    discovery:
      enabled: true

server:
  port: 8082
  servlet:
    context-path: /api/v1

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
    fetch-registry: true
    register-with-eureka: true
  instance:
    prefer-ip-address: true
```

```yaml
# Monitoring Service (port: 8083)
spring:
  application:
    name: monitoring-service
  cloud:
    loadbalancer:
      ribbon:
        enabled: false
    discovery:
      enabled: true

server:
  port: 8083
  servlet:
    context-path: /api/v1

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
    fetch-registry: true
    register-with-eureka: true
  instance:
    prefer-ip-address: true
```

## Service Ports
- Eureka Server: 8761
- Analysis Service: 8082
- Monitoring Service: 8083
- Frontend: 3000 or 5173 (Vite default)

## Development Setup

### Backend Setup (IntelliJ IDEA)
1. Install JDK 17
2. Clone repository
3. Open project as Maven project
4. Enable auto-import for Maven
5. Run applications in order:
   - Eureka Server
   - Analysis Service
   - Monitoring Service

### Frontend Setup (VSCode)
1. Install Node.js (LTS version)
2. Navigate to frontend directory:
```bash
cd monitoring-service/monitoring-frontend
```
3. Install dependencies:
```bash
npm install
```
4. Start development server:
```bash
npm run dev
```

## API Endpoints

### Analysis Service
- `GET /api/v1/analysis/timeseries` - Get time series data
- `GET /api/v1/analysis/fourier` - Get Fourier transform analysis
- `GET /api/v1/analysis/sample` - Generate sample data

### Monitoring Service
- `GET /api/monitoring/timeseries` - Get formatted time series data
- `GET /api/monitoring/frequency` - Get frequency domain data

## Current Implementation Details

### TimeSeriesAnalysis Class
- Implements time series data storage
- Calculates moving averages (SMA, EMA)
- Integrates with FourierTransformer
- Provides data access methods

### FourierTransformer Class
- Implements FFT algorithm
- Provides frequency analysis
- Calculates magnitude spectrum
- Supports filtering operations

### Frontend Dashboard
- Real-time data visualization
- Interactive charts
- Time series and frequency domain views
- Auto-refresh functionality

## Best Practices
1. Service Independence: Each service should be self-contained
2. Shared Code: Use shared-lib for common functionality
3. API Documentation: Maintain updated API documentation
4. Testing: Include unit and integration tests
5. Error Handling: Implement proper error handling and logging

## Current Focus Areas
1. Completing service communication
2. Enhancing frontend visualization
3. Implementing real-time updates
4. Adding data filtering capabilities
5. Improving error handling

## Known Issues
1. Load balancer warnings in Spring Cloud
2. Frontend component library integration
3. Real-time data synchronization
4. Cross-origin resource sharing (CORS)

## Next Steps
1. Complete service communication
2. Implement DTOs in shared library
3. Enhance frontend visualization
4. Add real-time data updates
5. Implement error handling
6. Add monitoring dashboard

This documentation provides a comprehensive overview of the Trading Bot project's current state, structure, and implementation details. It serves as a starting point for understanding and working with any part of the system.