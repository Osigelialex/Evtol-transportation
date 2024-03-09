package com.example.transportation.transportation.controllers;

import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.models.Medication;
import com.example.transportation.transportation.services.EvtolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{serialNumber}/load")
    public ResponseEntity<Object> loadEvtolWithMedication(
            @PathVariable String serialNumber,
            @RequestBody Medication medication
    ) {
        return evtolService.loadEvtolMedications(serialNumber, medication);
    }
}
