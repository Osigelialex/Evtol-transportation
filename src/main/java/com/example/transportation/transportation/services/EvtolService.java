package com.example.transportation.transportation.services;

import com.example.transportation.transportation.dto.BatteryDTO;
import com.example.transportation.transportation.dto.EvtolDTO;
import com.example.transportation.transportation.dto.EvtolDetailDTO;
import com.example.transportation.transportation.dto.MedicationDTO;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.models.Medication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EvtolService {
    public EvtolDTO registerEvtol(Evtol evtol);
    public EvtolDetailDTO getEvtolDetail(String serialNumber);
    public List<EvtolDTO> getLoadedEvtols();
    public List<MedicationDTO> getLoadedMedications(String serialNumber);
    public List<EvtolDTO> getAllEvtols();
    public List<EvtolDTO> getAllAvailableVtols();
    public ResponseEntity<byte[]> getMedicationImage(String imageName) throws IOException;
    public void updateBatteryPercentage();
}