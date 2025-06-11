package com.github.bruce_mig.users.exception;

/**
 *   Thrown when a passed string is not a valid UUID
 */
public class InvalidUUIDException extends Exception {

    public InvalidUUIDException(String message) {
        super(message);
    }
}
