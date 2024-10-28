package com.example.analysis.service;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.analysis.model.*;

@Service
public class FourierTransformerService {
    private static final Logger logger = LoggerFactory.getLogger(FourierTransformerService.class);
    private final FastFourierTransformer fft;

    public FourierTransformerService() {
        this.fft = new FastFourierTransformer(DftNormalization.STANDARD);
    }

    /**
     * Calculate the FFT of the input values. There is no magnitude normalization.
     *
     * @param values The list of values associated with the time series data.
     * @param timestamps The list of timestamps for the time series data.
     * @return The complex FFT result.
     */
    public Complex[] calculateFourierTransform(List<Double> values, List<Long> timestamps) {
        if (values.size() != timestamps.size()) {
            logger.error("Number of values and timestamps must be equal.");
            return new Complex[0];
        }

        int n = values.size();
        if (n == 0) {
            logger.warn("No values provided for Fourier Transform calculation.");
            return new Complex[0];
        }

        // Find the next power of two for padding
        int nextPowerOfTwo = 1;
        while (nextPowerOfTwo < n) {
            nextPowerOfTwo <<= 1;
        }
        logger.info("Zero-padding input values to the next power of two: {}", nextPowerOfTwo);

        // Create a padded array for FFT (zero-padding)
        double[] paddedValues = new double[nextPowerOfTwo];
        for (int i = 0; i < n; i++) {
            paddedValues[i] = values.get(i);
        }

        // Calculate the FFT
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] fftResult = fft.transform(paddedValues, TransformType.FORWARD);

