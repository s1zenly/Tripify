package com.tripify.tickets.service.provider.emulation;

import com.tripify.tickets.service.model.search.TicketSearchRequest;
import com.tripify.tickets.service.model.unified.*;
import com.tripify.tickets.service.provider.TicketProvider;
import com.tripify.tickets.service.provider.TicketsProviderAdapter;
import com.tripify.tickets.service.provider.emulation.raw.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EmulationProviderAdapter implements TicketsProviderAdapter<EmulationRawOffer> {

    @Override
    public UnifiedOffersResponse adapt(List<EmulationRawOffer> providerOffers, TicketSearchRequest request) {
        if (providerOffers == null || providerOffers.isEmpty()) {
            return UnifiedOffersResponse.builder().offers(List.of()).build();
        }

        List<UnifiedOffer> offers = providerOffers.stream()
                .map(raw -> toUnifiedOffer(raw, request))
                .collect(Collectors.toList());

        return UnifiedOffersResponse.builder().offers(offers).build();
    }

    private UnifiedOffer toUnifiedOffer(EmulationRawOffer raw, TicketSearchRequest request) {
        TicketProvider provider = TicketProvider.EMULATION;

        return UnifiedOffer.builder()
                .unifiedOfferId("ticket_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8))
                .provider(ProviderInfo.builder()
                        .code(provider)
                        .offerId(raw.offerId())
                        .proposalId(raw.proposalId())
                        .agencyId(raw.agencyId())
                        .build())
                .deeplink(raw.buyUrl())
                .passengers(toPassengers(raw.pax()))
                .price(toPrice(raw.price()))
                .journeys(raw.legs().stream().map(this::toJourney).collect(Collectors.toList()))
                .baggage(toBaggage(raw.baggage()))
                .fare(toFare(raw.fareRules()))
                .validatingAirline(toAirline(raw.validatingCarrier()))
                .availability(toAvailability(raw.availability()))
                .build();
    }

    private Passengers toPassengers(EmulationRawPassengers pax) {
        return Passengers.builder()
                .adults(pax.adt())
                .children(pax.chd())
                .build();
    }

    private Price toPrice(EmulationRawPrice raw) {
        return Price.builder()
                .amount(raw.value())
                .currency(Currency.fromCode(raw.currency()))
                .originalAmount(raw.originalValue())
                .originalCurrency(raw.originalCurrency() != null
                        ? Currency.fromCode(raw.originalCurrency())
                        : null)
                .build();
    }

    private Journey toJourney(EmulationRawLeg leg) {
        JourneyType type = JourneyType.fromValue(leg.direction());

        return Journey.builder()
                .type(type)
                .origin(toLocation(leg.from()))
                .destination(toLocation(leg.to()))
                .departure(toSchedule(leg.dep()))
                .arrival(toSchedule(leg.arr()))
                .durationMinutes(leg.durationMin())
                .transfers(Transfers.builder()
                        .count(leg.stops())
                        .isDirect(leg.stops() == 0)
                        .hasOvernight(leg.overnight())
                        .requiresAirportChange(leg.airportChange())
                        .requiresSelfTransfer(leg.selfTransfer())
                        .places(Collections.emptyList())
                        .build())
                .segments(leg.segments().stream().map(this::toSegment).collect(Collectors.toList()))
                .build();
    }

    private Segment toSegment(EmulationRawSegment raw) {
        return Segment.builder()
                .segmentId(raw.id())
                .origin(toLocation(raw.from()))
                .destination(toLocation(raw.to()))
                .departure(toSchedule(raw.dep()))
                .arrival(toSchedule(raw.arr()))
                .durationMinutes(raw.durationMin())
                .marketingAirline(toAirline(raw.marketing()))
                .operatingAirline(toAirline(raw.operating()))
                .flightNumber(raw.flightNo())
                .aircraft(raw.plane())
                .cabinClass(CabinClass.fromValue(raw.cabin()))
                .build();
    }

    private LocationPoint toLocation(EmulationRawPoint point) {
        return LocationPoint.builder()
                .cityCode(point.cityIata())
                .airportCode(point.airportIata())
                .airportName(point.airportTitle())
                .terminal(point.terminal())
                .build();
    }

    private SchedulePoint toSchedule(EmulationRawSchedule schedule) {
        return SchedulePoint.builder()
                .datetime(schedule.at())
                .timezone(schedule.tz())
                .build();
    }

    private Airline toAirline(EmulationRawCarrier carrier) {
        return Airline.builder()
                .code(carrier.iata())
                .name(carrier.title())
                .logoUrl(carrier.logo())
                .build();
    }

    private Baggage toBaggage(EmulationRawBaggage raw) {
        return Baggage.builder()
                .checked(toBaggageAllowance(raw.checkedBag()))
                .handLuggage(toBaggageAllowance(raw.carryOn()))
                .build();
    }

    private BaggageAllowance toBaggageAllowance(EmulationRawBaggageItem raw) {
        return BaggageAllowance.builder()
                .included(raw.included())
                .pieces(raw.count())
                .weightKg(raw.weight())
                .build();
    }

    private Fare toFare(EmulationRawFareRules raw) {
        return Fare.builder()
                .refundable(raw.canRefund())
                .exchangeable(raw.canExchange())
                .fareFamily(raw.familyName())
                .build();
    }

    private Availability toAvailability(EmulationRawAvailability raw) {
        return Availability.builder()
                .seatsLeft(raw.seats())
                .lastSeenAt(raw.seenAt())
                .expiresAt(raw.expiresAt())
                .build();
    }
}
