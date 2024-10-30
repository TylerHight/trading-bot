package com.example.monitoring.visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

/**
 * Service for plotting Fourier Transform results using JFreeChart.
 * This service provides visualization capabilities for frequency analysis data.
 */
@Service
public class FourierPlotter {

    /**
     * Plots the Fourier transform results in a new window.
     *
     * @param result List of frequency-magnitude pairs to plot
     * @param title  The title of the chart
     */
    public void plotFourierTransform(List<Double[]> result, String title) {
        SwingUtilities.invokeLater(() -> {
            // Initialize dataset for the chart
            XYSeries series = new XYSeries("Frequency vs Magnitude");

            // Add the values to the chart dataset
            for (Double[] pair : result) {
                double frequency = pair[0];
                double magnitude = pair[1];
                series.add(frequency, magnitude);
            }

            XYSeriesCollection dataset = new XYSeriesCollection(series);

            // Create the chart
            JFreeChart chart = ChartFactory.createXYLineChart(
                    title,            // Title
                    "Frequency (Hz)", // X-Axis label
                    "Magnitude",      // Y-Axis label
                    dataset,          // Data
                    PlotOrientation.VERTICAL,
                    true, true, false
            );

            // Display the chart in a JFrame
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to DISPOSE_ON_CLOSE
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
            frame.setContentPane(chartPanel);
            frame.pack();
            frame.setLocationRelativeTo(null); // Center on screen
            frame.setVisible(true);
        });
    }

    /**
     * Creates a chart without displaying it. Useful for saving to file or embedding in other components.
     *
     * @param result List of frequency-magnitude pairs
     * @param title  Chart title
     * @return The created JFreeChart object
     */
    public JFreeChart createFourierTransformChart(List<Double[]> result, String title) {
        XYSeries series = new XYSeries("Frequency vs Magnitude");

        for (Double[] pair : result) {
            series.add(pair[0], pair[1]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        return ChartFactory.createXYLineChart(
                title,
                "Frequency (Hz)",
                "Magnitude",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
    }
}