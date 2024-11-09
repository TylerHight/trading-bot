// analysis-service/src/main/java/com/example/analysis/dto/FrequencyComponentDTO.java
package com.example.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrequencyComponentDTO {
    private double frequency;
    private double magnitude;
}