package com.example.monitoring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Collections;

@FeignClient(
        name = "analysis-service",
        fallback = AnalysisServiceClient.AnalysisServiceFallback.class
)
public interface AnalysisServiceClient {
    @GetMapping("/api/v1/analysis/timeseries")
    List<Map<String, Object>> getTimeSeriesData();

    @GetMapping("/api/v1/analysis/frequency")
    List<Map<String, Object>> getFrequencyData();

    @Component
    class AnalysisServiceFallback implements AnalysisServiceClient {
        private static final Logger logger = LoggerFactory.getLogger(AnalysisServiceFallback.class);

        @Override
        public List<Map<String, Object>> getTimeSeriesData() {
            logger.warn("Using fallback for time series data");
            return Collections.emptyList();
        }

        @Override
        public List<Map<String, Object>> getFrequencyData() {
            logger.warn("Using fallback for frequency data");
            return Collections.emptyList();
        }
    }
}