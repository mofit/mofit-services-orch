package com.mofit.orch.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CustomClientException extends RuntimeException {

    private static final long serialVersionUID = 121L;

    @Getter
    private HttpStatus status;

    public CustomClientException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}