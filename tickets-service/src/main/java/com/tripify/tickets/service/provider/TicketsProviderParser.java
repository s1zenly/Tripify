package com.tripify.tickets.service.provider;

import com.tripify.tickets.service.model.search.TicketSearchRequest;

import java.util.List;

public interface TicketsProviderParser<T> {

    List<T> parse(TicketSearchRequest request);
}
