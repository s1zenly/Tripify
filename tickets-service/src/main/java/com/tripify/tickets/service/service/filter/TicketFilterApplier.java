package com.tripify.tickets.service.service.filter;

import com.tripify.tickets.service.model.unified.UnifiedOffer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketFilterApplier {

    private final TicketFilterMatcher ticketFilterMatcher;

    public TicketFilterApplier(TicketFilterMatcher ticketFilterMatcher) {
        this.ticketFilterMatcher = ticketFilterMatcher;
    }

    public List<UnifiedOffer> apply(List<UnifiedOffer> offers, List<String> filterIds) {
        if (filterIds == null || filterIds.isEmpty()) {
            return offers;
        }

        return offers.stream()
                .filter(offer -> filterIds.stream().allMatch(filterId -> ticketFilterMatcher.matches(offer, filterId)))
                .toList();
    }
}
