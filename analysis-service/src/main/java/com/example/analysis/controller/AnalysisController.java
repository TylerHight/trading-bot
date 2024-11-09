package com.example.analysis.controller;

import com.example.analysis.service.TimeSeriesAnalysis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AnalysisController {
    private final TimeSeriesAnalysis timeSeriesAnalysis;

    @GetMapping("/timeseries")
    public List<Map<String, Object>> getTimeSeriesData() {
        List<Double> values = timeSeriesAnalysis.getValues();
        List<Long> timestamps = timeSeriesAnalysis.getTimestamps();
        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("timestamp", timestamps.get(i));
            dataPoint.put("value", values.get(i));
            result.add(dataPoint);
        }

        return result;
    }

    @GetMapping("/frequency")
    public List<Map<String, Object>> getFrequencyData() {
        List<Double[]> frequencyData = timeSeriesAnalysis.getFrequencyMagnitudePairs();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Double[] pair : frequencyData) {
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("frequency", pair[0]);
            dataPoint.put("magnitude", pair[1]);
            result.add(dataPoint);
        }

        return result;
    }
}