package com.tripify.tickets.service.service;

import com.tripify.tickets.service.model.unified.UnifiedOffer;

import java.util.Collection;
import java.util.Optional;

public interface TicketOfferStore {

    void saveAll(Collection<UnifiedOffer> offers);

    Optional<UnifiedOffer> findById(String tid);
}
