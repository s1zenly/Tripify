package com.tripify.tickets.service.exception;

public class TicketNotFoundException extends RuntimeException {

    public static TicketNotFoundException byId(String tid) {
        return new TicketNotFoundException("Ticket not found: " + tid);
    }

    public static TicketNotFoundException forEmptySearch() {
        return new TicketNotFoundException("No tickets found for search criteria");
    }

    private TicketNotFoundException(String message) {
        super(message);
    }
}
