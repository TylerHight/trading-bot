package com.example.monitoring.controller;

import com.example.monitoring.client.AnalysisServiceClient;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/monitoring")
@CrossOrigin(origins = "http://localhost:3000")
public class FourierAnalysisController {

    private final AnalysisServiceClient analysisServiceClient;

    public FourierAnalysisController(AnalysisServiceClient analysisServiceClient) {
        this.analysisServiceClient = analysisServiceClient;
    }

    @GetMapping("/timeseries")
    public List<Map<String, Object>> getTimeSeriesData() {
        return analysisServiceClient.getTimeSeriesData();
    }

    @GetMapping("/frequency")
    public List<Map<String, Object>> getFrequencyData() {
        return analysisServiceClient.getFrequencyData();
    }

    // Test endpoint to verify the monitoring service is running
    @GetMapping("/test")
    public String test() {
        return "Monitoring service is working!";
    }
}