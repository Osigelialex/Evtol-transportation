package com.example.transportation.transportation.services.impl;

import com.example.transportation.transportation.dto.BatteryDTO;
import com.example.transportation.transportation.dto.EvtolDTO;
import com.example.transportation.transportation.dto.EvtolDetailDTO;
import com.example.transportation.transportation.dto.MedicationDTO;
import com.example.transportation.transportation.enums.EvtolState;
import com.example.transportation.transportation.exception.EvtolBadRequestException;
import com.example.transportation.transportation.exception.EvtolDuplicateException;
import com.example.transportation.transportation.exception.EvtolNotFoundException;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.models.Medication;
import com.example.transportation.transportation.repositories.EvtolRepository;
import com.example.transportation.transportation.repositories.MedicationRepository;
import com.example.transportation.transportation.services.EvtolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;


@Service
final class EvtolServiceImpl implements EvtolService {
    @Autowired
    EvtolRepository evtolRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public EvtolDTO registerEvtol(Evtol evtol) {
        if (evtolRepository.findEvtolBySerialNumber(evtol.getSerialNumber()) != null) {
            throw new EvtolDuplicateException("Serial number already exists");
        }

        Evtol evtolResponse = evtolRepository.save(evtol);
        return modelMapper.map(evtolResponse, EvtolDTO.class);
    }

    @Override
    public EvtolDetailDTO getEvtolDetail(String serialNumber) {
        Evtol evtol = evtolRepository.findEvtolBySerialNumber(serialNumber);
        if (evtol == null) {
            throw new EvtolNotFoundException("Requested Evtol could not be found");
        }

        return  modelMapper.map(evtol, EvtolDetailDTO.class);
    }

    @Override
    public List<EvtolDTO> getLoadedEvtols() {
        List<Evtol> evtols = evtolRepository.findLoadedEvtols();
        return evtols.stream().map(evtol -> modelMapper.map(evtol, EvtolDTO.class)).toList();
    }

    @Override
    public List<MedicationDTO> getLoadedMedications(String serialNumber) {
        Evtol evtol = evtolRepository.findEvtolBySerialNumber(serialNumber);
        if (evtol == null) {
            throw new EvtolNotFoundException("Requested Evtol could not be found");
        }

        List<Medication> medications = evtol.getMedications();
        return medications.stream()
                .map(medication -> modelMapper.map(medication, MedicationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EvtolDTO> getAllEvtols() {
        List<Evtol> evtols = evtolRepository.findAllByOrderByRegisteredAtDesc();
        if (evtols.isEmpty()) {
            throw new EvtolNotFoundException("No evtols where found");
        }

        return evtols.stream()
                .map(evtol -> modelMapper.map(evtol, EvtolDTO.class))
                .toList();
    }

    @Override
    public List<EvtolDTO> getAllAvailableVtols() {
        List<Evtol> availableEvtols = evtolRepository.findAvailableEvtols();

        if (availableEvtols.isEmpty()) {
            throw new EvtolNotFoundException("No available evtols where found");
        }

        return availableEvtols.stream()
                .map(evtol -> modelMapper.map(evtol, EvtolDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Scheduled(fixedRate = 100_000L)
    public void updateBatteryPercentage() {
        List<Evtol> evtols = evtolRepository.findAll();
        Random random = new Random();
        int MAX = 5;
        int MIN = 1;

        for (Evtol evtol : evtols) {
            evtol.setPercentage(Math.max(evtol.getPercentage() - random.nextInt(MAX - MIN + 1), 0));

            if (evtol.getPercentage() > 25) {
                evtol.setState(EvtolState.LOADING);
            }
            if (evtol.getPercentage() < 25) {
                evtol.setState(EvtolState.IDLE);
            }

            if (evtol.getPercentage() == 0) {
                evtol.setPercentage(100);
                evtol.setState(EvtolState.LOADING);
            }

            evtolRepository.save(evtol);
        }
    }
}