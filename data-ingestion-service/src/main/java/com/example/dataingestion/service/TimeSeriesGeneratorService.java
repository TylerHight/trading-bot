// data-ingestion-service/src/main/java/com/example/dataingestion/service/TimeSeriesGeneratorService.java
package com.example.dataingestion.service;

import com.example.dataingestion.dto.TimeSeriesPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TimeSeriesGeneratorService {
    private static final Logger logger = LoggerFactory.getLogger(TimeSeriesGeneratorService.class);

    private double baseValue = 100.0;
    private final double volatility = 0.15;
    private final double jumpProbability = 0.1;
    private final double maxJumpSize = 5.0;
    private final Random random = new Random();

    public TimeSeriesPoint generateDataPoint() {
        // Add sudden jumps occasionally
        if (random.nextDouble() < jumpProbability) {
            double jump = (random.nextDouble() * 2 - 1) * maxJumpSize;
            baseValue += jump;
            logger.debug("Price jump occurred: {}", jump);
        }

        // Random walk with increased volatility and reduced mean reversion
        double change = random.nextGaussian() * volatility * baseValue;
        double meanReversion = 0.05 * (100 - baseValue);

        // Add some non-linear noise
        double noise = Math.sin(baseValue / 10) * volatility * baseValue;

        baseValue = baseValue + change + meanReversion + noise;

        // Ensure price doesn't go negative
        baseValue = Math.max(baseValue, 1.0);

        TimeSeriesPoint point = new TimeSeriesPoint(
                Instant.now(),
                Math.round(baseValue * 100.0) / 100.0
        );

        logger.debug("Generated data point: {}", point);
        return point;
    }

    // New method to generate a list of points for REST API
    public List<TimeSeriesPoint> generateTimeSeriesData(int numberOfPoints) {
        List<TimeSeriesPoint> points = new ArrayList<>();
        for (int i = 0; i < numberOfPoints; i++) {
            points.add(generateDataPoint());
            // Small delay to simulate time passing
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Data generation interrupted", e);
            }
        }
        return points;
    }

    // Reset method for testing purposes
    public void resetBaseValue() {
        this.baseValue = 100.0;
    }
}