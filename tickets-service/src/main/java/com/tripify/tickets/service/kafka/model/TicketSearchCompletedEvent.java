package com.tripify.tickets.service.kafka.model;

import java.time.Instant;
import java.util.List;

public record TicketSearchCompletedEvent(
        Instant occurredAt,
        TicketSearchContextEvent search,
        List<TicketOfferSnapshot> offers,
        List<ProviderSearchAnalytics> providers
) {
}
