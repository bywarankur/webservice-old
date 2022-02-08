package com.example.csye6225webservice.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController

@RequestMapping(path ="api/v1/healthz")
public class HealthController {

    private final HealthService healthService;

    @Autowired
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public List<Health> getHealth() {
        return healthService.getHealth();

    }

//    public class RestController {
//        @GetMapping("api/v1/healthz")
//        public ResponseEntity getHealth() {
//
//            return new ResponseEntity<>("Health", HttpStatus.OK);
//        }
//
//    }
}
