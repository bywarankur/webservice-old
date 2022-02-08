package com.example.csye6225webservice.health;

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
    public List<Health> getHealth() {
        return List.of(new Health(
                1L,
                "Ankur",
                "ankurbywar@gmail.com",
                LocalDate.of(1994, 12, 22),
                21
        ));
    }

}
