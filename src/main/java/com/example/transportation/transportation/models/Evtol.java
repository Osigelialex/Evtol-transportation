package com.example.transportation.transportation.models;

import com.example.transportation.transportation.enums.EvtolModel;
import com.example.transportation.transportation.enums.EvtolState;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
public class Evtol {

    @Id
    @Size(min=1, max=100)
    private String serialNumber = generateSerialNumber();

    @Enumerated(EnumType.STRING)
    private EvtolModel model;

    @Max(500)
    private double weightLimit;

    @Min(0)
    @Max(100)
    private Integer percentage = 100;

    @Enumerated(EnumType.STRING)
    private EvtolState state = EvtolState.IDLE;

    @OneToMany(mappedBy = "evtol")
    private List<Medication> medications;

    public Evtol() {}

    public Evtol(EvtolModel model, double weightLimit) {
        this.model = model;
        this.weightLimit = weightLimit;
    }

    public String generateSerialNumber() {
        StringBuilder SERIAL_NUMBER = new StringBuilder();
        int SERIAL_NUMBER_LENGTH = 10;

        SERIAL_NUMBER.append("EV-");
        String numericChars = "0123456789";

        for (int i = 0; i < SERIAL_NUMBER_LENGTH; i++) {
            int index = (int) (Math.random() * numericChars.length());
            SERIAL_NUMBER.append(numericChars.charAt(index));
        }

        return SERIAL_NUMBER.toString();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public EvtolModel getModel() {
        return model;
    }

    public void setModel(EvtolModel model) {
        this.model = model;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public EvtolState getState() {
        return state;
    }

    public void setState(EvtolState state) {
        this.state = state;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void addMedication(Medication medication) {
        this.medications.add(medication);
    }
}
