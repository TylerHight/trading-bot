// analysis-service/src/main/java/com/example/analysis/dto/TimeSeriesDataDTO.java
package com.example.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSeriesDataDTO {
    private List<Double> values;
    private List<Long> timestamps;
}