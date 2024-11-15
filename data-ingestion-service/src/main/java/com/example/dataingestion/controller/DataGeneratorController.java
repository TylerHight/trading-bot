// data-ingestion-service/src/main/java/com/example/dataingestion/controller/DataGeneratorController.java
package com.example.dataingestion.controller;

import com.example.dataingestion.dto.TimeSeriesPoint;
import com.example.dataingestion.service.TimeSeriesGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/data")
public class DataGeneratorController {
    private final TimeSeriesGeneratorService generatorService;

    @Autowired
    public DataGeneratorController(TimeSeriesGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @GetMapping("/timeseries")
    public List<TimeSeriesPoint> getTimeSeriesData(
            @RequestParam(value = "points", defaultValue = "20") int numberOfPoints) {
        return generatorService.generateTimeSeriesData(numberOfPoints);
    }

    @GetMapping("/latest")
    public TimeSeriesPoint getLatestDataPoint() {
        return generatorService.generateDataPoint();
    }
}