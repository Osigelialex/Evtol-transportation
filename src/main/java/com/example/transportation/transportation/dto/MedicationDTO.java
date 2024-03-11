package com.example.transportation.transportation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationDTO {
    private String name;
    private String weight;
    private String medicationUrl;
    private String code;
}
