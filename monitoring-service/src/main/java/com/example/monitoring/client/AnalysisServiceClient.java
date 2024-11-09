package com.example.monitoring.client;

import com.example.monitoring.model.TimeSeriesPoint;
import com.example.monitoring.model.FrequencyPoint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "analysis-service")
public interface AnalysisServiceClient {

    @GetMapping("/api/analysis/timeseries/{symbol}")
    List<TimeSeriesPoint> getTimeSeriesData(@PathVariable("symbol") String symbol);

    @GetMapping("/api/analysis/frequency/{symbol}")
    List<FrequencyPoint> getFrequencyData(@PathVariable("symbol") String symbol);
}