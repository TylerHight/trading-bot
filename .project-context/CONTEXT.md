# Trading Bot - Full Project Context

## Project Overview
Converting a monolithic trading bot into a microservices architecture using Spring Boot. The system analyzes market data, performs technical analysis using Fourier transforms, and implements trading strategies.

## Complete Project Structure
```
trading-bot/                      # Root project directory
├── .project-context/            # Project context for continuity
│   ├── CONTEXT.md              # This file
│   └── README.md               # Context usage guide
│
├── docs/                        # Project-wide documentation
│   ├── architecture/
│   │   ├── overview.md         # System architecture
│   │   ├── data-flow.md        # Service interactions
│   │   ├── services.md         # Service descriptions
│   │   └── project-structure.md # Directory structure
│   ├── api/                    # API documentation
│   │   ├── data-ingestion.md   # Data service API
│   │   ├── analysis.md         # Analysis service API
│   │   └── monitoring.md       # Monitoring service API
│   ├── setup/                  # Setup guides
│   │   ├── installation.md     # Installation steps
│   │   └── configuration.md    # Configuration guide
│   └── README.md               # Documentation overview
│
├── shared-lib/                  # Shared library module
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── example/
│   │   │               └── shared/
│   │   │                   ├── model/       # Shared data models
│   │   │                   └── util/        # Shared utilities
│   │   └── test/
│   ├── pom.xml
│   └── README.md
│
├── data-ingestion-service/      # Market data ingestion
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── dataingestion/
│   │   │   │               ├── DataIngestionApplication.java
│   │   │   │               ├── controller/  # REST endpoints
│   │   │   │               ├── service/     # Business logic
│   │   │   │               ├── repository/  # Data access
│   │   │   │               └── model/       # Service models
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   ├── docs/                   # Service documentation
│   │   ├── api.md
│   │   └── configuration.md
│   ├── pom.xml
│   └── README.md
│
├── analysis-service/            # Technical analysis
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── analysis/
│   │   │   │               ├── AnalysisApplication.java
│   │   │   │               ├── controller/
│   │   │   │               │   └── FourierAnalysisController.java
│   │   │   │               ├── model/
│   │   │   │               │   └── TimeSeriesData.java
│   │   │   │               └── service/
│   │   │   │                   ├── preprocessing/
│   │   │   │                   │   └── FourierTransformer.java
│   │   │   │                   ├── FourierTransformerService.java
│   │   │   │                   ├── TimeSeriesAnalysis.java
│   │   │   │                   └── TimeSeriesAnalysisService.java
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   │       └── java/
│   │           └── com/
│   │               └── example/
│   │                   └── analysis/
│   │                       └── service/
│   │                           ├── FourierTransformerServiceTest.java
│   │                           ├── TimeSeriesAnalysisTest.java
│   │                           └── TimeSeriesAnalysisServiceTest.java
│   ├── docs/
│   │   ├── api.md
│   │   └── fourier-analysis.md
│   ├── pom.xml
│   └── README.md
│
├── monitoring-service/          # Visualization & monitoring
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── monitoring/
│   │   │   │               ├── MonitoringApplication.java
│   │   │   │               ├── client/
│   │   │   │               │   └── AnalysisServiceClient.java
│   │   │   │               ├── config/
│   │   │   │               │   └── FeignConfig.java
│   │   │   │               └── visualization/
│   │   │   │                   └── FourierPlotter.java
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── example/
│   │       │           └── monitoring/
│   │       │               └── visualization/
│   │       │                   └── FourierPlotterTest.java
│   │       └── resources/
│   │           ├── application-test.yml
│   │           └── wiremock/
│   │               └── mappings/
│   ├── pom.xml
│   └── README.md
│
├── strategy-service/           # Trading strategies
├── execution-service/          # Order execution
├── risk-service/              # Risk management
├── backtesting-service/       # Strategy testing
│
├── deployment/                # Deployment configuration
│   ├── docker/
│   │   ├── Dockerfile.service-name
│   │   └── docker-compose.yml
│   └── kubernetes/
│       ├── service-deployments/
│       └── config-maps/
│
├── config/                    # Common configuration
│   ├── application.yml       # Shared settings
│   └── logback.xml          # Logging config
│
├── scripts/                   # Build and utility scripts
│   ├── build-all.sh
│   └── deploy.sh
│
├── .gitignore
├── pom.xml                    # Parent POM
└── README.md
```

