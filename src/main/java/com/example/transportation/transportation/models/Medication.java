package com.example.transportation.transportation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Medication {

    @ManyToOne
    @JoinColumn(name = "evtolSerialNumber")
    private Evtol evtol;

    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String name;

    private double weight;

    @Id
    @Pattern(regexp = "^[A-Z0-9_]*$")
    private String code;

    private String medicalImageUrl;

    public Medication() {}

    public Medication(String name, double weight, String code, String medicalImageUrl) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.medicalImageUrl = medicalImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setEvtol(Evtol evtol) {
        this.evtol = evtol;
    }

    public String getMedicalImageUrl() {
        return medicalImageUrl;
    }

    public void setMedicalImageUrl(String medicalImageUrl) {
        this.medicalImageUrl = medicalImageUrl;
    }
}
