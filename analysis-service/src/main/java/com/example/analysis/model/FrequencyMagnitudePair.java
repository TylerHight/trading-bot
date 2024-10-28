package com.example.analysis.model;

public class FrequencyMagnitudePair {
    private Double frequency;
    private Double magnitude;

    // Default constructor
    public FrequencyMagnitudePair() {}

    // Constructor with parameters
    public FrequencyMagnitudePair(Double frequency, Double magnitude) {
        this.frequency = frequency;
        this.magnitude = magnitude;
    }

    // Getters and setters
    public Double getFrequency() { return frequency; }
    public void setFrequency(Double frequency) { this.frequency = frequency; }
    public Double getMagnitude() { return magnitude; }
    public void setMagnitude(Double magnitude) { this.magnitude = magnitude; }
}