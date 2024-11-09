package com.example.analysis.controller;

import com.example.analysis.model.TimeSeriesData;
import com.example.analysis.service.TimeSeriesAnalysisService;
import com.example.shared.dto.FourierAnalysisDTO;
import com.example.shared.dto.FrequencyComponentDTO;
import com.example.shared.dto.TimeSeriesDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/analysis/fourier")
@RequiredArgsConstructor
public class FourierAnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(FourierAnalysisController.class);
    private final TimeSeriesAnalysisService analysisService;

    @GetMapping("/analyze")
    public ResponseEntity<FourierAnalysisDTO> analyzeFourier() {
        try {
            logger.info("Starting Fourier analysis");

            // Get frequency-magnitude pairs from the analysis service
            List<Double[]> frequencyMagnitudePairs = analysisService.getFourierAnalysis("DEFAULT");

            // Convert to DTOs
            List<FrequencyComponentDTO> components = new ArrayList<>();
            for (Double[] pair : frequencyMagnitudePairs) {
                components.add(new FrequencyComponentDTO(pair[0], pair[1]));
            }

            // Create response
            FourierAnalysisDTO response = new FourierAnalysisDTO(components, 0.0); // Set actual sampling frequency

            logger.info("Fourier analysis completed successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error performing Fourier analysis: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}