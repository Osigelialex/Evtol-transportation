package com.example.transportation.transportation.dto;

import com.example.transportation.transportation.enums.EvtolState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvtolDTO {
    private String serialNumber;
    private String model;
    private Double weightLimit;
    private Integer percentage;
    private EvtolState state;
}
