package com.github.bruce_mig.ui_gateway.clients;


import com.github.bruce_mig.ui_gateway.config.RidesConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class RidesClient {
    private final RestTemplate restTemplate;
    private final RidesConfig config;

    public RidesClient(RestTemplate restTemplate, RidesConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public Map<String, Object> startRide(Map<String, Object> request) {
        String uri = config.uri("/api/rides/start");
        Map<String, Object> response = restTemplate.postForObject(uri, request, Map.class);
        return response;
    }

    public Map<String, Object> endRide(Map<String, Object> request) {
        String uri = config.uri("/api/rides/end");
        Map<String, Object> response = restTemplate.postForObject(uri, request, Map.class);
        return response;
    }

    public List<Map<String, Object>> getRides(String email) {
        String uri = config.uri(String.format("/api/rides?email=%s", email));
        Map<String, Object>[] rides = restTemplate.getForObject(uri, Map[].class);
        return Arrays.asList(rides);
    }
}
