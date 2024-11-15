package com.example.monitoring.service;

import com.example.monitoring.dto.TimeSeriesPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class TimeSeriesGeneratorService {
    private static final Logger logger = LoggerFactory.getLogger(TimeSeriesGeneratorService.class);

    private double baseValue = 100.0;
    private final double volatility = 0.15; // Increased from 0.02 to 0.15
    private final double jumpProbability = 0.1; // 10% chance of price jump
    private final double maxJumpSize = 5.0; // Maximum size of random jumps
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
        double meanReversion = 0.05 * (100 - baseValue); // Reduced mean reversion force

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
}