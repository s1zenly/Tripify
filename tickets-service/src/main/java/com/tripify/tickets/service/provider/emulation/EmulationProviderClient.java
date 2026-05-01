package com.tripify.tickets.service.provider.emulation;

import com.tripify.tickets.service.model.search.TicketSearchRequest;
import com.tripify.tickets.service.model.unified.Currency;
import com.tripify.tickets.service.model.unified.Passengers;
import com.tripify.tickets.service.provider.TicketsProviderClient;
import com.tripify.tickets.service.provider.emulation.raw.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class EmulationProviderClient implements TicketsProviderClient<EmulationRawOffer> {

    private static final Random RND = new Random(42);

    private static final List<EmulationRawCarrier> AIRLINES = List.of(
            carrier("EK", "Emirates"),
            carrier("SU", "Aeroflot"),
            carrier("TK", "Turkish Airlines"),
            carrier("QR", "Qatar Airways"),
            carrier("FZ", "flydubai")
    );

    private static final List<String> AIRCRAFT = List.of(
            "Boeing 777", "Boeing 737", "Airbus A320", "Airbus A350", "Airbus A380"
    );

    private static final List<String> FARE_FAMILIES = List.of(
            "Economy Saver", "Economy Flex", "Premium Economy", "Business Lite"
    );

    @Override
    public List<EmulationRawOffer> fetch(TicketSearchRequest request) {
        var origin = RouteCatalog.find(request.originCityCode())
                .orElseThrow(() -> new IllegalArgumentException("Unknown origin city: " + request.originCityCode()));
        var destination = RouteCatalog.find(request.destinationCityCode())
                .orElseThrow(() -> new IllegalArgumentException("Unknown destination city: " + request.destinationCityCode()));

        int offerCount = 3 + RND.nextInt(3);
        List<EmulationRawOffer> offers = new ArrayList<>(offerCount);

        for (int i = 0; i < offerCount; i++) {
            offers.add(buildOffer(request, origin, destination, i));
        }

        return offers;
    }

    private EmulationRawOffer buildOffer(
            TicketSearchRequest request,
            RouteCatalog.RouteEndpoint origin,
            RouteCatalog.RouteEndpoint destination,
            int index
    ) {
        EmulationRawCarrier airline = AIRLINES.get(index % AIRLINES.size());
        long basePrice = 25_000L + index * 12_000L + RND.nextInt(8_000);
        boolean discounted = index % 2 == 0;

        Passengers passengers = request.passengers();
        EmulationRawPassengers pax = EmulationRawPassengers.builder()
                .adt(passengers.adults())
                .chd(passengers.children())
                .inf(passengers.infants())
                .build();

        Currency currency = Currency.RUB;

        EmulationRawPrice price = EmulationRawPrice.builder()
                .value(basePrice)
                .currency(currency.getCode())
                .originalValue(discounted ? basePrice + 5_000 : null)
                .originalCurrency(discounted ? currency.getCode() : null)
                .build();

        List<EmulationRawLeg> legs = new ArrayList<>();
        legs.add(buildLeg("outbound", origin, destination, request.departureDate(), airline, index));
        legs.add(buildLeg("return", destination, origin, request.returnDate(), airline, index + 1));

        Instant now = Instant.now();

        return EmulationRawOffer.builder()
                .offerId("emu_offer_" + UUID.randomUUID().toString().substring(0, 8))
                .proposalId("emu_proposal_" + (10_000 + index))
                .agencyId("emu_agency_1")
                .buyUrl("https://emulation.tripify.example/buy/" + index)
                .pax(pax)
                .price(price)
                .legs(legs)
                .baggage(buildBaggage(index))
                .fareRules(EmulationRawFareRules.builder()
                        .canRefund(index % 3 == 0)
                        .canExchange(true)
                        .familyName(FARE_FAMILIES.get(index % FARE_FAMILIES.size()))
                        .build())
                .validatingCarrier(airline)
                .availability(EmulationRawAvailability.builder()
                        .seats(1 + RND.nextInt(6))
                        .seenAt(now)
                        .expiresAt(now.plusSeconds(1_800))
                        .build())
                .build();
    }

    private EmulationRawLeg buildLeg(
            String direction,
            RouteCatalog.RouteEndpoint from,
            RouteCatalog.RouteEndpoint to,
            java.time.LocalDate date,
            EmulationRawCarrier airline,
            int seed
    ) {
        int departureHour = 6 + (seed * 3) % 14;
        ZoneId originZone = ZoneId.of(from.timezone());
        OffsetDateTime departure = OffsetDateTime.of(
                date,
                LocalTime.of(departureHour, 30),
                originZone.getRules().getOffset(date.atTime(LocalTime.MIDNIGHT))
        );
        int durationMinutes = 180 + (seed % 4) * 60;
        OffsetDateTime arrival = departure.plusMinutes(durationMinutes);

        EmulationRawPoint fromPoint = RouteCatalog.toRawPoint(from);
        EmulationRawPoint toPoint = RouteCatalog.toRawPoint(to);

        EmulationRawSegment segment = EmulationRawSegment.builder()
                .id("1")
                .from(fromPoint)
                .to(toPoint)
                .dep(schedule(departure, from.timezone()))
                .arr(schedule(arrival, to.timezone()))
                .durationMin(durationMinutes)
                .marketing(airline)
                .operating(airline)
                .flightNo(airline.iata() + (100 + seed))
                .plane(AIRCRAFT.get(seed % AIRCRAFT.size()))
                .cabin("economy")
                .build();

        return EmulationRawLeg.builder()
                .direction(direction)
                .from(fromPoint)
                .to(toPoint)
                .dep(schedule(departure, from.timezone()))
                .arr(schedule(arrival, to.timezone()))
                .durationMin(durationMinutes)
                .stops(0)
                .overnight(false)
                .airportChange(false)
                .selfTransfer(false)
                .segments(List.of(segment))
                .build();
    }

    private static EmulationRawSchedule schedule(OffsetDateTime at, String timezone) {
        return EmulationRawSchedule.builder()
                .at(at)
                .tz(timezone)
                .build();
    }

    private static EmulationRawBaggage buildBaggage(int index) {
        return EmulationRawBaggage.builder()
                .checkedBag(EmulationRawBaggageItem.builder()
                        .included(true)
                        .count(1)
                        .weight(23)
                        .build())
                .carryOn(EmulationRawBaggageItem.builder()
                        .included(true)
                        .count(1)
                        .weight(index % 2 == 0 ? 10 : 8)
                        .build())
                .build();
    }

    private static EmulationRawCarrier carrier(String code, String name) {
        return EmulationRawCarrier.builder()
                .iata(code)
                .title(name)
                .logo("https://pics.avs.io/200/200/" + code + ".png")
                .build();
    }
}
