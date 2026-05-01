package com.tripify.tickets.service.exception;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String tid) {
        super("Ticket not found: " + tid);
    }
}
