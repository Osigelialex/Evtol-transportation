package com.example.transportation.transportation.services.impl;

import com.example.transportation.transportation.dto.DeliveryDTO;
import com.example.transportation.transportation.enums.EvtolState;
import com.example.transportation.transportation.exception.EvtolBadRequestException;
import com.example.transportation.transportation.exception.EvtolNotFoundException;
import com.example.transportation.transportation.models.Delivery;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.repositories.DeliveryRepository;
import com.example.transportation.transportation.repositories.EvtolRepository;
import com.example.transportation.transportation.services.DeliveryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    EvtolRepository evtolRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<DeliveryDTO> getDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return deliveries.stream().map(delivery -> modelMapper.map(delivery, DeliveryDTO.class)).toList();
    }

    @Override
    public DeliveryDTO createDelivery(Delivery delivery) {
        if (delivery.getAddress() == null ||
                delivery.getRecipientName() == null ||
                delivery.getEvtolSerialNumber() == null) {
            throw new EvtolBadRequestException("Invalid delivery information");
        }

        Evtol evtol = evtolRepository.findEvtolBySerialNumber(delivery.getEvtolSerialNumber());
        if (evtol == null) {
            throw new EvtolNotFoundException("Selected evtol could not be found");
        }

        evtol.setState(EvtolState.DELIVERED);
        evtol.removeAllMedications();

        evtolRepository.save(evtol);

        Delivery newDelivery = deliveryRepository.save(delivery);
        return modelMapper.map(newDelivery, DeliveryDTO.class);
    }
}
