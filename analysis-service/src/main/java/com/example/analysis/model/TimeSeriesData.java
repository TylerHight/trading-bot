package com.example.analysis.model;

import java.util.List;
import java.util.ArrayList;

public class TimeSeriesData {
    private final List<Double> values;
    private final List<Long> timestamps;

    public TimeSeriesData(List<Double> values, List<Long> timestamps) {
        if (values.size() != timestamps.size()) {
            throw new IllegalArgumentException("Values and timestamps must have the same size");
        }
        this.values = new ArrayList<>(values);
        this.timestamps = new ArrayList<>(timestamps);
    }

    public List<Double> getValues() {
        return new ArrayList<>(values);
    }

    public List<Long> getTimestamps() {
        return new ArrayList<>(timestamps);
    }
}