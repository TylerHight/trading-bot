// src/main/java/com/example/dataingestion/config/WebSocketConfig.java
package com.example.dataingestion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TimeSeriesWebSocketHandler timeSeriesHandler;

    public WebSocketConfig(TimeSeriesWebSocketHandler timeSeriesHandler) {
        this.timeSeriesHandler = timeSeriesHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(timeSeriesHandler, "/api/v1/data/ws")
                .setAllowedOrigins("*"); // Configure appropriate CORS in production
    }
}