package com.mofit.orch.exceptions;

import com.mofit.mainmofitapiservice.models.CustomClientException;
import com.mofit.mainmofitapiservice.models.GenericErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

//TODO: Write timestamps the same format as the objectMapper is set for consistency
@RestControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {


    // Add exception handler for BadCredentialsException
    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleBadCredentialsException(
        BadCredentialsException ex) {
        GenericErrorResponse response = new GenericErrorResponse(HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(), ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Add exception handler for Custom Client Exception
    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleCustomClientException(CustomClientException ex) {
        GenericErrorResponse response = new GenericErrorResponse(
            ex.getStatus().value(), ex.getMessage(), ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));

        return new ResponseEntity<>(response, ex.getStatus());
    }

    // TODO: See if we need that
    // Add exception handler for HttpClientErrorException
    @ExceptionHandler
    public GenericErrorResponse handleClientErrorException(HttpClientErrorException ex) {
        GenericErrorResponse response = GenericErrorResponse.builder()
            .timeStamp(ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()))
            .message(ex.getLocalizedMessage())
            .status(ex.getStatusCode().value())
            .build();

        return response;
    }

}