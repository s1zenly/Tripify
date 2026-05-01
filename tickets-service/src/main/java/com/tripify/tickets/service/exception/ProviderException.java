package com.tripify.tickets.service.exception;

import com.tripify.tickets.service.provider.TicketProvider;
import lombok.Getter;

@Getter
public class ProviderException extends RuntimeException {

    private final TicketProvider provider;

    public ProviderException(TicketProvider provider, String message) {
        super(message);
        this.provider = provider;
    }

    public ProviderException(TicketProvider provider, String message, Throwable cause) {
        super(message, cause);
        this.provider = provider;
    }
}