## Technology Stack
- Java 17
- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Netflix Eureka for service discovery
- OpenFeign for service communication
- Maven for build management
- JUnit 5.10.0 for testing
- WireMock for service virtualization
- JFreeChart 1.5.3 for visualization
- Apache Commons Math 3.6.1 for calculations

## Service Port Allocation
- Eureka Server: 8761
- Data Ingestion: 8081
- Analysis: 8082
- Monitoring: 8083
- Strategy: 8084
- Execution: 8085
- Risk: 8086
- Backtesting: 8087

## Technology Stack
- Java 17
- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Netflix Eureka for service discovery
- OpenFeign for service communication
- Maven for build management
- JUnit 5.10.0 for testing
- WireMock for service virtualization
- JFreeChart 1.5.3 for visualization
- Apache Commons Math 3.6.1 for calculations

## Service Port Allocation
- Eureka Server: 8761
- Data Ingestion: 8081
- Analysis: 8082
- Monitoring: 8083
- Strategy: 8084
- Execution: 8085
- Risk: 8086
- Backtesting: 8087

## Current Implementation Status

### Completed
- Basic project structure
- Service discovery setup
- Analysis service implementation:
    - Fourier transform calculations
    - Time series analysis with moving averages
    - Butterworth filtering
    - Multi-symbol support
    - Comprehensive test coverage
- Initial monitoring service setup

### In Progress
- Monitoring service visualization implementation
- Service-to-service communication
- Test infrastructure setup
- Documentation updates

### Pending
- Complete monitoring service implementation
- Strategy service development
- Order execution implementation
- Risk management features
- Backtesting framework
- Deployment configurations
- Integration tests
- Performance testing

## Analysis Service Architecture

### Component Structure
1. **Preprocessing Layer**
    - FourierTransformer
        - Core FFT calculations
        - Sampling frequency analysis
        - Butterworth filter implementation
        - Time series reconstruction

2. **Service Layer**
    - TimeSeriesAnalysis
        - Moving average calculations (SMA, EMA)
        - Price history management
        - Technical indicator computation
        - Integration with Fourier analysis
    - TimeSeriesAnalysisService
        - Multi-symbol management
        - Symbol-specific analysis isolation
        - Service coordination
        - State management

3. **Model Layer**
    - TimeSeriesData
        - Time series data structure
        - Value and timestamp management
        - Data validation
        - Immutable design

### Testing Coverage
- Unit tests for all components
    - Fourier transform accuracy
    - Moving average calculations
    - Filter response validation
    - Multi-symbol isolation
- Mock-based service tests
- Integration tests for service interactions

## Service Dependencies
- Monitoring Service → Analysis Service
- Analysis Service → Data Ingestion Service
- Strategy Service → Analysis Service
- Execution Service → Strategy Service
- Risk Service → Execution Service
- Backtesting Service → Strategy Service

## Configuration Management
- Each service has its own application.yml
- Environment-specific configurations (dev/test/prod)
- Centralized logging configuration
- Service discovery settings
- Circuit breaker configurations

## Testing Strategy
- Unit tests with JUnit 5
    - Comprehensive testing of Fourier transforms
    - Time series analysis validation
    - Moving average calculations
    - Filter implementations
    - Multi-symbol support
- Integration tests with WireMock
- Service virtualization for dependencies
- Separate test configurations
- Automated test execution in CI/CD

## Build and Deployment
- Maven multi-module project
- Docker containerization
- Kubernetes deployment configurations
- CI/CD pipeline (pending)
- Monitoring and logging setup

## Security (Planned)
- Service-to-service authentication
- API gateway integration
- Rate limiting
- Security headers
- SSL/TLS configuration

## Next Steps
1. Complete monitoring service visualization
2. Implement REST controllers for Analysis Service
3. Add more technical indicators
4. Implement service intercommunication
5. Set up comprehensive testing
6. Add security features
7. Configure deployment pipeline
8. Enhance documentation

## Documentation Status
- Architecture overview completed
- API documentation in progress
- Setup guides pending review
- Service documentation ongoing