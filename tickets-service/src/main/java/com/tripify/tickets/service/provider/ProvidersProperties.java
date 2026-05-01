package com.tripify.tickets.service.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties
public class ProvidersProperties {

    private Map<TicketProvider, ProviderConfig> providers = Map.of();
}
