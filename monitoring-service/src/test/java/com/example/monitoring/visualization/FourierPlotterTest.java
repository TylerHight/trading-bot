package com.example.monitoring.visualization;

import com.example.analysis.service.TimeSeriesAnalysis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Fourier Plotter Integration Tests")
public class FourierPlotterTest {

    private TimeSeriesAnalysis timeSeries;
    private static final int EXPECTED_FREQUENCY_BINS = 512;
    private static final int SAMPLE_RATE = 100; // 100 Hz
    private static final int SAMPLE_INTERVAL = 10; // 10 milliseconds
    private static final int SAMPLE_COUNT = 1000;
    private static final int DISPLAY_TIME = 10000; // 10 seconds

    @BeforeEach
    public void setup() {
        timeSeries = new TimeSeriesAnalysis(3, 5);
    }

    @Test
    @DisplayName("Should correctly plot single sine wave")
    public void testSingleSineWave() throws InterruptedException {
        // Generate 1 Hz sine wave
        generateSineWave(1.0);

        List<Double[]> result = timeSeries.getFrequencyMagnitudePairs();
        assertEquals(EXPECTED_FREQUENCY_BINS, result.size(),
                "Expected " + EXPECTED_FREQUENCY_BINS + " frequency bins after FFT");

        FourierPlotter.plotFourierTransform(result, "Single Sine Wave at 1 Hz");
        Thread.sleep(DISPLAY_TIME); // Display window for inspection
    }

    @Test
    @DisplayName("Should handle empty input gracefully")
    public void testEmptyInput() throws InterruptedException {
        List<Double[]> result = timeSeries.getFrequencyMagnitudePairs();
        assertEquals(0, result.size(),
                "Expected no frequency bins for empty time series");

        FourierPlotter.plotFourierTransform(result, "Empty List");
        Thread.sleep(DISPLAY_TIME);
    }

    @Test
    @DisplayName("Should correctly plot multiple sine waves")
    public void testMultipleSineWaves() throws InterruptedException {
        // Generate composite wave (1 Hz + 3 Hz)
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            double time = i * SAMPLE_INTERVAL;
            double value = Math.sin(2 * Math.PI * 1 * (time / 1000)) +
                    Math.sin(2 * Math.PI * 3 * (time / 1000));
            timeSeries.addPrice(value, (long) time);
        }

        List<Double[]> result = timeSeries.getFrequencyMagnitudePairs();
        assertEquals(EXPECTED_FREQUENCY_BINS, result.size(),
                "Expected " + EXPECTED_FREQUENCY_BINS + " frequency bins after FFT");

        FourierPlotter.plotFourierTransform(result, "Multiple Sine Waves at 1 Hz and 3 Hz");
        Thread.sleep(DISPLAY_TIME);
    }

    private void generateSineWave(double frequency) {
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            double time = i * SAMPLE_INTERVAL;
            double value = Math.sin(2 * Math.PI * frequency * (time / 1000));
            timeSeries.addPrice(value, (long) time);
        }
    }
}