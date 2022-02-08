package com.example.csye6225webservice;


import com.example.csye6225webservice.health.Health;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication

public class Csye6225WebserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(Csye6225WebserviceApplication.class, args);
	}



}
