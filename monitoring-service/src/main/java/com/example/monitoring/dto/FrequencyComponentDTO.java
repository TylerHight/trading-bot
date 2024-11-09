// monitoring-service/src/main/java/com/example/monitoring/dto/FrequencyComponentDTO.java
package com.example.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrequencyComponentDTO {
    private double frequency;
    private double magnitude;
}