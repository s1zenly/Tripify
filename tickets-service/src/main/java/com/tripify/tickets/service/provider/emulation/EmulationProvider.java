package com.tripify.tickets.service.provider.emulation;

import com.tripify.tickets.service.model.search.TicketSearchRequest;
import com.tripify.tickets.service.model.unified.UnifiedOffersResponse;
import com.tripify.tickets.service.provider.TicketProvider;
import com.tripify.tickets.service.provider.TicketsProvider;
import com.tripify.tickets.service.provider.TicketsProviderAdapter;
import com.tripify.tickets.service.provider.TicketsProviderParser;
import com.tripify.tickets.service.provider.emulation.raw.EmulationRawOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmulationProvider implements TicketsProvider {

    private final TicketsProviderParser<EmulationRawOffer> parser;
    private final TicketsProviderAdapter<EmulationRawOffer> adapter;

    @Override
    public TicketProvider getProvider() {
        return TicketProvider.EMULATION;
    }

    @Override
    public UnifiedOffersResponse searchOffers(TicketSearchRequest request) {
        return adapter.adapt(parser.parse(request), request);
    }
}
