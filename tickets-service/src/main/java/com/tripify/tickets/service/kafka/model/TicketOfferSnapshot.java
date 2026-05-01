package com.tripify.tickets.service.kafka.model;

public record TicketOfferSnapshot(
        String tid,
        String providerCode,
        String providerOfferId,
        Long priceAmount,
        String priceCurrency,
        String validatingAirlineIata,
        Integer seatsLeft
) {
}
