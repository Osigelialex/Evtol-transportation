package com.example.transportation.transportation.services.impl;

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

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Service
public class EvtolServiceImpl implements EvtolService {

    EvtolRepository evtolRepository;
    MedicationRepository medicationRepository;

    public EvtolServiceImpl(EvtolRepository evtolrepository,
                            MedicationRepository medicationRepository) {
        this.evtolRepository = evtolrepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public ResponseEntity<Object> registerEvtol(Evtol evtol) {
        if (evtolRepository.findById(evtol.getSerialNumber()).isPresent()) {
            throw new EvtolDuplicateException("Serial number already exists");
        }

        evtolRepository.save(evtol);

        return ResponseHandler.responseBuilder("evtol registered successfully", HttpStatus.OK, evtol);
    }

    @Override
    public ResponseEntity<Object> getEvtolBatteryInformation(String serialNumber) {
        Evtol evtol = evtolRepository.findById(serialNumber)
                .orElseThrow(() -> new EvtolNotFoundException("The specified evtol could not be found"));

        return ResponseHandler.responseBuilder("success", HttpStatus.OK, evtol.getPercentage());
    }

    @Override
    public ResponseEntity<Object> getEvtolLoadedMedications(String serialNumber) {
        Evtol evtol = evtolRepository.findById(serialNumber)
                .orElseThrow(() -> new EvtolNotFoundException("The specified evtol could not be found"));

        return ResponseHandler.responseBuilder("success", HttpStatus.OK, evtol.getMedications());
    }

    @Override
    public ResponseEntity<Object> loadEvtolMedications(String serialNumber,
                                                       Medication medication,
                                                       MultipartFile multipartFile
    ) throws IOException {
        Evtol evtol = evtolRepository.findById(serialNumber)
                .orElseThrow(() -> new EvtolNotFoundException("The specified evtol could not be found"));

        if (medication.getWeight() > evtol.getWeightLimit()) {
            throw new EvtolBadRequestException("The medication weight surpasses evtol weight limit");
        }

        String medicationImageName = multipartFile.getOriginalFilename();
        int medicationImageBytesLength = multipartFile.getBytes().length;

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(medicationImageName));
        if (fileName.contains("..")) {
            throw new EvtolBadRequestException("Filename contains invalid path sequence");
        }

        if (medicationImageBytesLength > (1024 * 1024)) {
            throw new EvtolBadRequestException("File size exceeds maximum limit");
        }

        // upload file to local file system
        String imagePath = "src/main/resources/images/" + medicationImageName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, Paths.get(imagePath));
        } catch (IOException e) {
            throw e;
        }

        String imageURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/v1/evtol/images/")
                .path(medicationImageName)
                .toUriString();

        medication.setEvtol(evtol);
        medication.setMedicalImageUrl(imageURl);
        medicationRepository.save(medication);

        evtol.addMedication(medication);
        evtolRepository.save(evtol);

        return ResponseHandler.responseBuilder("medications loaded successfully", HttpStatus.OK, medication);
    }

    @Override
    public ResponseEntity<Object> getAllAvailableVtols() {
        List<Evtol> availableEvtols = evtolRepository.findAvailableEvtols();

        if (availableEvtols.isEmpty()) {
            throw new EvtolNotFoundException("No available evtols where found");
        }

        return ResponseHandler.responseBuilder("success", HttpStatus.OK, availableEvtols);
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
}
