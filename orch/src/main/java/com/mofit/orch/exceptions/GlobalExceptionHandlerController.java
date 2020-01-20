package com.mofit.orch.exceptions;

import com.mofit.mainmofitapiservice.models.GenericErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    // Add exception handler for BadCredentialsException
    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleBadCredentialsException(
        BadCredentialsException ex) {
        GenericErrorResponse response = new GenericErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                                                 ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Add exception handler for Custom Client Exception
    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleCustomClientException(CustomClientException ex) {
        GenericErrorResponse response = new GenericErrorResponse(
            ex.getStatus().value(), ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, ex.getStatus());
    }

    // TODO: See if we need ResponseEntity and why to we get the status in the message
    // Add exception handler for HttpClientErrorException
    @ExceptionHandler
    public GenericErrorResponse handleClientErrorException(HttpClientErrorException ex) {
        GenericErrorResponse response = GenericErrorResponse.builder()
            .timeStamp(System.currentTimeMillis())
            .message(ex.getLocalizedMessage())
            .status(ex.getStatusCode().value())
            .build();

        return response;
    }

}