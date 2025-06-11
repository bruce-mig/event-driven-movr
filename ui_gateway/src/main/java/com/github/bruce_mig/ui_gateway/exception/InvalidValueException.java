package com.github.bruce_mig.ui_gateway.exception;

/**
 *     Thrown when something goes wrong in calculations (e.g., divide by zero) or some other missing data
 */
public class InvalidValueException extends Exception {

    public InvalidValueException(String message) {
        super(message);
    }
}
