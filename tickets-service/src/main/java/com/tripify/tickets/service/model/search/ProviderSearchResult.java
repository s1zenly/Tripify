package com.tripify.tickets.service.model.search;

import com.tripify.tickets.service.model.unified.UnifiedOffersResponse;
import com.tripify.tickets.service.provider.TicketProvider;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ProviderSearchResult(
        TicketProvider provider,
        UnifiedOffersResponse response,
        ProviderSearchStatus status,
        long durationMs,
        String error,
        Instant completedAt
) {
    public int offerCount() {
        if (response == null || response.offers() == null) {
            return 0;
        }
        return response.offers().size();
    }
}
