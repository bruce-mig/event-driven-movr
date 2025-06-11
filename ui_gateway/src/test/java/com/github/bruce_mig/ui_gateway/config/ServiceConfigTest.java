package com.github.bruce_mig.ui_gateway.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceConfigTest {

    @Test
    public void uri_shouldReturnTheFormattedURI() {
        ServiceConfig config = new ServiceConfig("host",1234) {};
        assertEquals("http://host:1234/suffix", config.uri("/suffix"));
    }

}