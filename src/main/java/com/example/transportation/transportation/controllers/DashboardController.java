package com.example.transportation.transportation.controllers;

import com.example.transportation.transportation.dto.DashboardDTO;
import com.example.transportation.transportation.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
final class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @GetMapping("/dashboard")
    public DashboardDTO getDashboardInformation() {
        return dashboardService.getDashboardInformation();
    }
}
