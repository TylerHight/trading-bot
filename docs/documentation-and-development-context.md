# Trading Bot Project: Development Context and Documentation
[Last Updated: 2024-11-15]

## LLM INSTRUCTIONS
- When sharing code snippets, always prefix with a comment containing the full file path
- Use explicit typing in all code examples
- Handle all error cases explicitly
- When implementing new features, consider circuit breaker patterns and fallbacks
- Maintain consistent service naming across all files
- Follow established logging patterns with appropriate log levels
- Consider security implications in all code suggestions

Example code comment format:
```java
// src/main/java/com/example/dataingestion/service/TimeSeriesGeneratorService.java
```

## ASSISTANT CONTEXT SECTION

Current Development Focus:
- Active Task: "Moving time series generation to data-ingestion-service"
- Last Change: "Added WebSocket support to data-ingestion-service"
- Next Task: "Connect frontend to data-ingestion-service WebSocket"

Recent Code Changes (Last 3 conversations):
1. Created data-ingestion-service WebSocket handler (2024-11-15)
2. Migrated TimeSeriesGeneratorService to data-ingestion-service (2024-11-15)
3. Updated frontend WebSocket connection (2024-11-15)

Known Context Gaps:
- WebSocket error handling needs improvement
- Need connection status monitoring
- Real-time data validation required
- Frontend reconnection logic needs testing

## IMPLEMENTATION STATUS

### Currently Working Features ✓
- Theme system (light/dark/system)
- Base chart components
- Time series visualization
- Theme-aware tooltips
- UI components for connection status
- Service discovery registration
- Basic data generation

### In Progress 🔄
- WebSocket integration
- Data streaming
- Frontend connection handling
- Service monitoring setup

### Planned Features 📋
- Advanced technical indicators
- Strategy implementation
- Order execution system
- Service metrics and monitoring
- Real-time data validation
- Connection resilience
- Data persistence

## TECHNICAL SPECIFICATIONS

### Core Data Types
```typescript
// src/types/TimeSeriesPoint.ts
interface TimeSeriesPoint {
    timestamp: string;
    value: number;
}

interface FrequencyPoint {
    frequency: string;
    magnitude: number;
}
```

### New Service Integration Files
```
Backend:
src/main/java/com/example/dataingestion/
├── config/
│   ├── WebSocketConfig.java          # WebSocket configuration
│   └── ObjectMapperConfig.java       # JSON serialization config
├── dto/
│   └── TimeSeriesPoint.java
├── service/
│   └── TimeSeriesGeneratorService.java
└── websocket/
    └── TimeSeriesWebSocketHandler.java

Frontend:
src/
├── hooks/
│   └── useTimeSeriesWebSocket.ts
├── services/
│   └── dataService.ts
└── components/
    └── FourierAnalysisDashboard.tsx
```

### WebSocket Configuration
```java
// src/main/java/com/example/dataingestion/config/WebSocketConfig.java
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(timeSeriesHandler, "/api/v1/data/ws")
               .setAllowedOrigins("*");
    }
}
```

## DEVELOPMENT NOTES

### Project Dependencies
[Updated to show current data-ingestion-service dependencies]
```xml
<!-- WebSocket Support -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

### Known Issues
1. ✓ Fixed: Service discovery registration
2. ✓ Fixed: Data generation service migration
3. 🔄 Active: WebSocket connection
4. 🔄 Active: Frontend integration
5. 📋 Planned: Connection resilience
6. 📋 Planned: Data validation

### Architecture Decisions
1. Using WebSocket for real-time data streaming
2. Implementing service discovery with Eureka
3. Centralizing data generation in data-ingestion-service
4. Using reactive patterns for data handling

### Integration Patterns
- WebSocket Communication
- Service Discovery
- Real-time Data Streaming
- Event-Driven Architecture

## SECURITY CONSIDERATIONS
- No sensitive data in configuration files
- No hardcoded credentials
- No internal URLs or IPs
- No proprietary algorithms
- Basic WebSocket security
- Service-to-service authentication pending

## VERSION CONTROL
Document Version: 1.3
Last Editor: Assistant
Last Edit Date: 2024-11-15

[End of Document]