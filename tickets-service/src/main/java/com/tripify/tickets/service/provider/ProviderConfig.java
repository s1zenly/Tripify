package com.tripify.tickets.service.provider;

import java.time.Duration;

public record ProviderConfig(
        boolean enabled,
        Duration disableDurationOn429
) {
    public ProviderConfig {
        if (disableDurationOn429 == null || disableDurationOn429.isZero() || disableDurationOn429.isNegative()) {
            disableDurationOn429 = Duration.ofMinutes(1);
        }
    }
}
