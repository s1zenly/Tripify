package com.tripify.tickets.service.service;

import com.tripify.tickets.service.kafka.model.PackHeadersEvent;
import com.tripify.tickets.service.kafka.model.ProviderSearchAnalytics;
import com.tripify.tickets.service.kafka.model.TicketOfferSnapshot;
import com.tripify.tickets.service.kafka.model.TicketSearchContextEvent;
import com.tripify.tickets.service.kafka.model.UserType;
import com.tripify.tickets.service.model.search.ProviderSearchResult;
import com.tripify.tickets.service.model.search.TicketSearchRequest;
import com.tripify.tickets.service.model.unified.UnifiedOffer;
import java.time.LocalDate;
import java.util.List;

final class TicketKafkaEventFactory {

    private TicketKafkaEventFactory() {
    }

    static PackHeadersEvent toHeaders(
            UserType userType,
            String userId,
            String anonymousId,
            String generationId,
            int packRevision,
            String generationMode,
            String requestId,
            Integer ticketsRevision
    ) {
        return new PackHeadersEvent(
                userType,
                userId,
                anonymousId,
                generationId,
                packRevision,
                generationMode,
                requestId,
                ticketsRevision
        );
    }

    static TicketSearchContextEvent toSearch(TicketSearchRequest request) {
        return new TicketSearchContextEvent(
                request.originCityCode(),
                request.destinationCityCode(),
                request.departureDate(),
                request.returnDate(),
                request.currency().getCode(),
                request.passengers().adults(),
                request.passengers().children(),
                request.passengers().infants(),
                request.budgetMaxAmount()
        );
    }

    static TicketSearchContextEvent toSearch(
            String originCityCode,
            String destinationCityCode,
            LocalDate departureDate,
            LocalDate returnDate,
            String currency,
            int adults,
            int children,
            int infants,
            Long budgetMaxAmount
    ) {
        return new TicketSearchContextEvent(
                originCityCode,
                destinationCityCode,
                departureDate,
                returnDate,
                currency,
                adults,
                children,
                infants,
                budgetMaxAmount
        );
    }

    static List<ProviderSearchAnalytics> toProviderAnalytics(List<ProviderSearchResult> results) {
        return results.stream()
                .map(TicketKafkaEventFactory::toProviderAnalytics)
                .toList();
    }

    private static ProviderSearchAnalytics toProviderAnalytics(ProviderSearchResult result) {
        return new ProviderSearchAnalytics(
                result.provider().getCode(),
                result.status().name(),
                result.offerCount(),
                result.durationMs(),
                result.error()
        );
    }

    static List<TicketOfferSnapshot> toOfferSnapshots(List<UnifiedOffer> offers) {
        return offers.stream()
                .map(TicketKafkaEventFactory::toOfferSnapshot)
                .toList();
    }

    private static TicketOfferSnapshot toOfferSnapshot(UnifiedOffer offer) {
        return new TicketOfferSnapshot(
                offer.unifiedOfferId(),
                offer.provider() != null && offer.provider().code() != null
                        ? offer.provider().code().getCode()
                        : null,
                offer.provider() != null ? offer.provider().offerId() : null,
                offer.price() != null ? offer.price().amount() : null,
                offer.price() != null && offer.price().currency() != null
                        ? offer.price().currency().name()
                        : null,
                offer.validatingAirline() != null ? offer.validatingAirline().code() : null,
                offer.availability() != null ? offer.availability().seatsLeft() : null
        );
    }
}
