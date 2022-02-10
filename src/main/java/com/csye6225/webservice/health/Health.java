package com.csye6225.webservice.health;

public class Health {
    private String healthCheckDetails;
    private HealthStatus healthStatus;

    public Health(String healthCheckDetails, HealthStatus healthStatus) {
        this.healthCheckDetails = healthCheckDetails;
        this.healthStatus = healthStatus;
    }

    public String getHealthCheckDetails() {
        return healthCheckDetails;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    @Override
    public String toString() {
        return "Health{" +
                "healthCheckDetails='" + healthCheckDetails + '\'' +
                ", healthStatus=" + healthStatus +
                '}';
    }
}
