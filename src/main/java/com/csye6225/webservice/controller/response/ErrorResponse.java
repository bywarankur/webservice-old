package com.csye6225.webservice.controller.response;

public class ErrorResponse {
    private String error_message;

    public ErrorResponse(String message) {
        this.error_message = message;
    }

    public String getMessage() {
        return error_message;
    }

    public void setMessage(String message) {
        this.error_message = message;
    }
}
