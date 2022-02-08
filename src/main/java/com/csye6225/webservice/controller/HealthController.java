package com.csye6225.webservice.controller;

import com.csye6225.webservice.health.Health;
import com.csye6225.webservice.health.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/api/v1/healthz")
public class HealthController {
    private final HealthService healthService;

    @Autowired
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public Health getHealth() {
        return healthService.getHealth();
    }
}
