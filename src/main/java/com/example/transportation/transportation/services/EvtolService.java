package com.example.transportation.transportation.services;

import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.models.Medication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EvtolService {
    public ResponseEntity<Object> registerEvtol(Evtol evtol);

    public ResponseEntity<Object> getEvtolBatteryInformation(String serialNumber);

    public ResponseEntity<Object> getEvtolLoadedMedications(String serialNumber);

    public ResponseEntity<Object> loadEvtolMedications(String serialNumber,
                                                       Medication medication,
                                                       MultipartFile multipartFile) throws IOException;

    public ResponseEntity<Object> getAllAvailableVtols();

    public ResponseEntity<byte[]> getMedicationImage(String imageName) throws IOException;

    public void updateBatteryPercentage();
}
