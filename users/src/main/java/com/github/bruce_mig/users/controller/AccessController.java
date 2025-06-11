package com.github.bruce_mig.users.controller;

import com.github.bruce_mig.users.dto.AuthenticationResponseDTO;
import com.github.bruce_mig.users.dto.CredentialsDTO;
import com.github.bruce_mig.users.exception.*;
import com.github.bruce_mig.users.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST Controller to manage login/logout activities
 * (in an actual application, these functions would provide authentication checks and other security features.)
 */

@RestController
@RequestMapping("/api")
public class AccessController {

    private final UserService userService;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public AccessController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Logs in the user.
     *
     * @param credentialsDTO        a POJO holding the json (email) that was passed in
     * @return                      json indicating if the user was authenticated or not
     * @throws NotFoundException    if the passed email does not exist
     */
    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody CredentialsDTO credentialsDTO)
        throws NotFoundException {
        logger.info("[POST] /api/auth/login");

        userService.getUser(credentialsDTO.getEmail());
        // in a normal app, we would perform a security check before returning a token or some other security method
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
        authenticationResponseDTO.setAuthenticated(true);
        return ResponseEntity.ok(authenticationResponseDTO);
    }
}
