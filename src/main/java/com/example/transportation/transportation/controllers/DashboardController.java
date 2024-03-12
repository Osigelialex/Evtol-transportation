package com.example.transportation.transportation.controllers;

import com.example.transportation.transportation.dto.DashboardDTO;
import com.example.transportation.transportation.services.DashboardService;
import com.example.transportation.transportation.services.EvtolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DashboardController {
    DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public DashboardDTO getDashboardInformation() {
        return dashboardService.getDashboardInformation();
    }
}
