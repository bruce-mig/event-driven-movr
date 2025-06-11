package com.github.bruce_mig.ui_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class UiGatewayApplication {

	private static final Logger logger = LoggerFactory.getLogger(UiGatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UiGatewayApplication.class, args);
		logger.info("*** Movr UI Gateway started ***");

	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder, ObjectMapper mapper) {
		return builder
				.messageConverters(new MappingJackson2HttpMessageConverter(mapper))
				.build();
	}

}
