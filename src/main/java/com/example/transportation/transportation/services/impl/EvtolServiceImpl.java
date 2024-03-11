package com.example.transportation.transportation.services.impl;

import com.example.transportation.transportation.dto.BatteryDTO;
import com.example.transportation.transportation.dto.EvtolDTO;
import com.example.transportation.transportation.dto.MedicationDTO;
import com.example.transportation.transportation.enums.EvtolState;
import com.example.transportation.transportation.exception.EvtolBadRequestException;
import com.example.transportation.transportation.exception.EvtolDuplicateException;
import com.example.transportation.transportation.exception.EvtolNotFoundException;
import com.example.transportation.transportation.models.Evtol;
import com.example.transportation.transportation.models.Medication;
import com.example.transportation.transportation.repositories.EvtolRepository;
import com.example.transportation.transportation.repositories.MedicationRepository;
import com.example.transportation.transportation.response.ResponseHandler;
import com.example.transportation.transportation.services.EvtolService;
import jakarta.validation.constraints.Null;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Service
public class EvtolServiceImpl implements EvtolService {

    private final EvtolRepository evtolRepository;
    private final MedicationRepository medicationRepository;
    private final ModelMapper modelMapper;

    public EvtolServiceImpl(EvtolRepository evtolrepository,
                            MedicationRepository medicationRepository,
                            ModelMapper modelMapper) {
        this.evtolRepository = evtolrepository;
        this.medicationRepository = medicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EvtolDTO registerEvtol(Evtol evtol) {
        if (evtolRepository.findEvtolBySerialNumber(evtol.getSerialNumber()) != null) {
            throw new EvtolDuplicateException("Serial number already exists");
        }

        Evtol evtolResponse = evtolRepository.save(evtol);
        return modelMapper.map(evtolResponse, EvtolDTO.class);
    }

    @Override
    public BatteryDTO getBatteryInformation(String serialNumber) {
        Evtol evtol = evtolRepository.findEvtolBySerialNumber(serialNumber);
        if (evtol == null) {
            throw new EvtolNotFoundException("Requested Evtol could not be found");
        }

        return modelMapper.map(evtol, BatteryDTO.class);
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
    public MedicationDTO loadEvtolMedications(String serialNumber,
                                                       Medication medication,
                                                       MultipartFile multipartFile
    ) throws IOException {
        Evtol evtol = evtolRepository.findEvtolBySerialNumber(serialNumber);
        if (evtol == null) {
            throw new EvtolNotFoundException("Requested Evtol could not be found");
        }

        String medicationImageName = multipartFile.getOriginalFilename();

        // validations on multipart file
        validatePathSequence(medicationImageName);
        validateImageWeight(evtol, medication);
        validateMedicalImageSize(multipartFile);

        // copy file to image path
        String uniquePath = medication.getCode() + "-" + medicationImageName;
        String imagePath = "src/main/resources/images/" + uniquePath;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, Paths.get(imagePath));
        } catch (IOException e) {
            throw e;
        }

        String imageURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/v1/evtol/images/")
                .path(uniquePath)
                .toUriString();

        medication.setEvtol(evtol);
        medication.setMedicalImageUrl(imageURl);
        Medication medicationResponse = medicationRepository.save(medication);

        evtol.addMedication(medication);
        evtolRepository.save(evtol);

        return modelMapper.map(medicationResponse, MedicationDTO.class);
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
    public ResponseEntity<byte[]> getMedicationImage(String imageName) throws IOException {
        Path imagePath = Paths.get("src/main/resources/images", imageName);
        byte[] imageContent = Files.readAllBytes(imagePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    @Override
    @Scheduled(fixedRate = 100_000L)
    public void updateBatteryPercentage() {
        List<Evtol> evtols = evtolRepository.findAll();

        for (Evtol evtol : evtols) {
            evtol.setPercentage(Math.max(evtol.getPercentage() - 1, 0));

            if (evtol.getPercentage() < 25 && evtol.getState().equals(EvtolState.LOADING)) {
                evtol.setState(EvtolState.IDLE);
            }

            if (evtol.getPercentage() == 0) {
                evtol.setPercentage(100);
            }

            evtolRepository.save(evtol);
        }
    }

    public void validateMedicalImageSize(MultipartFile multipartFile) throws IOException {
        int medicationImageBytesLength = multipartFile.getBytes().length;
        if (medicationImageBytesLength > (1024 * 1024)) {
            throw new EvtolBadRequestException("File size exceeds maximum limit");
        }
    }

    public void validatePathSequence(String medicationImageName) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(medicationImageName));
        if (fileName.contains("..")) {
            throw new EvtolBadRequestException("Filename contains invalid path sequence");
        }
    }

    public void validateImageWeight(Evtol evtol, Medication medication) {
        if (medication.getWeight() > evtol.getWeightLimit()) {
            throw new EvtolBadRequestException("The medication weight surpasses evtol weight limit");
        }
    }
}