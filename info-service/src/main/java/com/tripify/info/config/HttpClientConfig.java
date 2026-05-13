package com.tripify.info.config;

import java.net.http.HttpClient;
import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient tutuHttpClient(CountryInfoProperties properties) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(properties.scraping().connectTimeoutMs()))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    @Bean
    public HttpClient weatherHttpClient(CountryInfoProperties properties) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(properties.weather().connectTimeoutMs()))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }
}
