package com.tripify.tickets.service.provider;

import com.tripify.tickets.service.model.search.TicketSearchRequest;

import java.util.List;

public interface TicketsProviderClient<T> {

    List<T> fetch(TicketSearchRequest request);
}
