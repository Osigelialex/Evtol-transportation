package com.example.transportation.transportation.services.impl;

import com.example.transportation.transportation.dto.DashboardDTO;
import com.example.transportation.transportation.dto.EvtolDTO;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.repositories.EvtolRepository;
import com.example.transportation.transportation.services.DashboardService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    EvtolRepository evtolRepository;
    ModelMapper modelMapper;

    public DashboardServiceImpl(EvtolRepository evtolRepository, ModelMapper modelMapper) {
        this.evtolRepository = evtolRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DashboardDTO getDashboardInformation() {
        Integer availableEvtolCount = evtolRepository.findAvailableEvtols().size();
        Integer totalEvtols = evtolRepository.findAll().size();
        Integer loadedEvtolCount = evtolRepository.findLoadedEvtols().size();

        List<Evtol> availableEvtols = evtolRepository.findAvailableEvtols();
        List<EvtolDTO> availableEvtolDTOS = availableEvtols.stream()
                .map(evtol -> modelMapper.map(evtol, EvtolDTO.class))
                .toList();

        return new DashboardDTO(availableEvtolDTOS, loadedEvtolCount, availableEvtolCount, totalEvtols);
    }
}
