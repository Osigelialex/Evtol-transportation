package com.example.transportation.transportation.services;

import com.example.transportation.transportation.dto.DeliveryDTO;
import com.example.transportation.transportation.models.Delivery;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DeliveryService {
    List<DeliveryDTO> getDeliveries();
    DeliveryDTO createDelivery(Delivery delivery);
}
