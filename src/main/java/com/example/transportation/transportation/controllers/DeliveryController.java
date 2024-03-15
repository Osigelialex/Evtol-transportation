package com.example.transportation.transportation.controllers;

import com.example.transportation.transportation.dto.DeliveryDTO;
import com.example.transportation.transportation.models.Delivery;
import com.example.transportation.transportation.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
@CrossOrigin(origins = "http://localhost:5173")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getDeliveries() {
        List<DeliveryDTO> deliveryDTOS = deliveryService.getDeliveries();
        return new ResponseEntity<>(deliveryDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DeliveryDTO> createDelivery(@RequestBody Delivery delivery) {
        DeliveryDTO deliveryDTO = deliveryService.createDelivery(delivery);
        return new ResponseEntity<>(deliveryDTO, HttpStatus.OK);
    }
}
