package com.example.monitoring.dto;

import java.time.Instant;

public class TimeSeriesPoint {
    private Instant timestamp;
    private double value;

    public TimeSeriesPoint(Instant timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    // Getters and setters
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}