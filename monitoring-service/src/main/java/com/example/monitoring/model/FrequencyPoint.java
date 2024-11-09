package com.example.monitoring.model;

public class FrequencyPoint {
    private double frequency;
    private double magnitude;

    public FrequencyPoint() {}

    public FrequencyPoint(double frequency, double magnitude) {
        this.frequency = frequency;
        this.magnitude = magnitude;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
}