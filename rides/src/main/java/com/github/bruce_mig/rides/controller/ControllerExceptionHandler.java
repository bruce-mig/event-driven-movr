package com.github.bruce_mig.rides.controller;


import com.github.bruce_mig.rides.dto.MessagesDTO;
import com.github.bruce_mig.rides.exception.InvalidUUIDException;
import com.github.bruce_mig.rides.exception.InvalidValueException;
import com.github.bruce_mig.rides.exception.InvalidVehicleStateException;
import com.github.bruce_mig.rides.exception.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import org.hibernate.exception.DataException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class to override Spring's default error handling and return error details to the caller
 */

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MESSAGE_KEY = "messages";

    // handles any Invalid Values, typically math errors
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<Object> handleInvalidValueException(InvalidValueException e, WebRequest request) {
        return new ResponseEntity<>(createResponseBody(e.getMessage()), HttpStatus.CONFLICT);
    }

    // handles a vehicleId or rideId that is not a valid UUID
    @ExceptionHandler(InvalidUUIDException.class)
    public ResponseEntity<Object> handleInvalidUUID(InvalidUUIDException e, WebRequest request) {
        return new ResponseEntity<>(createResponseBody(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // handles when trying to get an entity that is not there (404)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException e, WebRequest request) {
        return new ResponseEntity<>(createResponseBody(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    // handles trying to access a vehicle that is in the wrong "in_use" states
    @ExceptionHandler(InvalidVehicleStateException.class)
    public ResponseEntity<Object> handleNotFound(InvalidVehicleStateException e, WebRequest request) {
        return new ResponseEntity<>(createResponseBody(e.getMessage()), HttpStatus.CONFLICT);
    }

    // handles parameters that are outside specified values
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        return new ResponseEntity<>(createResponseBody(e.getMessage()), HttpStatus.CONFLICT);
    }

    // handles any generic integrity errors the database might throw
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
        String errorMessage = e.getMessage();
        if (e.getCause() instanceof DataException dataException) {
            errorMessage = "SQL Exception: " + dataException.getSQLException().getMessage();
        }
        return new ResponseEntity<>(createResponseBody(errorMessage), HttpStatus.CONFLICT);
    }

    // handles any generic argument exceptions the database might throw
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        String errorMessage = e.getMessage();
        if (e.getCause() instanceof DataException dataException) {
            errorMessage = "SQL Exception: " + dataException.getSQLException().getMessage();
        }
        return new ResponseEntity<>(createResponseBody(errorMessage), HttpStatus.CONFLICT);
    }

    // create a default error body
    private MessagesDTO createResponseBody(String message) {
        return new MessagesDTO(message);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // Spring already has a method handler for invalid arguments, so we need to override it instead of just handle it
        String errorField = ex.getBindingResult().getFieldError().getField();
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        // clean up the error message as the one in the exception is a bit hard to read
        String errorMessage = String.format("Validation error in field <%s> : %s", errorField, message);
        return new ResponseEntity<>(createResponseBody(errorMessage), HttpStatus.BAD_REQUEST);
    }
}
