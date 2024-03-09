package com.example.transportation.transportation.services.impl;

import com.example.transportation.transportation.enums.EvtolState;
import com.example.transportation.transportation.exception.EvtolDuplicateException;
import com.example.transportation.transportation.exception.EvtolNotFoundException;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.models.Medication;
import com.example.transportation.transportation.repositories.EvtolRepository;
import com.example.transportation.transportation.repositories.MedicationRepository;
import com.example.transportation.transportation.response.ResponseHandler;
import com.example.transportation.transportation.services.EvtolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvtolServiceImpl implements EvtolService {

    EvtolRepository evtolRepository;
    MedicationRepository medicationRepository;

    public EvtolServiceImpl(EvtolRepository evtolrepository, MedicationRepository medicationRepository) {
        this.evtolRepository = evtolrepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public ResponseEntity<Object> registerEvtol(Evtol evtol) {
        if (evtolRepository.findById(evtol.getSerialNumber()).isPresent()) {
            throw new EvtolDuplicateException("Serial number already exists");
        }

        evtolRepository.save(evtol);

        return ResponseHandler.responseBuilder("evtol registered successfully", HttpStatus.OK, evtol);
    }

    @Override
    public ResponseEntity<Object> getEvtolBatteryInformation(String serialNumber) {
        Optional<Evtol> evtolOptional = evtolRepository.findById(serialNumber);

        if (evtolOptional.isEmpty()) {
            throw new EvtolNotFoundException("The requested evtol could not be found");
        }

        int evtolBatteryPercentage = evtolOptional.get().getPercentage();

        return ResponseHandler.responseBuilder("success", HttpStatus.OK, evtolBatteryPercentage);
    }

    @Override
    public ResponseEntity<Object> getEvtolLoadedMedications(String serialNumber) {
        Optional<Evtol> evtolOptional = evtolRepository.findById(serialNumber);

        if (evtolOptional.isEmpty()) {
            throw new EvtolNotFoundException("The requested evtol could not be found");
        }

        Evtol evtol = evtolOptional.get();

        return ResponseHandler.responseBuilder("success", HttpStatus.OK, evtol.getMedications());
    }

    @Override
    public ResponseEntity<Object> loadEvtolMedications(String serialNumber, Medication medication) {
        Optional<Evtol> evtolOptional = evtolRepository.findById(serialNumber);

        if (evtolOptional.isEmpty()) {
            throw new EvtolNotFoundException("The specified evtol could not be found");
        }

        Evtol evtol = evtolOptional.get();

        medication.setEvtol(evtol);
        medicationRepository.save(medication);

        evtol.addMedication(medication);
        evtolRepository.save(evtol);

        return ResponseHandler.responseBuilder("medications loaded successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllAvailableVtols() {
        List<Evtol> availableEvtols = evtolRepository.findAvailableEvtols();

        if (availableEvtols.isEmpty()) {
            throw new EvtolNotFoundException("No available evtols where found");
        }

        return ResponseHandler.responseBuilder("success", HttpStatus.OK, availableEvtols);
    }

    @Override
    @Scheduled(fixedRate = 100_000L)
    public void updateBatteryPercentage() {
        List<Evtol> evtols = evtolRepository.findAll();

        for (Evtol evtol : evtols) {
            evtol.setPercentage(Math.max(evtol.getPercentage() - 1, 0));

            if (evtol.getPercentage() < 25 && evtol.getState().equals(EvtolState.LOADING)) {
                evtol.setState(EvtolState.IDLE);
            }

            if (evtol.getPercentage() == 0) {
                evtol.setPercentage(100);
            }

            evtolRepository.save(evtol);
        }
    }
}
