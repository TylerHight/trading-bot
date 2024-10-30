package com.example.analysis.service;

import com.example.analysis.model.TimeSeriesData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.commons.math3.complex.Complex;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TimeSeriesAnalysisTest {
    private TimeSeriesAnalysis tsa;
    private static final double DELTA = 1e-6;

    @BeforeEach
    void setUp() {
        tsa = new TimeSeriesAnalysis(3, 3, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void testSMACalculation() {
        tsa.addPrice(10, 1000);
        assertNull(tsa.getLastSMA());

        tsa.addPrice(20, 2000);
        assertNull(tsa.getLastSMA());

        tsa.addPrice(30, 3000);
        assertEquals(20, tsa.getLastSMA(), DELTA);

        tsa.addPrice(40, 4000);
        assertEquals(30, tsa.getLastSMA(), DELTA);

        tsa.addPrice(50, 5000);
        assertEquals(40, tsa.getLastSMA(), DELTA);
    }

    @Test
    void testEMACalculation() {
        tsa.addPrice(10, 1000);
        assertNull(tsa.getLastEMA());

        tsa.addPrice(20, 2000);
        assertNull(tsa.getLastEMA());

        tsa.addPrice(30, 3000);
        assertEquals(20, tsa.getLastEMA(), DELTA);

        tsa.addPrice(40, 4000);
        assertEquals(30, tsa.getLastEMA(), DELTA);

        tsa.addPrice(50, 5000);
        assertEquals(40, tsa.getLastEMA(), DELTA);
    }

    @Test
    void testInitialDataLargerThanWindow() {
        List<Double> initialValues = Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0);
        List<Long> initialTimestamps = Arrays.asList(1000L, 2000L, 3000L, 4000L, 5000L);

        TimeSeriesAnalysis tsaWithInitialData = new TimeSeriesAnalysis(3, 3, initialValues, initialTimestamps);

        assertEquals(40, tsaWithInitialData.getLastSMA(), DELTA);
        assertEquals(40, tsaWithInitialData.getLastEMA(), DELTA);

        tsaWithInitialData.addPrice(60, 6000);
        assertEquals(50, tsaWithInitialData.getLastSMA(), DELTA);
        assertEquals(50, tsaWithInitialData.getLastEMA(), DELTA);
    }

    @Test
    void testAddPrice() {
        tsa.addPrice(10, 1000);
        assertEquals(1, tsa.getValues().size());
        assertEquals(1, tsa.getTimestamps().size());
        assertEquals(10, tsa.getValues().get(0), DELTA);
        assertEquals(1000, tsa.getTimestamps().get(0));
    }

    @Test
    void testGetTimeSeriesData() {
        tsa.addPrice(10, 1000);
        tsa.addPrice(20, 2000);

        TimeSeriesData data = tsa.getTimeSeriesData();
        assertNotNull(data);
        assertEquals(2, data.getValues().size());
        assertEquals(2, data.getTimestamps().size());
        assertEquals(10, data.getValues().get(0), DELTA);
        assertEquals(1000, data.getTimestamps().get(0));
    }

    @Test
    void testCalculateSamplingFrequency() {
        List<Double> values = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Long> timestamps = Arrays.asList(0L, 1000L, 2000L, 3000L, 4000L);

        for (int i = 0; i < values.size(); i++) {
            tsa.addPrice(values.get(i), timestamps.get(i));
        }

        double samplingFrequency = tsa.calculateSamplingFrequency();
        assertEquals(1.0, samplingFrequency, DELTA);
    }

    @Test
    void testGetFrequencyMagnitudePairs() {
        // Create a simple sine wave with known frequency
        double frequency = 1.0; // 1 Hz
        int numSamples = 32;
        double samplingRate = 10.0; // 10 Hz

        for (int i = 0; i < numSamples; i++) {
            double time = i / samplingRate;
            double value = Math.sin(2 * Math.PI * frequency * time);
            tsa.addPrice(value, (long)(time * 1000));
        }

        List<Double[]> result = tsa.getFrequencyMagnitudePairs();

        assertNotNull(result);
        assertTrue(result.size() > 0);

        // Find the peak frequency
        Double[] peak = result.stream()
                .max((a, b) -> Double.compare(a[1], b[1]))
                .orElseThrow();

        // Check if peak frequency is close to 1 Hz
        assertEquals(1.0, peak[0], 0.1);
    }

    @Test
    void testApplyFilter() {
        // Create a composite signal with two frequencies
        double f1 = 1.0; // 1 Hz - will be kept
        double f2 = 5.0; // 5 Hz - will be filtered out
        int numSamples = 100;
        double samplingRate = 20.0; // 20 Hz

        for (int i = 0; i < numSamples; i++) {
            double time = i / samplingRate;
            double value = Math.sin(2 * Math.PI * f1 * time) +
                    Math.sin(2 * Math.PI * f2 * time);
            tsa.addPrice(value, (long)(time * 1000));
        }

        TimeSeriesData filtered = tsa.applyFilter(0.0, 2.0, 4, "lowpass");

        assertNotNull(filtered);
        assertFalse(filtered.getValues().isEmpty());

        // Check if high frequency component is attenuated
        double maxAmplitude = filtered.getValues().stream()
                .mapToDouble(Math::abs)
                .max()
                .orElse(0.0);

        // Max amplitude should be close to 1 (the amplitude of the remaining 1 Hz signal)
        assertEquals(1.0, maxAmplitude, 0.2);
    }
}