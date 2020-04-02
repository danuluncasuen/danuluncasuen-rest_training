package com.internship.rest.exceptionshandler;

import org.springframework.http.HttpStatus;

public class CustomUserServiceException extends Exception {
    private final HttpStatus responseCode;

    public CustomUserServiceException(String message, HttpStatus code) {
        super(message);
        this.responseCode = code;
    }

    public HttpStatus getResponseCode() {
        return responseCode;
    }
}
