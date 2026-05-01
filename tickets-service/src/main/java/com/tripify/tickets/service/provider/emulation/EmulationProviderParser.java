package com.tripify.tickets.service.provider.emulation;

import com.tripify.tickets.service.model.search.TicketSearchRequest;
import com.tripify.tickets.service.provider.TicketsProviderClient;
import com.tripify.tickets.service.provider.TicketsProviderParser;
import com.tripify.tickets.service.provider.emulation.raw.EmulationRawOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmulationProviderParser implements TicketsProviderParser<EmulationRawOffer> {

    private final TicketsProviderClient<EmulationRawOffer> client;

    @Override
    public List<EmulationRawOffer> parse(TicketSearchRequest request) {
        return client.fetch(request);
    }
}
