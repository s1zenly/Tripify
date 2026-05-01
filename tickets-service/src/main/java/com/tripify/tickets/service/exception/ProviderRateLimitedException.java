package com.tripify.tickets.service.exception;

import com.tripify.tickets.service.provider.TicketProvider;

public class ProviderRateLimitedException extends ProviderException {

    public ProviderRateLimitedException(TicketProvider provider) {
        super(provider, "Provider rate limited (HTTP 429): " + provider.getCode());
    }

    public ProviderRateLimitedException(TicketProvider provider, Throwable cause) {
        super(provider, "Provider rate limited (HTTP 429): " + provider.getCode(), cause);
    }
}
