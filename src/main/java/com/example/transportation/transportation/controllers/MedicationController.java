package com.example.transportation.transportation.controllers;

import com.example.transportation.transportation.dto.MedicationDTO;
import com.example.transportation.transportation.models.Medication;
import com.example.transportation.transportation.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medications")
@CrossOrigin(origins = "http://localhost:5173")
public class MedicationController {

    @Autowired
    MedicationService medicationService;

    @GetMapping
    public ResponseEntity<List<MedicationDTO>> getAllMedications() {
        List<MedicationDTO> medicationDTOS = medicationService.getAllMedications();
        return new ResponseEntity<>(medicationDTOS, HttpStatus.OK);
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<List<MedicationDTO>> getMedicationForEvtol(@PathVariable String serialNumber) {
        List<MedicationDTO> medicationDTOS = medicationService.getMedicationForEvtol(serialNumber);
        return new ResponseEntity<>(medicationDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicationDTO> addNewMedication(@RequestBody Medication medication) {
        MedicationDTO medicationDTO = medicationService.addNewMedication(medication);
        return new ResponseEntity<>(medicationDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/{serialNumber}/load")
    public ResponseEntity<MedicationDTO> loadEvtolWithMedication(
            @PathVariable String serialNumber,
            @RequestBody Medication medication
    ) throws IOException {
        MedicationDTO medicationDTO = medicationService.loadMedicationToEvtol(serialNumber, medication);
        return new ResponseEntity<>(medicationDTO, HttpStatus.CREATED);
    }
}
