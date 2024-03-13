package com.example.transportation.transportation.controllers;

import com.example.transportation.transportation.dto.BatteryDTO;
import com.example.transportation.transportation.dto.EvtolDTO;
import com.example.transportation.transportation.dto.MedicationDTO;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.models.Medication;
import com.example.transportation.transportation.services.EvtolService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class EvtolController {

    @Autowired
    EvtolService evtolService;

    @PostMapping("/evtol/register")
    public ResponseEntity<EvtolDTO> registerEvtol(@RequestBody Evtol evtol) {
        EvtolDTO evtolDTO = evtolService.registerEvtol(evtol);
        return new ResponseEntity<>(evtolDTO, HttpStatus.CREATED);
    }

    @GetMapping("/evtol")
    public ResponseEntity<List<EvtolDTO>> getAvailableEvtols() {
        List<EvtolDTO> availableEvtols = evtolService.getAllAvailableVtols();
        return new ResponseEntity<>(availableEvtols, HttpStatus.OK);
    }

    @GetMapping("/evtol/{serialNumber}/battery")
    public ResponseEntity<BatteryDTO> getEvtolBatteryInformation(@PathVariable String serialNumber) {
        BatteryDTO batteryDTO = evtolService.getBatteryInformation(serialNumber);
        return new ResponseEntity<>(batteryDTO, HttpStatus.OK);
    }

    @GetMapping("/evtol/{serialNumber}/medications")
    public ResponseEntity<List<MedicationDTO>> getEvtolLoadedMedications(@PathVariable String serialNumber) {
        List<MedicationDTO> loadedMedications = evtolService.getLoadedMedications(serialNumber);
        return new ResponseEntity<>(loadedMedications, HttpStatus.OK);
    }

    @PostMapping(value = "/evtol/{serialNumber}/load", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MedicationDTO> loadEvtolWithMedication(
            @PathVariable String serialNumber,
            @RequestPart Medication medication,
            @RequestPart MultipartFile multipartFile
    ) throws IOException {
        MedicationDTO medicationDTO = evtolService.loadEvtolMedications(serialNumber, medication, multipartFile);
        return new ResponseEntity<>(medicationDTO, HttpStatus.CREATED);
    }

    @GetMapping("/evtol/images/{imageName}")
    @ResponseBody
    public ResponseEntity<byte[]> getMedicationImage(@PathVariable String imageName) throws IOException {
        return evtolService.getMedicationImage(imageName);
    }
}
