package com.csye6225.webservice.controller;

import com.csye6225.webservice.controller.request.CreateUserRequest;
import com.csye6225.webservice.controller.request.RequestParser;
import com.csye6225.webservice.controller.request.UpdateUserRequest;
import com.csye6225.webservice.controller.response.CreateUserResponse;
import com.csye6225.webservice.controller.response.ErrorResponse;
import com.csye6225.webservice.controller.response.GetUserResponse;
import com.csye6225.webservice.model.User;
import com.csye6225.webservice.exceptions.WebServiceException;
import com.csye6225.webservice.service.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path ="/v1/user")
public class UserManagementServiceController {
    private final UserManagementService userService;
    private final RequestParser requestParser;
    Logger logger = LoggerFactory.getLogger(UserManagementServiceController.class);

    @PostConstruct
    private void initialize() {
        try {
            userService.createUsersDataStorage();

        } catch (Exception e) {
            logger.error("Unexpected exception while initializing user controller. ", e);
            throw new RuntimeException(e);
        }

    }

    @Autowired
    public UserManagementServiceController(UserManagementService userService) {
        this.userService = userService;
        requestParser = new RequestParser();
    }

    public boolean checkValidUsername(String userName) {
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(regexPattern)
                .matcher(userName)
                .matches();
    }

    @GetMapping(path= "/self", produces = "application/json")
    public ResponseEntity<Object> getUser(@RequestHeader HttpHeaders headers) {

        String userName = getUserNameFromAuthHeader(headers);
        if (userName == null) {
            return new ResponseEntity<Object>(new ErrorResponse("Cannot extract username"),
                    HttpStatus.BAD_REQUEST);
        }

        logger.info("Called Get User API for username " + userName);
        // validate input user and send bad request
        User user = null;
        try {
            user = userService.getUser(userName);
            if (user == null) {
                return new ResponseEntity<Object>(
                        new ErrorResponse("Invalid username OR password. No such user found"),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (WebServiceException e) {
            logger.error("Some unexpected exception occurred. Exception - " + e.getMessage());
            return new ResponseEntity<Object>(new ErrorResponse("Some internal service error occurred"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Object>(new GetUserResponse(user), HttpStatus.OK);
    }

    @PutMapping(path= "/self", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateUser(@RequestBody String updateUserRequestPayload,
                                             @RequestHeader HttpHeaders headers) {

        String userName = getUserNameFromAuthHeader(headers);
        if (userName == null) {
            return new ResponseEntity<Object>(new ErrorResponse("Cannot extract username"),
                    HttpStatus.BAD_REQUEST);
        }

        if (!checkValidUsername(userName)) {
            return new ResponseEntity<Object>(
                    new ErrorResponse("Invalid username"), HttpStatus.BAD_REQUEST);
        }

        // validate input user and send bad request
        logger.info("Called Update User API");
        UpdateUserRequest updateUserRequest = null;
        try {
            updateUserRequest = requestParser.buildUpdateUserRequest(updateUserRequestPayload);
        } catch (WebServiceException e) {
            logger.error("Exception while parsing update user request.", e);
            return new ResponseEntity<Object>(new ErrorResponse("Invalid update user payload"),
                    HttpStatus.BAD_REQUEST);
        }

        updateUserRequest.setUsername(userName);
        User updatedUser = null;
        try {
            if (!userService.userAlreadyExists(updateUserRequest.getUsername())) {
                return new ResponseEntity<Object>(
                        new ErrorResponse("User " + updateUserRequest.getUsername() + " does not exists"),
                        HttpStatus.BAD_REQUEST);
            }
            updatedUser = userService.updateUser(updateUserRequest);
            logger.debug("User created successfully. User ID = " + updatedUser.getId());
        } catch (WebServiceException e) {
            logger.error("Some unexpected exception occurred.", e);
            return new ResponseEntity<Object>(new ErrorResponse("Some internal service error occurred")
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path= "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody String createUserRequestPayload) {
        logger.info("Called Create User API");

        // validate input user and send bad request
        CreateUserRequest createUserRequest = null;
        try {
            createUserRequest = requestParser.buildCreateUserRequest(createUserRequestPayload);
        } catch (WebServiceException e) {
            logger.error("Exception while parsing create user request.", e);
            return new ResponseEntity<Object>(
                    new ErrorResponse("Invalid create user payload"),
                    HttpStatus.BAD_REQUEST);
        }

        if (!checkValidUsername(createUserRequest.getUsername())) {
            return new ResponseEntity<Object>(
                    new ErrorResponse("Invalid username"), HttpStatus.BAD_REQUEST);
        }

        User createdUser = null;
        CreateUserResponse createUserResponse = null;
        try {
            if (userService.userAlreadyExists(createUserRequest.getUsername())) {
                return new ResponseEntity<Object>(
                        new ErrorResponse("User already exists"), HttpStatus.BAD_REQUEST);
            }
            createdUser = userService.createUser(createUserRequest);
            createUserResponse = new CreateUserResponse(createdUser);
            logger.debug("User created successfully. User ID = " + createdUser.getId());
        } catch (WebServiceException e) {
            logger.error("Some unexpected exception occurred.", e);
            return new ResponseEntity<Object>(new ErrorResponse("Some internal service error occurred"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("** Some unexpected exception occurred.", e);
            return new ResponseEntity<Object>(new ErrorResponse("Some internal service error occurred"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.error("Returning response");
        return new ResponseEntity<Object>(createUserResponse, HttpStatus.CREATED);
    }

    private String getUserNameFromAuthHeader(HttpHeaders headers) {
      List<String> authorizationHeaders = headers.get((Object)"Authorization");
        if (authorizationHeaders.isEmpty()) {
            return null;
        }

        logger.info("authorizationHeaders.get(0) " + authorizationHeaders.get(0));
        // extract token from auth header
        // Basic auth header is : Basic amFuZS5kb2VAZXhhbXBsZS5jb206c2tkamZoc2tkZmpoZw==
        String authToken = authorizationHeaders.get(0).split(" ")[1];
            logger.info("authToken = " + authToken);
        // token is base64 encoded. So decoded it. Decoded token will be of
        // format "username:password"
        byte[] decodedTokenBytes = Base64.getDecoder().decode(authToken);
        String decodedToken = new String(decodedTokenBytes, StandardCharsets.UTF_8);

        String[] splitToken = decodedToken.split(":");

        logger.info("decodedToken " + decodedToken);
        logger.info("splitToken[0] " + splitToken[0]);

        if (splitToken.length == 0) {
            return null;
        }

        return splitToken[0];
    }
}
