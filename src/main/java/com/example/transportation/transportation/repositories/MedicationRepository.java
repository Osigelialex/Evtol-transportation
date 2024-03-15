package com.example.transportation.transportation.repositories;

import com.example.transportation.transportation.dto.MedicationDTO;
import com.example.transportation.transportation.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, String> {
    List<Medication> findAllByEvtolSerialNumber(String serialNumber);
}
