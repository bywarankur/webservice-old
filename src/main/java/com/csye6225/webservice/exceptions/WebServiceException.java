package com.csye6225.webservice.exceptions;

public class WebServiceException extends RuntimeException {
    private String message;

    public WebServiceException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public WebServiceException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
