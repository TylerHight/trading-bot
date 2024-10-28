package com.example.analysis.controller;

import com.example.analysis.model.TimeSeriesData;
import com.example.analysis.service.FourierTransformerService;
import org.apache.commons.math3.complex.Complex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis/fourier")
public class FourierAnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(FourierAnalysisController.class);
    private final FourierTransformerService fourierService;

    @Autowired
    public FourierAnalysisController(FourierTransformerService fourierService) {
        this.fourierService = fourierService;
    }

    /**
     * Perform Fourier transform and return frequency-magnitude pairs
     */
    @PostMapping("/transform")
    public ResponseEntity<List<Double[]>> transformTimeSeries(@RequestBody TimeSeriesData timeSeriesData) {
        try {
            logger.info("Performing Fourier transform on time series data");
            Complex[] fftResult = fourierService.calculateFourierTransform(
                    timeSeriesData.getValues(),
                    timeSeriesData.getTimestamps()
            );
            double samplingFrequency = fourierService.calculateSamplingFrequency(
                    timeSeriesData.getTimestamps()
            );
            List<Double[]> result = fourierService.getFrequencyMagnitudePairs(fftResult, samplingFrequency);
            logger.info("Successfully completed Fourier transform");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error performing Fourier transform: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Apply filter to time series data
     */
    @PostMapping("/filter")
    public ResponseEntity<TimeSeriesData> filterTimeSeries(
            @RequestBody Map<String, Object> request) {
        try {
            TimeSeriesData inputData = new TimeSeriesData(
                    (List<Double>) request.get("values"),
                    (List<Long>) request.get("timestamps")
            );
            double lowCutoff = ((Number) request.get("lowCutoff")).doubleValue();
            double highCutoff = ((Number) request.get("highCutoff")).doubleValue();
            int order = ((Number) request.get("order")).intValue();
            String filterType = (String) request.get("filterType");

            logger.info("Applying {} filter to time series data", filterType);
            TimeSeriesData result = fourierService.filterTimeSeries(
                    inputData, lowCutoff, highCutoff, order, filterType
            );
            logger.info("Successfully applied filter");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error applying filter: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Main analysis endpoint combining transform and filtering
     */
    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeTimeSeries(
            @RequestBody Map<String, Object> request) {
        try {
            // Extract time series data
            TimeSeriesData inputData = new TimeSeriesData(
                    (List<Double>) request.get("values"),
                    (List<Long>) request.get("timestamps")
            );

            // Perform FFT
            Complex[] fftResult = fourierService.calculateFourierTransform(
                    inputData.getValues(),
                    inputData.getTimestamps()
            );
            double samplingFrequency = fourierService.calculateSamplingFrequency(
                    inputData.getTimestamps()
            );
            List<Double[]> frequencyMagnitudes = fourierService.getFrequencyMagnitudePairs(
                    fftResult,
                    samplingFrequency
            );

            // Apply filter if parameters are provided
            TimeSeriesData filteredData = null;
            if (request.containsKey("filterParams")) {
                Map<String, Object> filterParams = (Map<String, Object>) request.get("filterParams");
                filteredData = fourierService.filterTimeSeries(
                        inputData,
                        ((Number) filterParams.get("lowCutoff")).doubleValue(),
                        ((Number) filterParams.get("highCutoff")).doubleValue(),
                        ((Number) filterParams.get("order")).intValue(),
                        (String) filterParams.get("filterType")
                );
            }

            // Prepare response
            Map<String, Object> response = Map.of(
                    "frequencyAnalysis", frequencyMagnitudes,
                    "samplingFrequency", samplingFrequency,
                    "filteredData", filteredData != null ? filteredData : inputData
            );

            logger.info("Successfully completed time series analysis");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error analyzing time series: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}