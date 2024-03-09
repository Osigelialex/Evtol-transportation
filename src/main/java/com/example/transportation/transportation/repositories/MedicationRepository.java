package com.example.transportation.transportation.repositories;

import com.example.transportation.transportation.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, String> {
}
