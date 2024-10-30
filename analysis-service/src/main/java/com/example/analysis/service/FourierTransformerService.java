package com.example.analysis.service;

import com.example.analysis.model.TimeSeriesData;
import com.example.analysis.service.FourierTransformer;
import org.apache.commons.math3.complex.Complex;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class FourierTransformerService {
    private final FourierTransformer fourierTransformer;

    public FourierTransformerService() {
        this.fourierTransformer = new FourierTransformer();
    }

    public Complex[] calculateFourierTransform(List<Double> values, List<Long> timestamps) {
        return fourierTransformer.calculateFourierTransform(values, timestamps);
    }

    public double calculateSamplingFrequency(List<Long> timestamps) {
        return fourierTransformer.calculateSamplingFrequency(timestamps);
    }

    public List<Double[]> getFrequencyMagnitudePairs(Complex[] fftResult, double samplingFrequency) {
        return fourierTransformer.getFrequencyMagnitudePairs(fftResult, samplingFrequency);
    }

    public TimeSeriesData calculateInverseFFT(Complex[] fftResult, List<Long> originalTimestamps) {
        List<List<Double>> result = fourierTransformer.calculateInverseFFT(fftResult, originalTimestamps);
        List<Long> reconstructedTimestamps = result.get(1).stream()
                .mapToLong(Math::round)
                .boxed()
                .collect(Collectors.toList());
        return new TimeSeriesData(result.get(0), reconstructedTimestamps);
    }

    public TimeSeriesData filterTimeSeries(TimeSeriesData input, double lowCutoff,
                                           double highCutoff, int order, String filterType) {
        List<List<Double>> result = fourierTransformer.filterTimeSeries(
                input.getValues(), input.getTimestamps(),
                lowCutoff, highCutoff, order, filterType);
        List<Long> filteredTimestamps = result.get(1).stream()
                .mapToLong(Math::round)
                .boxed()
                .collect(Collectors.toList());
        return new TimeSeriesData(result.get(0), filteredTimestamps);
    }
}