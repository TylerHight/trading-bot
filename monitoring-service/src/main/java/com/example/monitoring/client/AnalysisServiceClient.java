package com.example.monitoring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.analysis.model.TimeSeriesData;

import java.util.List;

@FeignClient(name = "analysis-service")
public interface AnalysisServiceClient {
    @PostMapping("/api/analysis/fourier/frequency-magnitude")
    List<Double[]> getFrequencyMagnitudePairs(@RequestBody TimeSeriesData timeSeriesData);
}