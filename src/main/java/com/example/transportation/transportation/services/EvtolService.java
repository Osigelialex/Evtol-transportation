package com.example.transportation.transportation.services;

import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.models.Medication;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface EvtolService {
    public ResponseEntity<Object> registerEvtol(Evtol evtol);

    public ResponseEntity<Object> getEvtolBatteryInformation(String serialNumber);

    public ResponseEntity<Object> getEvtolLoadedMedications(String serialNumber);

    public ResponseEntity<Object> loadEvtolMedications(String serialNumber, Medication medication);

    public ResponseEntity<Object> getAllAvailableVtols();

    public void updateBatteryPercentage();
}
