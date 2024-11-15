// src/main/java/com/example/dataingestion/config/TimeSeriesWebSocketHandler.java
package com.example.dataingestion.config;

import com.example.dataingestion.service.TimeSeriesGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TimeSeriesWebSocketHandler extends TextWebSocketHandler {
    private final TimeSeriesGeneratorService generatorService;
    private final ObjectMapper objectMapper;
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public TimeSeriesWebSocketHandler(TimeSeriesGeneratorService generatorService, ObjectMapper objectMapper) {
        this.generatorService = generatorService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Scheduled(fixedRate = 1000) // Send data every second
    public void sendData() {
        if (sessions.isEmpty()) {
            return;
        }

        try {
            String data = objectMapper.writeValueAsString(generatorService.generateDataPoint());
            TextMessage message = new TextMessage(data);

            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}