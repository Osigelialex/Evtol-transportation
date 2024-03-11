package com.example.transportation.transportation.models;

import com.example.transportation.transportation.enums.EvtolModel;
import com.example.transportation.transportation.enums.EvtolState;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Evtol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=1, max=100)
    @lombok.NonNull
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @lombok.NonNull
    private EvtolModel model;

    @Max(500)
    @lombok.NonNull
    private Integer weightLimit;

    @Min(0)
    @Max(100)
    private Integer percentage = 100;

    @Enumerated(EnumType.STRING)
    private EvtolState state = EvtolState.IDLE;

    @OneToMany(mappedBy = "evtol", cascade = CascadeType.ALL)
    private List<Medication> medications;

    public void addMedication(Medication medication) {
        medications.add(medication);
    }
}
