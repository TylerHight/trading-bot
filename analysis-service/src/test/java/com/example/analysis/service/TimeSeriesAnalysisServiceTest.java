package com.example.analysis.service;

import com.example.analysis.model.TimeSeriesData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeSeriesAnalysisServiceTest {

    @Mock
    private FourierTransformerService fourierTransformer;

    private TimeSeriesAnalysisService service;
    private static final double DELTA = 1e-6;

    @BeforeEach
    void setUp() {
        service = new TimeSeriesAnalysisService(fourierTransformer);
    }

    @Test
    void testGetOrCreateAnalysis() {
        String symbol = "AAPL";

        TimeSeriesAnalysis analysis = service.getOrCreateAnalysis(symbol);
        assertNotNull(analysis);

        // Should return the same instance for the same symbol
        TimeSeriesAnalysis analysis2 = service.getOrCreateAnalysis(symbol);
        assertSame(analysis, analysis2);

        // Should create new instance for different symbol
        TimeSeriesAnalysis analysis3 = service.getOrCreateAnalysis("GOOGL");
        assertNotSame(analysis, analysis3);
    }

    @Test
    void testAddPrice() {
        String symbol = "AAPL";
        service.addPrice(symbol, 150.0, 1000L);
        service.addPrice(symbol, 151.0, 2000L);
        service.addPrice(symbol, 152.0, 3000L);

        TimeSeriesAnalysis analysis = service.getOrCreateAnalysis(symbol);
        List<Double> values = analysis.getValues();
        assertEquals(3, values.size());
        assertEquals(150.0, values.get(0), DELTA);
        assertEquals(151.0, values.get(1), DELTA);
        assertEquals(152.0, values.get(2), DELTA);
    }

    @Test
    void testGetLastSMA() {
        String symbol = "AAPL";

        // Add more than enough prices to get SMA (need 20 values)
        for (int i = 0; i < 25; i++) {
            service.addPrice(symbol, 150.0 + i, 1000L + i * 1000);
        }

        Double sma = service.getLastSMA(symbol);
        assertNotNull(sma);
        assertTrue(sma > 150.0 && sma < 175.0); // Should be in the middle range
    }

    @Test
    void testGetLastEMA() {
        String symbol = "AAPL";

        // Add more than enough prices to get EMA (need 50 values)
        for (int i = 0; i < 55; i++) {
            service.addPrice(symbol, 150.0 + i, 1000L + i * 1000);
        }

        Double ema = service.getLastEMA(symbol);
        assertNotNull(ema);
        assertTrue(ema > 150.0 && ema < 205.0); // Should be in the range
    }

    @Test
    void testGetFourierAnalysis() {
        String symbol = "AAPL";
        List<Double[]> expectedResult = Arrays.asList(
                new Double[]{1.0, 1.0},
                new Double[]{2.0, 0.1}
        );

        when(fourierTransformer.getFrequencyMagnitudePairs(anyList(), anyDouble()))
                .thenReturn(expectedResult);

        // Add some test data
        for (int i = 0; i < 10; i++) {
            double time = i / 10.0;
            service.addPrice(symbol, Math.sin(2 * Math.PI * time), (long)(time * 1000));
        }

        List<Double[]> result = service.getFourierAnalysis(symbol);
        assertNotNull(result);
        assertEquals(expectedResult.size(), result.size());
    }

    @Test
    void testGetTimeSeriesData() {
        String symbol = "AAPL";
        service.addPrice(symbol, 150.0, 1000L);
        service.addPrice(symbol, 151.0, 2000L);
        service.addPrice(symbol, 152.0, 3000L);

        TimeSeriesData data = service.getTimeSeriesData(symbol);
        assertNotNull(data);
        assertEquals(3, data.getValues().size());
        assertEquals(3, data.getTimestamps().size());
        assertEquals(150.0, data.getValues().get(0), DELTA);
        assertEquals(1000L, data.getTimestamps().get(0));
    }

    @Test
    void testMultipleSymbols() {
        // Test that different symbols maintain separate analysis
        service.addPrice("AAPL", 150.0, 1000L);
        service.addPrice("GOOGL", 2500.0, 1000L);
        service.addPrice("AAPL", 151.0, 2000L);
        service.addPrice("GOOGL", 2510.0, 2000L);

        TimeSeriesData appleData = service.getTimeSeriesData("AAPL");
        TimeSeriesData googleData = service.getTimeSeriesData("GOOGL");

        assertEquals(150.0, appleData.getValues().get(0), DELTA);
        assertEquals(2500.0, googleData.getValues().get(0), DELTA);
        assertEquals(151.0, appleData.getValues().get(1), DELTA);
        assertEquals(2510.0, googleData.getValues().get(1), DELTA);
    }
}