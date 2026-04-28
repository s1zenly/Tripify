package com.tripify.hotels.parser.config;

import com.tripify.hotels.parser.models.Provider;
import com.tripify.hotels.parser.models.ProvidersProperties;
import com.tripify.hotels.parser.service.ProviderExecutionContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class ExecutorsConfig {

    @Bean
    public Map<Provider, ProviderExecutionContext> providersExecutors(ProvidersProperties properties) {
        Map<Provider, ProviderExecutionContext> map = new EnumMap<>(Provider.class);

        properties.getProviders().forEach((provider, config) ->
                map.put(provider, new ProviderExecutionContext(config)));

        return map;
    }
}
