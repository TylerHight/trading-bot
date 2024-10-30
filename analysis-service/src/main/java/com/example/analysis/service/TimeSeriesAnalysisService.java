package com.example.analysis.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.analysis.model.TimeSeriesData;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Service
public class TimeSeriesAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(TimeSeriesAnalysisService.class);

    private final FourierTransformerService fourierTransformer;
    private final Map<String, TimeSeriesAnalysis> analysisBySymbol;

    @Autowired
    public TimeSeriesAnalysisService(FourierTransformerService fourierTransformer) {
        this.fourierTransformer = fourierTransformer;
        this.analysisBySymbol = new HashMap<>();
        logger.info("Initialized TimeSeriesAnalysisService");
    }

    public TimeSeriesAnalysis getOrCreateAnalysis(String symbol) {
        return analysisBySymbol.computeIfAbsent(symbol, k -> {
            logger.info("Creating new time series analysis for symbol: {}", symbol);
            return new TimeSeriesAnalysis(20, 50, new ArrayList<>(), new ArrayList<>());
        });
    }

    public void addPrice(String symbol, double price, long timestamp) {
        TimeSeriesAnalysis analysis = getOrCreateAnalysis(symbol);
        analysis.addPrice(price, timestamp);
        logger.debug("Added price {} for symbol {} at timestamp {}", price, symbol, timestamp);
    }

    public Double getLastSMA(String symbol) {
        return getOrCreateAnalysis(symbol).getLastSMA();
    }

    public Double getLastEMA(String symbol) {
        return getOrCreateAnalysis(symbol).getLastEMA();
    }

    public List<Double[]> getFourierAnalysis(String symbol) {
        logger.info("Performing Fourier analysis for symbol: {}", symbol);
        return getOrCreateAnalysis(symbol).getFrequencyMagnitudePairs();
    }

    public TimeSeriesData getTimeSeriesData(String symbol) {
        return getOrCreateAnalysis(symbol).getTimeSeriesData();
    }

    public TimeSeriesData applyFilter(String symbol, double lowCutoff, double highCutoff,
                                      int order, String filterType) {
        return getOrCreateAnalysis(symbol).applyFilter(lowCutoff, highCutoff, order, filterType);
    }
}