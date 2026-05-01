package com.tripify.tickets.service.provider;

import lombok.Getter;

@Getter
public enum TicketProvider {

    EMULATION("EMULATION", "Emulation"),
    AVIASALES("AVIASALES", "Aviasales"),
    TRIP("TRIP", "Trip.com");

    private final String code;
    private final String displayName;

    TicketProvider(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
