package com.tripify.tickets.service.service;

import com.tripify.tickets.service.provider.ProviderConfig;
import com.tripify.tickets.service.provider.ProvidersProperties;
import com.tripify.tickets.service.provider.TicketProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProviderAvailability {

    private static final Logger logger = LoggerFactory.getLogger(ProviderAvailability.class);

    private final ProvidersProperties providersProperties;
    private final Map<TicketProvider, Instant> disabledUntil = new ConcurrentHashMap<>();

    public ProviderAvailability(ProvidersProperties providersProperties) {
        this.providersProperties = providersProperties;
    }

    public boolean isEnabled(TicketProvider provider) {
        ProviderConfig config = providersProperties.getProviders().get(provider);
        return config != null && config.enabled();
    }

    public boolean isAvailable(TicketProvider provider) {
        if (!isEnabled(provider)) {
            return false;
        }

        Instant until = disabledUntil.get(provider);
        if (until == null) {
            return true;
        }

        if (Instant.now().isAfter(until)) {
            disabledUntil.remove(provider);
            logger.info("Provider re-enabled after rate limit cooldown: provider={}", provider);
            return true;
        }

        return false;
    }

    public void disableAfterRateLimit(TicketProvider provider) {
        ProviderConfig config = providersProperties.getProviders().get(provider);
        if (config == null) {
            return;
        }

        Instant until = Instant.now().plus(config.disableDurationOn429());
        disabledUntil.put(provider, until);
        logger.warn("Provider disabled after 429: provider={}, until={}", provider, until);
    }
}
