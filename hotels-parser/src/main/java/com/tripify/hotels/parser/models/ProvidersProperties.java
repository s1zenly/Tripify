package com.tripify.hotels.parser.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties
public class ProvidersProperties {
    private Map<Provider, ProviderConfig> providers;
}
