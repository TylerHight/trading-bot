package com.example.analysis.model;

import java.util.List;

public class TimeSeriesData {
    private List<Double> values;
    private List<Long> timestamps;

    // Default constructor
    public TimeSeriesData() {}

    // Constructor with parameters
    public TimeSeriesData(List<Double> values, List<Long> timestamps) {
        this.values = values;
        this.timestamps = timestamps;
    }

    // Getters and setters
    public List<Double> getValues() { return values; }
    public void setValues(List<Double> values) { this.values = values; }
    public List<Long> getTimestamps() { return timestamps; }
    public void setTimestamps(List<Long> timestamps) { this.timestamps = timestamps; }
}