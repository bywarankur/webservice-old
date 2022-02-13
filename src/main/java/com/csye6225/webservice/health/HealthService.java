package com.csye6225.webservice.health;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;
@Service
@ResponseStatus
public class HealthService {
    public Health getHealth() {
        return new Health(
                "Service is Healthy",
                HealthStatus.HEALTHY);
    }

}
