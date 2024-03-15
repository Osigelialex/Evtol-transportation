package com.example.transportation.transportation.services.impl;

import com.example.transportation.transportation.dto.MedicationDTO;
import com.example.transportation.transportation.enums.EvtolState;
import com.example.transportation.transportation.exception.EvtolBadRequestException;
import com.example.transportation.transportation.exception.EvtolNotFoundException;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.models.Medication;
import com.example.transportation.transportation.repositories.EvtolRepository;
import com.example.transportation.transportation.repositories.MedicationRepository;
import com.example.transportation.transportation.services.MedicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    EvtolRepository evtolRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<MedicationDTO> getAllMedications() {
        List<Medication> medications = medicationRepository.findAll();
        return medications.stream().map(medication -> modelMapper.map(medication, MedicationDTO.class)).toList();
    }

    @Override
    public List<MedicationDTO> getMedicationForEvtol(String serialNumber) {
        List<Medication> medications = medicationRepository.findAllByEvtolSerialNumber(serialNumber);
        return medications.stream().map(medication -> modelMapper.map(medication, MedicationDTO.class)).toList();
    }

    @Override
    public MedicationDTO addNewMedication(Medication medication) {
        Medication newMedication = medicationRepository.save(medication);
        return modelMapper.map(newMedication, MedicationDTO.class);
    }

    @Override
    public MedicationDTO loadMedicationToEvtol(String serialNumber, Medication medication) throws IOException {
        Evtol evtol = evtolRepository.findEvtolBySerialNumber(serialNumber);
        if (evtol == null) {
            throw new EvtolNotFoundException("Requested Evtol could not be found");
        }

        if (evtol.getPercentage() < 25) {
            throw new EvtolBadRequestException("Battery too low for loading");
        }

        medication.setEvtol(evtol);
        Medication medicationResponse = medicationRepository.save(medication);

        evtol.addMedication(medication);
        evtol.setState(EvtolState.LOADED);
        evtolRepository.save(evtol);

        return modelMapper.map(medicationResponse, MedicationDTO.class);
    }
}
