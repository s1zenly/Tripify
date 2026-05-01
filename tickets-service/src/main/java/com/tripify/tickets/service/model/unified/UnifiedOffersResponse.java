package com.tripify.tickets.service.model.unified;

import lombok.Builder;

import java.util.List;

@Builder
public record UnifiedOffersResponse(
        List<UnifiedOffer> offers
) {
}
