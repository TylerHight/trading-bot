// shared-lib/src/main/java/com/example/shared/dto/FourierAnalysisDTO.java
package com.example.shared.dto;

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