package com.github.bruce_mig.users.service;

import java.util.Optional;

import com.github.bruce_mig.users.dao.UserRepository;
import com.github.bruce_mig.users.entity.User;
import com.github.bruce_mig.users.exception.NotFoundException;
import com.github.bruce_mig.users.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import org.modelmapper.ModelMapper;

import static com.github.bruce_mig.users.util.Constants.ERR_USER_ALREADY_EXISTS;
import static com.github.bruce_mig.users.util.Constants.ERR_USER_EMAIL_NOT_FOUND;

/**
 * Implementation of the User Service Interface
 */

@Service
public class UserServiceImpl implements UserService {

    private final static ModelMapper modelMapper = new ModelMapper();

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gets the specified user.
     *
     * @param email                 the email address identifying the user to retrieve
     * @return                      The database entity object for the user
     * @throws NotFoundException    if the user is not found in the database
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true)
    public User getUser(String email) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(email);
        if (!userOptional.isPresent()) {
            throw new NotFoundException(String.format(ERR_USER_EMAIL_NOT_FOUND, email));
        }
        return userOptional.get();
    }

    /**
     * Adds a new user.
     *
     * @param email                         the email address identifying the user to add
     * @param firstName                     the first name of the user
     * @param lastName                      the last name of the user
     * @param phoneNumbers                  an array of phone numbers for the user
     * @return                              the email of the added user
     * @throws UserAlreadyExistsException   if the user's email already exists in the database
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User addUser(String email, String firstName, String lastName, String[] phoneNumbers)
        throws UserAlreadyExistsException {
        // first check if it already exists
        Optional<User> userOptional = userRepository.findById(email);

        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException(String.format(ERR_USER_ALREADY_EXISTS, email));
        }
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumbers(phoneNumbers);
        userRepository.save(user);
        return user;
    }

    /**
     * Deletes the specified user.
     *
     * @param email                 the email of the user to delete
     * @throws NotFoundException    if the user is not found in the database
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteUser(String email) throws NotFoundException {
        // fetch it to make sure it's there
        User user = getUser(email);
        userRepository.delete(user);
    }

}
