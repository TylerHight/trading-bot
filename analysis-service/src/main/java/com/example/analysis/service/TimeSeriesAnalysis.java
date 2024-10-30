package com.example.analysis.service;

import com.example.analysis.model.TimeSeriesData;
import com.example.analysis.service.FourierTransformer;
import org.apache.commons.math3.complex.Complex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stores data on and performs analyses on a particular set of timeseries data.
 */
@Service
public class TimeSeriesAnalysis {
    private static final Logger logger = LoggerFactory.getLogger(TimeSeriesAnalysis.class);

    private List<Double> values;
    private List<Long> timestamps;
    private final FourierTransformer fourierTransformer;
    private Double lastSMA;
    private Double lastEMA;
    private int smaPeriod;
    private int emaPeriod;
    private double smaSum;

    public TimeSeriesAnalysis(int smaPeriod, int emaPeriod) {
        this(smaPeriod, emaPeriod, new ArrayList<>(), new ArrayList<>());
    }

    public TimeSeriesAnalysis(int smaPeriod, int emaPeriod, List<Double> initialValues, List<Long> initialTimestamps) {
        if (initialValues.size() != initialTimestamps.size()) {
            throw new IllegalArgumentException("Values and timestamps must have the same size");
        }
        this.values = new ArrayList<>(initialValues);
        this.timestamps = new ArrayList<>(initialTimestamps);
        this.fourierTransformer = new FourierTransformer();
        this.smaPeriod = smaPeriod;
        this.emaPeriod = emaPeriod;
        this.lastSMA = null;
        this.lastEMA = null;
        this.smaSum = 0.0;

        initializeIndicators();
        logger.info("Initialized TimeSeriesAnalysis with SMA period: {} and EMA period: {}",
                smaPeriod, emaPeriod);
    }

    private void initializeIndicators() {
        if (values.isEmpty()) return;

        // Initialize SMA
        int smaStartIndex = Math.max(0, values.size() - smaPeriod);
        for (int i = smaStartIndex; i < values.size(); i++) {
            smaSum += values.get(i);
        }
        if (values.size() >= smaPeriod) {
            lastSMA = smaSum / smaPeriod;
        }

        // Initialize EMA
        if (values.size() >= emaPeriod) {
            double sum = 0.0;
            for (int i = values.size() - emaPeriod; i < values.size(); i++) {
                sum += values.get(i);
            }
            lastEMA = sum / emaPeriod;
        }

        logger.debug("Indicators initialized. Initial SMA: {}, Initial EMA: {}", lastSMA, lastEMA);
    }

    public void addPrice(double price, long timestamp) {
        values.add(price);
        timestamps.add(timestamp);
        updateSMA(price);
        updateEMA(price);
        logger.debug("Added new price: {} at timestamp: {}. Updated SMA: {}, EMA: {}",
                price, timestamp, lastSMA, lastEMA);
    }

    private void updateSMA(double newPrice) {
        if (values.size() <= smaPeriod) {
            smaSum += newPrice;
            if (values.size() == smaPeriod) {
                lastSMA = smaSum / smaPeriod;
            } else {
                lastSMA = null;
            }
        } else {
            smaSum = smaSum - values.get(values.size() - smaPeriod - 1) + newPrice;
            lastSMA = smaSum / smaPeriod;
        }
    }

    private void updateEMA(double newPrice) {
        if (values.size() < emaPeriod) {
            lastEMA = null;
            return;
        }

        if (lastEMA == null) {
            double sum = 0.0;
            for (int i = values.size() - emaPeriod; i < values.size(); i++) {
                sum += values.get(i);
            }
            lastEMA = sum / emaPeriod;
        } else {
            double multiplier = 2.0 / (emaPeriod + 1);
            lastEMA = ((newPrice - lastEMA) * multiplier) + lastEMA;
        }
    }

    public Double getLastSMA() {
        return lastSMA;
    }

    public Double getLastEMA() {
        return lastEMA;
    }

    public List<Double> getValues() {
        return new ArrayList<>(values);
    }

    public List<Long> getTimestamps() {
        return new ArrayList<>(timestamps);
    }

    public TimeSeriesData getTimeSeriesData() {
        return new TimeSeriesData(values, timestamps);
    }

    /**
     * Calculate the FFT of the input values using FourierTransformer.
     *
     * @return The complex FFT result.
     */
    public Complex[] calculateFourierTransform() {
        return fourierTransformer.calculateFourierTransform(values, timestamps);
    }

    /**
     * Calculate the sampling frequency from the timestamps using FourierTransformer.
     *
     * @return The sampling frequency in Hz.
     */
    public double calculateSamplingFrequency() {
        return fourierTransformer.calculateSamplingFrequency(timestamps);
    }

    /**
     * Get frequency-magnitude pairs from the complex FFT result using FourierTransformer.
     *
     * @return A list of Fourier Transform magnitudes and frequencies.
     */
    public List<Double[]> getFrequencyMagnitudePairs() {
        Complex[] fftResult = calculateFourierTransform();
        double samplingFrequency = calculateSamplingFrequency();
        return fourierTransformer.getFrequencyMagnitudePairs(fftResult, samplingFrequency);
    }

    /**
     * Apply filtering to the current time series data.
     *
     * @param lowCutoff The lower cutoff frequency
     * @param highCutoff The higher cutoff frequency
     * @param order The filter order
     * @param filterType The type of filter ("lowpass", "highpass", or "bandpass")
     * @return The filtered time series data
     */
    public TimeSeriesData applyFilter(double lowCutoff, double highCutoff, int order, String filterType) {
        List<List<Double>> filtered = fourierTransformer.filterTimeSeries(values, timestamps,
                lowCutoff, highCutoff, order, filterType);

        // Convert doubles to longs using collect instead of toList()
        List<Long> filteredTimestamps = filtered.get(1).stream()
                .mapToLong(Math::round)
                .boxed()
                .collect(Collectors.toList());

        return new TimeSeriesData(filtered.get(0), filteredTimestamps);
    }
}