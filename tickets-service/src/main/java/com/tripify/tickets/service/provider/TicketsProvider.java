package com.tripify.tickets.service.provider;

import com.tripify.tickets.service.model.search.TicketSearchRequest;
import com.tripify.tickets.service.model.unified.UnifiedOffersResponse;

public interface TicketsProvider {

    TicketProvider getProvider();

    UnifiedOffersResponse searchOffers(TicketSearchRequest request);
}
