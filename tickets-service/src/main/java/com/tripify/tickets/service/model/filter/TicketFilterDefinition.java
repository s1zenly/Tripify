package com.tripify.tickets.service.model.filter;

public record TicketFilterDefinition(
        String id,
        TicketFilterType type,
        String label
) {
}
