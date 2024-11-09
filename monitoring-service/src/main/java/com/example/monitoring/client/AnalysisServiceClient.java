package com.example.monitoring.client;

import com.example.monitoring.dto.FourierAnalysisDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

@FeignClient(name = "analysis-service")
public interface AnalysisServiceClient {
    @GetMapping("/api/analysis/fourier/analyze")
    ResponseEntity<FourierAnalysisDTO> getFourierAnalysis();
}