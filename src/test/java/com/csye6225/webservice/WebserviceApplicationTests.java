package com.csye6225.webservice;

import com.csye6225.webservice.controller.HealthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WebserviceApplication.class)
class WebserviceApplicationTests {
    @Autowired
    private HealthController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
        String response = this.restTemplate.getForObject(
                "http://localhost:8080/api/v1/healthz", String.class);
        assertThat(response.contains(
                "{\"healthCheckDetails\":\"Service is Healthy\",\"healthStatus\":\"HEALTHY\"}"));
    }
}
