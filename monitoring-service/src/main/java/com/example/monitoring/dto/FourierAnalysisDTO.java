// monitoring-service/src/main/java/com/example/monitoring/dto/FourierAnalysisDTO.java
package com.example.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FourierAnalysisDTO {
    private List<FrequencyComponentDTO> components;
    private double samplingFrequency;
}



