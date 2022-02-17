package com.csye6225.webservice.controller;

import com.csye6225.webservice.health.Health;
import com.csye6225.webservice.health.HealthService;
import com.csye6225.webservice.health.HealthStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(HealthService.class)
@Import(HealthController.class)
public class HealthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    HealthService healthService;

    Health DUMMY_HEALTH_CHECK_RESPONSE =
            new Health("DUMMY_HEALTH_CHECK_RESPONSE",
                    HealthStatus.HEALTHY);

    Health DUMMY_UNHEALTH_CHECK_RESPONSE =
            new Health("DUMMY_HEALTH_CHECK_RESPONSE",
                    HealthStatus.UNHEALTHY);

    //@Test
    public void getHealth_healthy_status() throws Exception {
        Mockito.when(healthService.getHealth()).
                thenReturn(DUMMY_HEALTH_CHECK_RESPONSE);

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/healthz"));

        String expectedHealthCheckResponse =
                "{\"healthCheckDetails\":\"DUMMY_HEALTH_CHECK_RESPONSE\"," +
                        "\"healthStatus\":\"HEALTHY\"}";

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().string(
                containsString(expectedHealthCheckResponse)));
    }

    //@Test
    public void getHealth_unhealthy_status() throws Exception {
        Mockito.when(healthService.getHealth()).
                thenReturn(DUMMY_UNHEALTH_CHECK_RESPONSE);

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/healthz"));

        String expectedHealthCheckResponse =
                "{\"healthCheckDetails\":\"DUMMY_HEALTH_CHECK_RESPONSE\"," +
                        "\"healthStatus\":\"UNHEALTHY\"}";

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().string(
                containsString(expectedHealthCheckResponse)));
    }

    //@Test
    public void getHealth_invalid_path() throws Exception {
        Mockito.when(healthService.getHealth()).
                thenReturn(DUMMY_UNHEALTH_CHECK_RESPONSE);

        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/blah_blah_blah"));

        resultActions.andExpect(status().isNotFound());
    }
}
