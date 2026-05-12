package com.tripify.notification.service.provider;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class ProviderHealthRegistry {

    private final Map<String, Instant> disabledUntil = new ConcurrentHashMap<>();

    public boolean isAvailable(NotificationProvider<?, ?> provider) {
        Instant until = disabledUntil.get(provider.name());

        return until == null || Instant.now().isAfter(until);
    }

    public void disableTemporarily(NotificationProvider<?, ?> provider) {
        disabledUntil.put(provider.name(), Instant.now().plus(Duration.ofMinutes(5)));
    }
}