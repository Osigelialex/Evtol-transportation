package com.example.transportation.transportation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;

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

    public Medication() {}

    public Medication(String name, double weight, String code) {
        this.name = name;
        this.weight = weight;
        this.code = code;
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
}
