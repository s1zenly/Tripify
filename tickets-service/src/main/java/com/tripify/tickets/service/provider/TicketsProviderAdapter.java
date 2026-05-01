package com.tripify.tickets.service.provider;

import com.tripify.tickets.service.model.search.TicketSearchRequest;
import com.tripify.tickets.service.model.unified.UnifiedOffersResponse;

import java.util.List;

public interface TicketsProviderAdapter<T> {

    UnifiedOffersResponse adapt(List<T> providerOffers, TicketSearchRequest request);
}
