package com.example.transportation.transportation.services.impl;

import com.example.transportation.transportation.dto.DashboardDTO;
import com.example.transportation.transportation.enums.EvtolState;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.repositories.DeliveryRepository;
import com.example.transportation.transportation.repositories.EvtolRepository;
import com.example.transportation.transportation.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    EvtolRepository evtolRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Override
    public DashboardDTO getDashboardInformation() {
        Integer availableEvtolsCount = evtolRepository.findAvailableEvtols().size();
        Integer totalEvtols = evtolRepository.findAll().size();
        Integer loadedEvtols = evtolRepository.findLoadedEvtols().size();
        Integer totalDeliveries = deliveryRepository.findAll().size();

        return new DashboardDTO(loadedEvtols, availableEvtolsCount, totalEvtols, totalDeliveries);
    }
}
