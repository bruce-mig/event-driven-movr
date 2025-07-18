package com.github.bruce_mig.ui_gateway.controller;

import com.github.bruce_mig.ui_gateway.clients.AuthClient;
import com.github.bruce_mig.ui_gateway.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller to manage login/logout activities
 * (in an actual application, these functions would provide authentication checks and other security features.)
 */

@RestController
@RequestMapping("/ui")
public class AccessController {

    private final AuthClient authClient;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public AccessController(AuthClient authClient) {
        this.authClient = authClient;
    }

    /**
     * Logs in the user.
     *
     * @param credentialsDTO        a POJO holding the json (email) that was passed in
     * @return                      json indicating if the user was authenticated or not
     * @throws NotFoundException    if the passed email does not exist
     */
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> credentialsDTO) {
        logger.info("[POST] /ui/auth/login");
        return ResponseEntity.ok(authClient.login(credentialsDTO));
    }
}
