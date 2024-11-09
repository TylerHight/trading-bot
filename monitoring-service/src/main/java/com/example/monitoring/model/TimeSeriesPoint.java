package com.example.monitoring.model;

public class TimeSeriesPoint {
    private String timestamp;
    private double price;

    public TimeSeriesPoint() {}

    public TimeSeriesPoint(String timestamp, double price) {
        this.timestamp = timestamp;
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}