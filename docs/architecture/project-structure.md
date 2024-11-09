# docs/architecture/project-structure.md

# Project Structure

## Overview
This document details the organization and structure of the Trading Bot project. The project follows a microservices architecture with clear separation of concerns and modular design.

## Directory Structure
```
trading-bot/                      # Root project directory
├── docs/                        # Project-wide documentation
│   ├── architecture/           # Architecture documentation
│   │   ├── overview.md        # High-level architecture overview
│   │   ├── data-flow.md       # Data flow between services
│   │   ├── services.md        # Service descriptions
│   │   └── project-structure.md # This file
│   ├── api/                   # API documentation
│   │   ├── data-ingestion.md
│   │   ├── analysis.md
│   │   └── monitoring.md
│   └── setup/                 # Setup and deployment guides
│
├── shared-lib/                  # Shared library module
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── example/
│   │   │               └── shared/
│   │   │                   ├── dto/           # Data Transfer Objects
│   │   │                   ├── model/         # Common domain models
│   │   │                   └── util/          # Shared utilities
│   │   └── test/
│   └── pom.xml
│
├── analysis-service/            # Technical Analysis Service
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── analysis/
│   │   │   │               ├── AnalysisApplication.java
│   │   │   │               ├── controller/    # REST endpoints
│   │   │   │               │   └── FourierAnalysisController.java
│   │   │   │               ├── service/       # Business logic
│   │   │   │               │   ├── TimeSeriesAnalysis.java
│   │   │   │               │   └── FourierTransformer.java
│   │   │   │               └── model/         # Domain models
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   └── pom.xml
│
├── monitoring-service/          # Visualization Service
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── monitoring/
│   │   │   │               ├── MonitoringApplication.java
│   │   │   │               ├── controller/    # REST endpoints
│   │   │   │               ├── service/       # Business logic
│   │   │   │               └── client/        # Feign clients
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   ├── monitoring-frontend/    # React frontend
│   │   ├── src/
│   │   │   ├── components/
│   │   │   │   └── FourierTransformPlot.jsx
│   │   │   ├── App.jsx
│   │   │   └── main.jsx
│   │   ├── package.json
│   │   └── vite.config.js
│   └── pom.xml
│
├── strategy-service/           # Trading Strategy Service
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── example/
│   │       │           └── strategy/
│   │       │               └── StrategyApplication.java
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
│
├── execution-service/          # Order Execution Service
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── example/
│   │       │           └── execution/
│   │       │               └── ExecutionApplication.java
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
│
├── risk-service/              # Risk Management Service
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── example/
│   │       │           └── risk/
│   │       │               └── RiskApplication.java
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
│
├── data-ingestion-service/    # Market Data Ingestion Service
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── example/
│   │       │           └── dataingestion/
│   │       │               └── DataIngestionApplication.java
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
│
├── backtesting-service/       # Backtesting Service
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── example/
│   │       │           └── backtesting/
│   │       │               └── BacktestingApplication.java
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
│
├── .gitignore
└── pom.xml                    # Parent POM
```

## Key Configuration Files

### Parent pom.xml
- Defines common dependencies
- Manages versions
- Configures shared plugins
- Lists all modules

### Application Configuration (application.yml)
Each service should have:
```yaml
server:
  port: [unique-port]

spring:
  application:
    name: [service-name]

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

## Service Ports
- Eureka Server: 8761
- Analysis Service: 8082
- Monitoring Service: 8083
- Frontend: 3000
- Other services: Assign unique ports as needed

## Package Structure
Each service follows:
```
com.example.[service-name]/
├── controller/     # REST endpoints
├── service/        # Business logic
├── model/          # Domain models
├── config/         # Configuration classes
└── client/         # Service clients (if needed)
```

## Shared Library Structure
```
com.example.shared/
├── dto/            # Data Transfer Objects
├── model/          # Common domain models
└── util/           # Shared utilities
```

## Running the Services

### IntelliJ IDEA Community Edition
1. Create "Application" run configurations for each service
2. Set main class to [ServiceName]Application
3. Run in order: Analysis -> Monitoring
4. Use VSCode for frontend development

### Frontend Development (VSCode)
```bash
cd monitoring-service/monitoring-frontend
npm install
npm run dev
```

## Best Practices
1. Keep service-specific code in respective services
2. Use shared-lib for common code
3. Maintain consistent package structure
4. Document APIs and configurations
5. Follow microservice principles
    - Single responsibility
    - Independent deployability
    - Loose coupling

## Current Focus
- Fourier Transform implementation
- Service communication
- Frontend visualization
- Data flow between services