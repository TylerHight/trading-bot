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
    private final double volatility = 0.02;
    private final Random random = new Random();

    public TimeSeriesPoint generateDataPoint() {
        // Random walk with mean reversion
        double change = random.nextGaussian() * volatility;
        double meanReversion = 0.1 * (100 - baseValue);
        baseValue = baseValue + change + meanReversion;

        TimeSeriesPoint point = new TimeSeriesPoint(
                Instant.now(),
                Math.round(baseValue * 100.0) / 100.0
        );

        logger.debug("Generated data point: {}", point);
        return point;
    }
}