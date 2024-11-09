// shared-lib/src/main/java/com/example/shared/dto/TimeSeriesDataDTO.java
package com.example.shared.dto;

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