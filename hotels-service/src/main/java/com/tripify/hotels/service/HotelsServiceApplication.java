package com.tripify.hotels.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HotelsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelsServiceApplication.class, args);
    }
}
