package com.tripify.tickets.service.kafka.model;

import com.tripify.tickets.generated.model.TicketDetail;

import java.time.Instant;

public record TicketPackViewEvent(
        Instant occurredAt,
        PackHeadersEvent headers,
        TicketSearchContextEvent search,
        TicketDetail ticketDetail
) {
}
