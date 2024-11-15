// data-ingestion-service/src/main/java/com/example/dataingestion/dto/TimeSeriesPoint.java
package com.example.dataingestion.dto;

import java.time.Instant;

public class TimeSeriesPoint {
    private final Instant timestamp;
    private final double value;

    public TimeSeriesPoint(Instant timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TimeSeriesPoint{" +
                "timestamp=" + timestamp +
                ", value=" + value +
                '}';
    }
}