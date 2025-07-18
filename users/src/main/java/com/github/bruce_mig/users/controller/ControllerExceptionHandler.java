package com.github.bruce_mig.users.controller;


import com.github.bruce_mig.users.dto.MessagesDTO;
import com.github.bruce_mig.users.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import org.hibernate.exception.DataException;

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

    // handles when trying to add an email that already exists
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserExists(UserAlreadyExistsException e, WebRequest request) {
        return new ResponseEntity<>(createResponseBody(e.getMessage()), HttpStatus.CONFLICT);
    }

    // handles when trying to get an entity that is not there (404)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException e, WebRequest request) {
        return new ResponseEntity<>(createResponseBody(e.getMessage()), HttpStatus.NOT_FOUND);
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
        if (e.getCause() instanceof DataException) {
            DataException dataException = (DataException) e.getCause();
            errorMessage = "SQL Exception: " + dataException.getSQLException().getMessage();
        }
        return new ResponseEntity<>(createResponseBody(errorMessage), HttpStatus.CONFLICT);
    }

    // handles any generic argument exceptions the database might throw
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        String errorMessage = e.getMessage();
        if (e.getCause() instanceof DataException) {
            DataException dataException = (DataException) e.getCause();
            errorMessage = "SQL Exception: " + dataException.getSQLException().getMessage();
        }
        return new ResponseEntity<>(createResponseBody(errorMessage), HttpStatus.CONFLICT);
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

    // create a default error body
    private MessagesDTO createResponseBody(String message) {
        return new MessagesDTO(message);
    }

}
