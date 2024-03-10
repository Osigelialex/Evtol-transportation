package com.example.transportation.transportation.controllers;

import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.models.Medication;
import com.example.transportation.transportation.services.EvtolService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/evtol")
public class EvtolController {

    EvtolService evtolService;

    public EvtolController(EvtolService evtolService) {
        this.evtolService = evtolService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerEvtol(@RequestBody Evtol evtol) {
        return evtolService.registerEvtol(evtol);
    }

    @GetMapping("")
    public ResponseEntity<Object> getAvailableEvtols() {
        return evtolService.getAllAvailableVtols();
    }

    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<Object> getEvtolBatteryInformation(@PathVariable String serialNumber) {
        return evtolService.getEvtolBatteryInformation(serialNumber);
    }

    @GetMapping("/{serialNumber}/medications")
    public ResponseEntity<Object> getEvtolLoadedMedications(@PathVariable String serialNumber) {
        return evtolService.getEvtolLoadedMedications(serialNumber);
    }

    @PostMapping(value = "/{serialNumber}/load", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> loadEvtolWithMedication(
            @PathVariable String serialNumber,
            @RequestPart Medication medication,
            @RequestPart MultipartFile multipartFile
    ) throws IOException {
        return evtolService.loadEvtolMedications(serialNumber, medication, multipartFile);
    }

    @GetMapping("/images/{imageName}")
    @ResponseBody
    public ResponseEntity<byte[]> getMedicationImage(@PathVariable String imageName) throws IOException {
        return evtolService.getMedicationImage(imageName);
    }
}
