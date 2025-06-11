package com.github.bruce_mig.users.exception;

/**
 *   thrown when the user we're trying to add already exists (duplicate email address)
 */
public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
