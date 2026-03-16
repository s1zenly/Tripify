package com.tripify.hotels.parser.config;

import com.tripify.hotels.parser.models.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorsConfig {

    @Bean
    public Map<Provider, ExecutorService> providersExecutors() {
        Map<Provider, ExecutorService> map = new HashMap<>();

        map.put(Provider.MOCK, Executors.newFixedThreadPool(5));

        return map;
    }
}
