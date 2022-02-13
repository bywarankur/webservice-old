# Ankur Bywar CSYE 6225

---------------------------------------------------------------
Summary
----------------------

- This repository contains source for a webservice built using Java/Spring-boot based
  on REST architecture

Tools and Technologies
----------------------

| Infra                | Tools/Technologies                   |
|----------------------|--------------------------------------|
| Webapp               | Java, Maven, Spring Boot             |
| Github               | Github Actions                       |


APIs
----------------------
(1) **Health API**
- Path: ``/api/v1/healthz``
- Parameters: None
- Expected response: HTTP 200 OK indicating the service is healthy
  ```
  {"healthCheckDetails":"Service is Healthy","healthStatus":"HEALTHY"}
    ```

Web service configuration
----------------------
- Port Number: 8080
- HTTP protocol

Build and running tests
----------------------
(1) Clone the repository using following command

```
mkdir ~/ankur-csye-6225
cd ~/ankur-csye-6225
git clone git@github.com:bywarankur/webservice.git
cd webservice
```

(2) Build the maven application and run all tests.
This will run all unit and end to end tests.
```
mvn clean verify -Dspring.profiles.active=integration-test
```

(3) Start the web service on local host. The port number on which
the service will run is define in ``src/main/resources/application.properties``.
The port number is 8080.
```
mvn spring-boot:run
```

(4) Testing the service APIs manually (using Postman): In postman, send a ``GET`` request to ``http://localhost:8080/api/v1/healthz`` and verify the response


Unit Tests/Integration Tests
----------------------
The package as both unit tests and end to end test.
- Unit tests are under ``test/java/..`` directory
- End to End test is written in ``WebserviceApplicationTests.java`` file