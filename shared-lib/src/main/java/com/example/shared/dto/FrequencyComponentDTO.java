// shared-lib/src/main/java/com/example/shared/dto/FrequencyComponentDTO.java
package com.example.shared.dto;

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