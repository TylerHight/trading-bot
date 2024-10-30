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
│   ├── setup/                 # Setup and deployment guides
│   │   ├── installation.md
│   │   └── configuration.md
│   └── README.md
│
├── shared-lib/                  # Shared library module
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── example/
│   │   │               └── shared/
│   │   │                   ├── model/
│   │   │                   └── util/
│   │   └── test/
│   ├── pom.xml
│   └── README.md
│
├── data-ingestion-service/      # Data ingestion service
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── dataingestion/
│   │   │   │               ├── DataIngestionApplication.java
│   │   │   │               ├── controller/
│   │   │   │               ├── service/
│   │   │   │               ├── repository/
│   │   │   │               └── model/
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   ├── docs/                   # Service-specific documentation
│   │   ├── api.md
│   │   └── configuration.md
│   ├── pom.xml
│   └── README.md
│
├── analysis-service/            # Analysis service
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── analysis/
│   │   │   │               ├── AnalysisApplication.java
│   │   │   │               ├── controller/
│   │   │   │               ├── service/
│   │   │   │               ├── repository/
│   │   │   │               └── model/
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   ├── docs/                   # Service-specific documentation
│   │   ├── api.md
│   │   └── fourier-analysis.md
│   ├── pom.xml
│   └── README.md
│
├── deployment/                 # Deployment configuration
│   ├── docker/
│   │   ├── Dockerfile.service-name
│   │   └── docker-compose.yml
│   └── kubernetes/
│       ├── service-deployments/
│       └── config-maps/
│
├── config/                     # Common configuration
│   ├── application.yml
│   └── logback.xml
│
├── scripts/                    # Build and utility scripts
│   ├── build-all.sh
│   └── deploy.sh
│
├── .gitignore
├── pom.xml                     # Parent POM
└── README.md
```

## Directory Descriptions

### Root Level
- `docs/` - Project documentation
- `shared-lib/` - Common code shared across services
- `*-service/` - Individual microservices
- `deployment/` - Deployment configurations
- `config/` - Common configuration files
- `scripts/` - Build and deployment scripts

### Service Structure
Each service follows a standard Maven project structure:
```
service-name/
├── src/
│   ├── main/
│   │   ├── java/        # Service source code
│   │   └── resources/   # Service configuration
│   └── test/            # Test files
├── docs/                # Service documentation
├── pom.xml             # Service build file
└── README.md           # Service overview
```

### Documentation Structure
```
docs/
├── architecture/       # System architecture docs
├── api/               # API documentation
├── setup/             # Setup guides
└── README.md          # Documentation overview
```

## Package Naming Convention
- Base package: `com.example`
- Service packages: `com.example.[service-name]`
- Shared code: `com.example.shared`

## Build Structure
- Parent POM at root level
- Individual service POMs inherit from parent
- Shared library as a dependency

## Key Files
- `pom.xml` - Maven build configuration
- `application.yml` - Application configuration
- `Dockerfile` - Container build instructions
- `docker-compose.yml` - Local deployment setup

## Adding New Services
1. Create new directory under root
2. Follow standard service structure
3. Add to parent POM modules
4. Create service documentation
5. Update deployment configurations

## Best Practices
1. Maintain package structure consistency
2. Keep service-specific docs with service
3. Use shared library for common code
4. Follow naming conventions
5. Update documentation when adding features

## Related Documentation
- [Architecture Overview](./overview.md)
- [Service Documentation](./services.md)
- [Setup Guide](../setup/installation.md)