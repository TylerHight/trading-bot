package com.example.monitoring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;

@FeignClient(name = "analysis-service")
public interface AnalysisServiceClient {
    @GetMapping("/api/v1/analysis/timeseries")
    List<Map<String, Object>> getTimeSeriesData();

    @GetMapping("/api/v1/analysis/frequency")
    List<Map<String, Object>> getFrequencyData();
}