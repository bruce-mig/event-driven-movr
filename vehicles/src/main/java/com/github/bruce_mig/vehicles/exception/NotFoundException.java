package com.github.bruce_mig.vehicles.exception;

/**
 *  Thrown when we can't find the database entity being requested
 */
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }
}
