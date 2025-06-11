package com.github.bruce_mig.users.service;


import com.github.bruce_mig.users.entity.User;
import com.github.bruce_mig.users.exception.NotFoundException;
import com.github.bruce_mig.users.exception.UserAlreadyExistsException;

/**
 * Service to handle basic CRUD functions for user entity
 */

public interface UserService {

    User getUser(String email) throws NotFoundException;
    User addUser(String email, String firstName, String lastName, String[] phoneNumbers) throws UserAlreadyExistsException;
    void deleteUser(String email) throws NotFoundException;
}
