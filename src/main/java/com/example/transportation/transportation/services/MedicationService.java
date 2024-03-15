package com.example.transportation.transportation.services;


import com.example.transportation.transportation.dto.MedicationDTO;
import com.example.transportation.transportation.models.Medication;

import java.io.IOException;
import java.util.List;

public interface MedicationService {
    List<MedicationDTO> getAllMedications();
    List<MedicationDTO> getMedicationForEvtol(String serialNumber);
    MedicationDTO addNewMedication(Medication medication);
    MedicationDTO loadMedicationToEvtol(String serialNumber, Medication medication) throws IOException;
}