        logger.info("Completed Fourier Transform calculation.");
        return fftResult;
    }

    /**
     * Calculate the sampling frequency from the timestamps.
     *
     * @param timestamps The list of timestamps for the time series data.
     * @return The sampling frequency in Hz.
     */
    public double calculateSamplingFrequency(List<Long> timestamps) {
        if (timestamps.size() < 2) {
            logger.error("Not enough timestamps to calculate sampling frequency.");
            return 0.0;
        }

        // Calculate sampling interval and frequency
        double samplingInterval = (timestamps.get(timestamps.size() - 1) - timestamps.get(0)) / (double) (timestamps.size() - 1);
        double samplingFrequency = 1000.0 / samplingInterval; // Convert to Hz
        logger.debug("Sampling interval: {} ms, Sampling frequency: {} Hz", samplingInterval, samplingFrequency);

        return samplingFrequency;
    }

    /**
     * Get frequency-magnitude pairs from the complex FFT result.
     *
     * @param fftResult The complex FFT result.
     * @param samplingFrequency The sampling frequency of the original data.
     * @return A list of Fourier Transform magnitudes and frequencies.
     */
    public List<Double[]> getFrequencyMagnitudePairs(Complex[] fftResult, double samplingFrequency) {
        if (samplingFrequency == 0) {
            logger.error("Invalid sampling frequency. Sampling frequency must not be 0.");
            return new ArrayList<>();
        }

        List<Double[]> fmPairs = new ArrayList<>();
        int halfLength = fftResult.length / 2; // Only get the first half

        for (int k = 0; k < halfLength; k++) {
            double magnitude = fftResult[k].abs(); // Magnitude
            double frequency = k * samplingFrequency / fftResult.length; // Frequency for each bin
            fmPairs.add(new Double[]{frequency, magnitude});
        }

        logger.info("Got {} frequency-magnitude pairs.", fmPairs.size());
        return fmPairs;
    }

    /**
     * Calculate the inverse FFT of a complex array to reconstruct the time series data.
     *
     * @param fftResult The complex array result of a forward FFT.
     * @param originalTimestamps The original timestamps of the input timeseries data.
     * @return A pair of lists: the first is the reconstructed values, the second is their corresponding timestamps.
     */
    public TimeSeriesData calculateInverseFFT(Complex[] fftResult, List<Long> originalTimestamps) {
        if (fftResult.length == 0 || originalTimestamps.isEmpty()) {
            logger.warn("Invalid input for Inverse FFT calculation.");
            return new TimeSeriesData(new ArrayList<>(), new ArrayList<>());
        }

        Complex[] ifftResult = fft.transform(fftResult, TransformType.INVERSE);
        double samplingInterval = (originalTimestamps.get(originalTimestamps.size() - 1) - originalTimestamps.get(0))
                / (double) (originalTimestamps.size() - 1);

        List<Double> reconstructedValues = new ArrayList<>();
        List<Long> reconstructedTimestamps = new ArrayList<>();
        long startTime = originalTimestamps.get(0);
        int originalSize = originalTimestamps.size();

        for (int i = 0; i < originalSize; i++) {
            reconstructedValues.add(ifftResult[i].getReal());
            reconstructedTimestamps.add(startTime + (long)(i * samplingInterval));
        }

        return new TimeSeriesData(reconstructedValues, reconstructedTimestamps);
    }

    /**
     * Apply a Butterworth filter to the FFT output.
     *
     * @param fftResult The complex FFT output.
     * @param samplingFrequency The sampling frequency of the original data.
     * @param lowCutoff The lower cutoff frequency for the filter.
     * @param highCutoff The higher cutoff frequency for the filter.
     * @param order The order of the Butterworth filter (higher order forms a sharper cutoff).
     * @param filterType The type of filter to apply: "lowpass", "highpass", or "bandpass".
     * @return The filtered complex FFT result.
     */
    public Complex[] applyButterworthFilter(Complex[] fftResult, double samplingFrequency, double lowCutoff, double highCutoff, int order, String filterType) {
        if (fftResult.length == 0 || samplingFrequency <= 0) {
            logger.error("Cannot apply Butterworth filter due to invalid input.");
            return new Complex[0];
        }

        Complex[] filteredData = new Complex[fftResult.length];
        int halfLength = fftResult.length / 2;

        for (int i = 0; i < fftResult.length; i++) {
            double frequency = i * samplingFrequency / fftResult.length;
            if (i > halfLength) {
                // Copy the frequencies for the second half
                frequency = samplingFrequency - frequency;
            }

            // Form filter response based on filter type
            double response = calculateFilterResponse(frequency, lowCutoff, highCutoff, order, filterType);

            // Filter the FFT output
            filteredData[i] = fftResult[i].multiply(response);
        }

        logger.info("Applied Butterworth {} filter to FFT result. Order: {}", filterType, order);
        return filteredData;
    }

    /**
     * Calculates the filter response for given frequency(s).
     *
     * @param frequency The frequency to calculate the response for.
     * @param lowCutoff The lower cutoff frequency for the filter.
     * @param highCutoff The higher cutoff frequency for the filter.
     * @param order The order of the Butterworth filter.
     * @param filterType The type of filter: "lowpass", "highpass", or "bandpass".
     * @return The filter response between 0 and 1.
     */
    private double calculateFilterResponse(double frequency, double lowCutoff, double highCutoff, int order, String filterType) {
        switch (filterType.toLowerCase()) {
            case "lowpass":
                return 1.0 / Math.sqrt(1.0 + Math.pow(frequency / highCutoff, 2 * order));
            case "highpass":
                return 1.0 / Math.sqrt(1.0 + Math.pow(lowCutoff / frequency, 2 * order));
            case "bandpass":
                double lowPassResponse = 1.0 / Math.sqrt(1.0 + Math.pow(frequency / highCutoff, 2 * order));
                double highPassResponse = 1.0 / Math.sqrt(1.0 + Math.pow(lowCutoff / frequency, 2 * order));
                return lowPassResponse * highPassResponse;
            default:
                logger.warn("Invalid choice for filter type '{}'. No filtering was applied.", filterType);
                return 1.0; // No filter
        }
    }

    /**
     * Performs an FFT, applies a Butterworth filter, and then performs an inverse
     * FFT to get  the filtered time series data.
     *
     * @param input The time series data values as a TimeSeriesData object.
     * @param lowCutoff The lower cutoff frequency.
     * @param highCutoff The higher cutoff frequency.
     * @param order The Butterworth filter order (higher forms sharper cutoff).
     * @param filterType The type of filter to use: "lowpass", "highpass", or "bandpass".
     * @return A pair of lists: the first list is the filtered values, the second list is the timestamps for the values.
     */
    public TimeSeriesData filterTimeSeries(TimeSeriesData input, double lowCutoff,
                                           double highCutoff, int order, String filterType) {
        Complex[] fftResult = calculateFourierTransform(input.getValues(), input.getTimestamps());
        double samplingFrequency = calculateSamplingFrequency(input.getTimestamps());
        Complex[] filteredFFT = applyButterworthFilter(fftResult, samplingFrequency,
                lowCutoff, highCutoff, order, filterType);
        return calculateInverseFFT(filteredFFT, input.getTimestamps());
    }
}