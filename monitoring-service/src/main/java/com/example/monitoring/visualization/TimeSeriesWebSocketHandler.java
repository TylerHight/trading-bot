package com.example.monitoring.visualization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.monitoring.service.TimeSeriesGeneratorService;
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
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final TimeSeriesGeneratorService generatorService;
    private final ObjectMapper objectMapper;

    public TimeSeriesWebSocketHandler(TimeSeriesGeneratorService generatorService, ObjectMapper objectMapper) {
        this.generatorService = generatorService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
    }

    @Scheduled(fixedRate = 1000) // Generate and send data every second
    public void sendData() {
        var dataPoint = generatorService.generateDataPoint();
        String jsonMessage;
        try {
            jsonMessage = objectMapper.writeValueAsString(dataPoint);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        sessions.forEach(session -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(jsonMessage));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}