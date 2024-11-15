package com.example.monitoring.visualization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.monitoring.service.TimeSeriesGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TimeSeriesWebSocketHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(TimeSeriesWebSocketHandler.class);
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final TimeSeriesGeneratorService generatorService;
    private final ObjectMapper objectMapper;

    public TimeSeriesWebSocketHandler(TimeSeriesGeneratorService generatorService, ObjectMapper objectMapper) {
        this.generatorService = generatorService;
        this.objectMapper = objectMapper;
        logger.info("TimeSeriesWebSocketHandler initialized");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        logger.info("WebSocket connection established: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
        logger.info("WebSocket connection closed: {}, status: {}", session.getId(), status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("Transport error for session {}: {}", session.getId(), exception.getMessage());
    }

    @Scheduled(fixedRate = 1000)
    public void sendData() {
        if (sessions.isEmpty()) {
            logger.debug("No active sessions, skipping data generation");
            return;
        }

        var dataPoint = generatorService.generateDataPoint();
        try {
            String jsonMessage = objectMapper.writeValueAsString(dataPoint);
            logger.debug("Generated data point: {}", jsonMessage);

            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(jsonMessage));
                    logger.debug("Data sent to session: {}", session.getId());
                }
            }
        } catch (IOException e) {
            logger.error("Error sending data: {}", e.getMessage(), e);
        }
    }
}