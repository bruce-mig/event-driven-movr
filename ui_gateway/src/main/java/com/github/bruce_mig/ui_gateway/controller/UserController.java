package com.github.bruce_mig.ui_gateway.controller;


import com.github.bruce_mig.ui_gateway.clients.UserClient;
import com.github.bruce_mig.ui_gateway.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller to manage user activities
 */

@RestController
@RequestMapping("/ui")
public class UserController {

    private final UserClient userClient;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    /**
     * Registers a new user (add a User entity).
     *
     * @param userDTO                      a POJO holding the json that was passed in containing the user information
     * @return                             the email of the added user
     */
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody @Validated Map<String, Object> userDTO) {
        logger.info("[POST] /ui/users");
        return ResponseEntity.ok(userClient.addUser(userDTO));
    }

    /**
     * Gets a user.
     *
     * @param email                the email of the user to retrieve
     * @return                     Json with the details about the user
     * @throws NotFoundException   if the email does not exist
     */
    @GetMapping("/users/{email}")
    public ResponseEntity<Map<String, Object>> getProfile(@PathVariable String email) {
        logger.info("[GET] /ui/users/{email}");
        return ResponseEntity.ok(userClient.getUser(email));
    }

    /**
     * Deletes a user.
     *
     * @param email                the email of the user to delete
     * @return                     a message indicated the user was deleted
     * @throws NotFoundException   if the email does not exist
     */
    @DeleteMapping("/users/{email}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String email) {
        logger.info("[DELETE] /ui/users/{email}");
        return ResponseEntity.ok(userClient.deleteUser(email));
    }
}

