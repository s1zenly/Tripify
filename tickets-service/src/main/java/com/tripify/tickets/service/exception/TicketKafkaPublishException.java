package com.tripify.tickets.service.exception;

public class TicketKafkaPublishException extends RuntimeException {

    public TicketKafkaPublishException(String message, Throwable cause) {
        super(message, cause);
    }
}
