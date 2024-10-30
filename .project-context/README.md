# Trading Bot Microservices

A sophisticated trading bot platform built with Spring Boot microservices architecture, designed for high-performance market analysis and automated trading strategies.

## Project Overview

This platform converts a monolithic trading application into a scalable, maintainable microservices architecture. It provides real-time market data analysis, technical indicators using Fourier transforms, automated trading strategies, and comprehensive risk management.

## Architecture

### Services
- **Data Ingestion Service** (Port: 8081)
    - Market data collection
    - Real-time data processing
    - Historical data management

- **Analysis Service** (Port: 8082)
    - Technical analysis
    - Fourier transforms
    - Signal processing
    - Time series analysis

- **Monitoring Service** (Port: 8083)
    - Real-time visualization
    - System monitoring
    - Performance metrics
    - Alert management

- **Strategy Service** (Port: 8084)
    - Trading strategy implementation
    - Signal generation
    - Portfolio management

- **Execution Service** (Port: 8085)
    - Order execution
    - Position management
    - Transaction logging

- **Risk Service** (Port: 8086)
    - Risk assessment
    - Exposure monitoring
    - Compliance checks

- **Backtesting Service** (Port: 8087)
    - Strategy testing
    - Performance analysis
    - Historical simulations

## Technology Stack

### Core
- Java 17
- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Maven

### Service Communication
- Netflix Eureka
- OpenFeign
- Spring Cloud Gateway

### Analysis & Computation
- Apache Commons Math 3.6.1
- JFreeChart 1.5.3

### Testing
- JUnit 5.10.0
- WireMock
- Spring Cloud Contract

## Getting Started

### Prerequisites
```bash
Java 17+
Maven 3.8+
Docker (optional)
```

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/trading-bot.git
cd trading-bot
```

2. Build all services
```bash
./scripts/build-all.sh
```

3. Start Eureka Server
```bash
java -jar eureka-server/target/eureka-server.jar
```

4. Start required services
```bash
# Start individual services
java -jar service-name/target/service-name.jar
```

### Docker Deployment
```bash
# Build Docker images
docker-compose -f deployment/docker/docker-compose.yml build

# Start services
docker-compose -f deployment/docker/docker-compose.yml up
```

## Development

### Building
```bash
# Build single service
mvn clean install -pl :service-name

# Build all services
mvn clean install
```

### Testing
```bash
# Run tests for single service
mvn test -pl :service-name

# Run all tests
mvn test
```

### Configuration
- Each service has its own `application.yml`
- Environment-specific configurations in `application-{env}.yml`
- Common settings in `config/application.yml`

## Project Structure
See [Project Context](.project-context/CONTEXT.md) for detailed structure and current development status.

## Documentation
- Architecture: `docs/architecture/`
- API Documentation: `docs/api/`
- Setup Guides: `docs/setup/`
- Service-specific docs: `{service-name}/docs/`

## Contributing

1. Check existing issues or create a new one
2. Fork the repository
3. Create a feature branch (`git checkout -b feature/amazing-feature`)
4. Commit changes (`git commit -m 'Add amazing feature'`)
5. Push to branch (`git push origin feature/amazing-feature`)
6. Create a Pull Request

### Development Guidelines
- Follow Spring Boot best practices
- Maintain service independence
- Write comprehensive tests
- Update documentation
- Keep the project context updated

## Current Status
- Core services implemented
- Monitoring visualization in progress
- Service communication being established
- Testing infrastructure setup
- Documentation ongoing

## Next Steps
1. Complete monitoring service
2. Implement security features
3. Add performance testing
4. Set up CI/CD pipeline
5. Deploy to production

## License
[Your License Choice]

## Contact
Your Name - your.email@example.com
Project Link: [https://github.com/yourusername/trading-bot](https://github.com/yourusername/trading-bot)

## Acknowledgments
- Spring Boot Team
- Apache Commons Math
- JFreeChart
- Other libraries and tools used

---
**Note**: Ensure all sensitive information (API keys, credentials) is properly secured and never committed to the repository.