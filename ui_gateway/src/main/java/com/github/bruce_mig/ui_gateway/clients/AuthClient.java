package com.github.bruce_mig.ui_gateway.clients;

import com.github.bruce_mig.ui_gateway.config.AuthConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AuthClient {

    private final RestTemplate restTemplate;
    private final AuthConfig config;

    public AuthClient(RestTemplate restTemplate, AuthConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public Map<String, Object> login(Map<String, Object> credentialsDTO) {
        String uri = config.uri("/api/auth/login");
        Map<String, Object> response = restTemplate.postForObject(uri, credentialsDTO, Map.class);
        return response;
    }
}
