# Ankur Bywar CSYE 6225

---------------------------------------------------------------
Summary
----------------------

- This repository contains source for a webservice built using Java/Spring-boot based
  on REST architecture

Tools and Technologies
----------------------

| Infra    | Tools/Technologies       |
|----------|--------------------------|
| Webapp   | Java, Maven, Spring Boot |
| Github   | Github Actions           |
| Database | MySQL                    |

APIs
----------------------
(1) **Health API**
- Path: ``/api/v1/healthz``
- Parameters: None
- Expected response: HTTP 200 OK indicating the service is healthy
  ```
  {"healthCheckDetails":"Service is Healthy","healthStatus":"HEALTHY"}
    ```
(2) **Create User**
- Path: ``/v1/user``
- HTTP Method POST
- Parameters:
```
 {
     "first_name": "...",
     "last_name": "...",
     "password": "...",
     "username": "..."
  }
```
- Auth: None
- Expected response: 
  - HTTP **201** OK indicating the user was created
    ```
    {
       "id": "...",
       "first_name": "...",
       "last_name": "...",
       "username": "...",
       "account_created": "...",
       "account_updated": "..."
    }
    ```
  - HTTP **400** Bad Request if create user request payload is invalid
  - HTTP **400** Bad Request if user already exists

(3) **Get User**
- Path: ``/v1/user/self``
- HTTP Method GET
- Parameters: None
- Auth: Basic auth (username/password)
- Expected response:
  - HTTP **200** OK indicating the user was created
    ```
    {
       "id": "...",
       "first_name": "...",
       "last_name": "...",
       "username": "...",
       "account_created": "...",
       "account_updated": "..."
    }
    ```
  - HTTP **401** Bad credentials if invalid username/password provided

(4) **Put User**
- Path: ``/v1/user/self``
- HTTP Method PUT
- Parameters:
```
 {
     "first_name": "...",
     "last_name": "...",
     "password": "...",
  }
```
- Auth: Basic auth (username/password)
- Expected response:
  - HTTP **204** indicating user details were updated
  - HTTP **401** Bad credentials if invalid username/password provided
  - HTTP **400** Bad Request if update user request payload is invalid

Database
----------------------
- **DB**: MySQL
- **Schema**:
```
Users Table
+-----------------+-------------+------+-----+---------+-------+
| Field           | Type        | Null | Key | Default | Extra |
+-----------------+-------------+------+-----+---------+-------+
| id              | varchar(60) | NO   |     | NULL    |       |
| user_name       | varchar(60) | NO   | PRI | NULL    |       |
| first_name      | varchar(60) | NO   |     | NULL    |       |
| last_name       | varchar(60) | NO   |     | NULL    |       |
| password        | varchar(60) | NO   |     | NULL    |       |
| account_created | varchar(60) | NO   |     | NULL    |       |
| account_updated | varchar(60) | NO   |     | NULL    |       |
+-----------------+-------------+------+-----+---------+-------+
```
- Password is stored using Bcrypt Hash + Salt

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
