package com.tripify.info.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tripify.info")
public record CountryInfoProperties(
        Scraping scraping,
        Weather weather
) {

    public record Scraping(
            boolean enabled,
            String cron,
            int connectTimeoutMs,
            int readTimeoutMs,
            String userAgent,
            List<CountryConfig> countries
    ) {
        public Scraping {
            if (countries == null) {
                countries = List.of();
            }
        }
    }

    public record CountryConfig(
            String alpha2,
            String name,
            String sourceUrl,
            String localCurrency
    ) {
    }

    public record Weather(
            boolean enabled,
            String provider,
            String apiUrl,
            int connectTimeoutMs,
            int readTimeoutMs,
            int maxDays
    ) {
    }
}
