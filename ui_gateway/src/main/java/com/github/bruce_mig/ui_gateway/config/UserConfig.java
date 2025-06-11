package com.github.bruce_mig.ui_gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("user-service")
public class UserConfig extends ServiceConfig{
    public UserConfig() {
        super();
    }

    public UserConfig(String host, int port) {
        super(host, port);
    }
}
