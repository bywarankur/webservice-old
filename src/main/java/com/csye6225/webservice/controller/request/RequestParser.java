package com.csye6225.webservice.controller.request;

import com.csye6225.webservice.exceptions.WebServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestParser {
    private ObjectMapper objectMapper;

    public RequestParser() {
        objectMapper = new ObjectMapper();
    }

    public UpdateUserRequest buildUpdateUserRequest(String requestPayload) throws WebServiceException {
        UpdateUserRequest updateUserRequest = null;
        try {
            updateUserRequest = objectMapper.readValue(requestPayload, UpdateUserRequest.class);
        } catch (Exception e) {
            throw new WebServiceException("Exception while parsing upadate user request payload", e);
        }
        return updateUserRequest;
    }

    public CreateUserRequest buildCreateUserRequest(String createUserRequestPayload) throws WebServiceException {
        CreateUserRequest createUserRequest = null;
        try {
            createUserRequest = objectMapper.readValue(createUserRequestPayload, CreateUserRequest.class);
        } catch (Exception e) {
            throw new WebServiceException("Exception while parsing create user request payload", e);
        }
        return createUserRequest;
    }
}
