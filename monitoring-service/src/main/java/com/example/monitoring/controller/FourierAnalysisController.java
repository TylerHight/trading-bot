package com.example.monitoring.controller;

import com.example.monitoring.client.AnalysisServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/monitoring")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class FourierAnalysisController {
    private final AnalysisServiceClient analysisServiceClient;

    @GetMapping("/timeseries")
    public List<Map<String, Object>> getTimeSeriesData() {
        log.debug("Requesting time series data from analysis service");
        return analysisServiceClient.getTimeSeriesData();
    }

    @GetMapping("/frequency")
    public List<Map<String, Object>> getFrequencyData() {
        log.debug("Requesting frequency data from analysis service");
        return analysisServiceClient.getFrequencyData();
    }

    @GetMapping("/test")
    public String test() {
        return "Monitoring service is working!";
    }
}