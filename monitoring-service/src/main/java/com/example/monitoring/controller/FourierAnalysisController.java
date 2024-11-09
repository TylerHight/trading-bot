package com.example.monitoring.controller;

import com.example.monitoring.client.AnalysisServiceClient;
import com.example.monitoring.model.TimeSeriesPoint;
import com.example.monitoring.model.FrequencyPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "*") // For development only - configure appropriately for production
public class FourierAnalysisController {

    private final AnalysisServiceClient analysisServiceClient;

    @Autowired
    public FourierAnalysisController(AnalysisServiceClient analysisServiceClient) {
        this.analysisServiceClient = analysisServiceClient;
    }

    @GetMapping("/timeseries/{symbol}")
    public ResponseEntity<List<TimeSeriesPoint>> getTimeSeriesData(@PathVariable String symbol) {
        return ResponseEntity.ok(analysisServiceClient.getTimeSeriesData(symbol));
    }

    @GetMapping("/frequency/{symbol}")
    public ResponseEntity<List<FrequencyPoint>> getFrequencyData(@PathVariable String symbol) {
        return ResponseEntity.ok(analysisServiceClient.getFrequencyData(symbol));
    }
}