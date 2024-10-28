package com.example.analysis.service;

import com.example.analysis.model.TimeSeriesData;
import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FourierTransformerServiceTest {
    private static final double DELTA = 0.001;
    private FourierTransformerService fourierService;

    @BeforeEach
    void setUp() {
        fourierService = new FourierTransformerService();
    }

    @Test
    void testInverseFFTWithEmptyInput() {
        List<Long> originalTimestamps = Arrays.asList(0L, 100L, 200L);
        Complex[] fftResult = new Complex[0];
        TimeSeriesData result = fourierService.calculateInverseFFT(fftResult, originalTimestamps);
        assertTrue(result.getValues().isEmpty());
    }

    @Test
    void testInverseFFTWithConstantSignal() {
        List<Long> originalTimestamps = Arrays.asList(0L, 100L, 200L, 300L);
        Complex[] fftResult = {new Complex(4, 0), new Complex(0, 0), new Complex(0, 0), new Complex(0, 0)};
        TimeSeriesData result = fourierService.calculateInverseFFT(fftResult, originalTimestamps);
        assertEquals(1.0, result.getValues().get(0), DELTA);
    }

    @Test
    void testInverseFFTWithSingleFrequencySineWave() {
        int numSamples = 1000;
        double samplingInterval = 10; // 10 milliseconds
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i * samplingInterval;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000));
            values.add(value);
            timestamps.add((long) time);
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);
        Complex[] fftResult = fourierService.calculateFourierTransform(inputData.getValues(), inputData.getTimestamps());
        TimeSeriesData result = fourierService.calculateInverseFFT(fftResult, timestamps);

        assertEquals(values.get(3), result.getValues().get(3), 0.1);
    }

    @Test
    void testInverseFFTWithCompositeSineWave() {
        int numSamples = 1000;
        double samplingInterval = 10;
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i * samplingInterval;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000)) +
                    Math.sin(2 * Math.PI * 3 * (time / 1000));
            values.add(value);
            timestamps.add((long) time);
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);
        Complex[] fftResult = fourierService.calculateFourierTransform(inputData.getValues(), inputData.getTimestamps());
        TimeSeriesData result = fourierService.calculateInverseFFT(fftResult, timestamps);

        assertEquals(values.get(0), result.getValues().get(0), DELTA);
    }

    @Test
    void testInverseFFTWithTripleSineWave() {
        int numSamples = 1000;
        double samplingInterval = 10;
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i * samplingInterval;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000)) +
                    Math.sin(2 * Math.PI * 3 * (time / 1000)) +
                    Math.sin(3 * Math.PI * 6 * (time / 1000));
            values.add(value);
            timestamps.add((long) time);
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);
        Complex[] fftResult = fourierService.calculateFourierTransform(inputData.getValues(), inputData.getTimestamps());
        TimeSeriesData result = fourierService.calculateInverseFFT(fftResult, timestamps);

        assertEquals(values.get(5), result.getValues().get(5), 0.001);
    }

    @Test
    void testEmptyInput() {
        TimeSeriesData inputData = new TimeSeriesData(new ArrayList<>(), new ArrayList<>());
        Complex[] result = fourierService.calculateFourierTransform(
                inputData.getValues(),
                inputData.getTimestamps()
        );
        assertEquals(0, result.length, "Empty input should return empty array");
    }

    @Test
    void testSingleValue() {
        TimeSeriesData inputData = new TimeSeriesData(
                Arrays.asList(5.0),
                Arrays.asList(0L)
        );
        Complex[] result = fourierService.calculateFourierTransform(
                inputData.getValues(),
                inputData.getTimestamps()
        );
        assertEquals(5.0, result[0].getReal(), DELTA, "FFT of single value should be the value itself");
    }

    @Test
    void testConstantInput() {
        TimeSeriesData inputData = new TimeSeriesData(
                Arrays.asList(3.0, 3.0, 3.0, 3.0),
                Arrays.asList(0L, 1000L, 2000L, 3000L)
        );
        Complex[] result = fourierService.calculateFourierTransform(
                inputData.getValues(),
                inputData.getTimestamps()
        );
        assertEquals(12.0, result[0].getReal(), DELTA, "DC component should be 4 times the constant value");
    }

    @Test
    void testSamplingFrequency() {
        List<Long> timestamps = Arrays.asList(0L, 100L, 200L, 300L);
        double samplingFrequency = fourierService.calculateSamplingFrequency(timestamps);
        assertEquals(10.0, samplingFrequency, DELTA, "Sampling frequency should be 10 Hz for 100ms intervals");
    }

    @Test
    void testCalculateFourierTransform() {
        int numSamples = 1000;
        double frequency = 1.0; // 1 Hz
        double amplitude = 2.0;
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i / (double) numSamples;
            double value = amplitude * Math.sin(2 * Math.PI * frequency * time);
            values.add(value);
            timestamps.add((long) (time * 1000));
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);
        Complex[] fftResult = fourierService.calculateFourierTransform(
                inputData.getValues(),
                inputData.getTimestamps()
        );

        int expectedPeakIndex = 1;
        double expectedImaginary = -1008.0;
        Complex resultUnrounded = fftResult[expectedPeakIndex];
        long resultRoundedComplex = Math.round(resultUnrounded.getImaginary());

        assertEquals(expectedImaginary, resultRoundedComplex, "FFT result should match expected value");
    }

    @Test
    void testCompositeSineWaveFFT() {
        int numSamples = 1000;
        double samplingInterval = 10;
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i * samplingInterval;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000)) +
                    Math.sin(2 * Math.PI * 3 * (time / 1000));
            values.add(value);
            timestamps.add((long) time);
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);
        Complex[] fftResult = fourierService.calculateFourierTransform(
                inputData.getValues(),
                inputData.getTimestamps()
        );

        List<Double[]> freqMagPairs = fourierService.getFrequencyMagnitudePairs(
                fftResult,
                fourierService.calculateSamplingFrequency(timestamps)
        );

        Double[] peak1Hz = null;
        Double[] peak3Hz = null;
        for (Double[] pair : freqMagPairs) {
            if (Math.abs(pair[0] - 1.0) < 0.1 && (peak1Hz == null || pair[1] > peak1Hz[1])) {
                peak1Hz = pair;
            }
            if (Math.abs(pair[0] - 3.0) < 0.1 && (peak3Hz == null || pair[1] > peak3Hz[1])) {
                peak3Hz = pair;
            }
        }

        assertNotNull(peak1Hz, "1 Hz peak should be found");
        assertNotNull(peak3Hz, "3 Hz peak should be found");
        assertEquals(469.0, Math.round(peak1Hz[1]), "Magnitude of 1 Hz peak should be around 469");
        assertEquals(442.0, Math.round(peak3Hz[1]), "Magnitude of 3 Hz peak should be around 442");
    }

    @Test
    void testSingleFrequencySineWaveFFT() {
        int numSamples = 1000;
        double samplingInterval = 10;
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i * samplingInterval;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000));
            values.add(value);
            timestamps.add((long) time);
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);
        Complex[] fftResult = fourierService.calculateFourierTransform(
                inputData.getValues(),
                inputData.getTimestamps()
        );

        List<Double[]> freqMagPairs = fourierService.getFrequencyMagnitudePairs(
                fftResult,
                fourierService.calculateSamplingFrequency(timestamps)
        );

        Double[] peak1Hz = null;
        for (Double[] pair : freqMagPairs) {
            if (Math.abs(pair[0] - 1.0) < 0.1 && (peak1Hz == null || pair[1] > peak1Hz[1])) {
                peak1Hz = pair;
            }
        }

        assertNotNull(peak1Hz, "1 Hz peak should be found");
        assertEquals(461.0, Math.round(peak1Hz[1]), "Magnitude of 1 Hz peak should be around 461");
    }

    @Test
    void testLowPassFilterOnCompositeSineWave() {
        int numSamples = 1000;
        double samplingInterval = 10;
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i * samplingInterval;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000)) +
                    Math.sin(2 * Math.PI * 3 * (time / 1000));
            values.add(value);
            timestamps.add((long) time);
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);
        double cutoffFrequency = 2;
        int order = 4;

        TimeSeriesData filteredResult = fourierService.filterTimeSeries(
                inputData, 0, cutoffFrequency, order, "lowpass"
        );

        assertEquals(-0.83528977, filteredResult.getValues().get(170), 0.001);
    }

    @Test
    void testHighPassFilterOnCompositeSineWave() {
        int numSamples = 1000;
        double samplingInterval = 10;
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i * samplingInterval;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000)) +
                    Math.sin(2 * Math.PI * 3 * (time / 1000));
            values.add(value);
            timestamps.add((long) time);
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);
        double samplingFrequency = 1000 / samplingInterval; // 100 Hz
        double cutoffFrequency = 2;
        int order = 4;

        TimeSeriesData filteredResult = fourierService.filterTimeSeries(
                inputData, cutoffFrequency, samplingFrequency / 2, order, "highpass"
        );

        assertEquals(0.5172870746696506, filteredResult.getValues().get(170), 0.001);
    }

    @Test
    void testBandPassFilterOnTripleSineWave() {
        int numSamples = 1000;
        double samplingInterval = 10;
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i * samplingInterval;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000)) +
                    Math.sin(2 * Math.PI * 3 * (time / 1000)) +
                    Math.sin(2 * Math.PI * 6 * (time / 1000));
            values.add(value);
            timestamps.add((long) time);
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);
        double lowCutoff = 2;
        double highCutoff = 4;
        int order = 4;

        TimeSeriesData filteredResult = fourierService.filterTimeSeries(
                inputData, lowCutoff, highCutoff, order, "bandpass"
        );

        assertEquals(-0.3061684830113329, filteredResult.getValues().get(220), 0.001);
    }

    @Test
    void testHighPassFilterOn1HzAnd5HzWave() {
        int numSamples = 1000;
        double samplingInterval = 5; // 5 milliseconds
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i * samplingInterval;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000)) + 0.5 * Math.sin(2 * Math.PI * 5 * (time / 1000));
            values.add(value);
            timestamps.add((long) time);
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);

        double samplingFrequency = 1000 / samplingInterval; // 200 Hz
        double cutoffFrequency = 3;
        int order = 4;
        TimeSeriesData filteredResult = fourierService.filterTimeSeries(inputData, cutoffFrequency, samplingFrequency / 2, order, "highpass");

        assertEquals(0.35621948221407745, filteredResult.getValues().get(215), 0.001);
    }

    @Test
    void testBandPassFilterOn1Hz10Hz100HzWave() {
        int numSamples = 10000;
        double samplingInterval = 0.5; // 0.5 milliseconds
        List<Double> values = new ArrayList<>();
        List<Long> timestamps = new ArrayList<>();

        for (int i = 0; i < numSamples; i++) {
            double time = i * samplingInterval;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000)) +
                    0.5 * Math.sin(2 * Math.PI * 10 * (time / 1000)) +
                    0.25 * Math.sin(2 * Math.PI * 100 * (time / 1000));
            values.add(value);
            timestamps.add((long) time);
        }

        TimeSeriesData inputData = new TimeSeriesData(values, timestamps);

        double lowCutoff = 5; // Hz
        double highCutoff = 15; // Hz
        int order = 6;
        TimeSeriesData filteredResult = fourierService.filterTimeSeries(inputData, lowCutoff, highCutoff, order, "bandpass");

        assertEquals(-0.40376562589104215, filteredResult.getValues().get(170), DELTA, "Band-pass filter test (1 Hz, 10 Hz, 100 Hz) failed");

    }
}