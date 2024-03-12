package com.example.transportation.transportation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicationId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Evtol evtol;

    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String name;

    private double weight;

    @Pattern(regexp = "^[A-Z0-9_]*$")
    private String code;

    private String medicalImageUrl;
}