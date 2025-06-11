package com.github.bruce_mig.ui_gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("vehicle-service")
public class VehicleConfig extends ServiceConfig{
    public VehicleConfig() {
        super();
    }

    public VehicleConfig(String host, int port) {
        super(host, port);
    }
}
