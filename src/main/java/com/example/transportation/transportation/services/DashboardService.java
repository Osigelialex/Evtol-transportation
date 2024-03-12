package com.example.transportation.transportation.services;

import com.example.transportation.transportation.dto.DashboardDTO;
import com.example.transportation.transportation.repositories.EvtolRepository;
import org.springframework.stereotype.Service;

public interface DashboardService {
    DashboardDTO getDashboardInformation();
}
