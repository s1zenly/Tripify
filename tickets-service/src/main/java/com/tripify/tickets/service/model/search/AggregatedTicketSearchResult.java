package com.tripify.tickets.service.model.search;

import com.tripify.tickets.service.model.unified.UnifiedOffer;
import com.tripify.tickets.service.provider.TicketProvider;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Builder
public record AggregatedTicketSearchResult(
        List<UnifiedOffer> offers,
        Map<TicketProvider, ProviderSearchResult> byProvider,
        Instant completedAt
) {
}
