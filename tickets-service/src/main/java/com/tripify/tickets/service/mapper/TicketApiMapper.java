package com.tripify.tickets.service.mapper;

import com.tripify.tickets.generated.model.AirlineSummary;
import com.tripify.tickets.generated.model.Currency;
import com.tripify.tickets.generated.model.DateTimePoint;
import com.tripify.tickets.generated.model.Duration;
import com.tripify.tickets.generated.model.JourneyType;
import com.tripify.tickets.generated.model.LocationSummary;
import com.tripify.tickets.generated.model.Ticket;
import com.tripify.tickets.generated.model.TicketBaggage;
import com.tripify.tickets.generated.model.TicketFare;
import com.tripify.tickets.generated.model.TicketJourney;
import com.tripify.tickets.generated.model.TicketPrice;
import com.tripify.tickets.generated.model.TicketsResponse;
import com.tripify.tickets.generated.model.TransfersSummary;
import com.tripify.tickets.service.model.unified.Airline;
import com.tripify.tickets.service.model.unified.Baggage;
import com.tripify.tickets.service.model.unified.Fare;
import com.tripify.tickets.service.model.unified.Journey;
import com.tripify.tickets.service.model.unified.LocationPoint;
import com.tripify.tickets.service.model.unified.Price;
import com.tripify.tickets.service.model.unified.SchedulePoint;
import com.tripify.tickets.service.model.unified.Segment;
import com.tripify.tickets.service.model.unified.Transfers;
import com.tripify.tickets.service.model.unified.UnifiedOffer;
import com.tripify.tickets.service.service.currency.TicketOfferCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketApiMapper {

    private final TicketOfferCurrencyService ticketOfferCurrencyService;

    public TicketsResponse toTicketsResponse(List<UnifiedOffer> offers, String displayCurrency) {
        return new TicketsResponse(offers.stream().map(offer -> toTicket(offer, displayCurrency)).toList());
    }

    private Ticket toTicket(UnifiedOffer offer, String displayCurrency) {
        return new Ticket(
                offer.unifiedOfferId(),
                URI.create(offer.deeplink()),
                toPrice(offer.price(), displayCurrency),
                toApiPassengers(offer.passengers()),
                offer.journeys().stream().map(this::toJourney).toList(),
                toBaggage(offer.baggage()),
                toFare(offer.fare()),
                offer.availability() != null ? offer.availability().seatsLeft() : null
        );
    }

    private TicketPrice toPrice(Price price, String displayCurrency) {
        return new TicketPrice(
                ticketOfferCurrencyService.fromStorageAmount(price.amount(), displayCurrency),
                Currency.fromValue(displayCurrency)
        );
    }

    private com.tripify.tickets.generated.model.Passengers toApiPassengers(
            com.tripify.tickets.service.model.unified.Passengers passengers
    ) {
        return new com.tripify.tickets.generated.model.Passengers(
                passengers.adults(),
                passengers.children(),
                passengers.infants()
        );
    }

    private TicketJourney toJourney(Journey journey) {
        Segment firstSegment = journey.segments().getFirst();

        return new TicketJourney(
                JourneyType.fromValue(journey.type().getValue()),
                toLocation(journey.origin()),
                toLocation(journey.destination()),
                toDateTimePoint(journey.departure()),
                toDateTimePoint(journey.arrival()),
                new Duration(journey.durationMinutes()),
                toTransfers(journey.transfers()),
                toAirline(firstSegment.marketingAirline())
        );
    }

    private LocationSummary toLocation(LocationPoint point) {
        return new LocationSummary(point.cityCode(), point.cityName(), point.airportCode());
    }

    private DateTimePoint toDateTimePoint(SchedulePoint schedule) {
        return new DateTimePoint(schedule.datetime());
    }

    private TransfersSummary toTransfers(Transfers transfers) {
        return new TransfersSummary(transfers.count(), transfers.isDirect());
    }

    private AirlineSummary toAirline(Airline airline) {
        return new AirlineSummary(airline.code(), airline.name(), URI.create(airline.logoUrl()));
    }

    private TicketBaggage toBaggage(Baggage baggage) {
        return new TicketBaggage(
                toApiBaggageAllowance(baggage.checked()),
                toApiBaggageAllowance(baggage.handLuggage())
        );
    }

    private com.tripify.tickets.generated.model.BaggageAllowance toApiBaggageAllowance(
            com.tripify.tickets.service.model.unified.BaggageAllowance allowance
    ) {
        return new com.tripify.tickets.generated.model.BaggageAllowance(allowance.included())
                .pieces(allowance.pieces())
                .weightKg(allowance.weightKg());
    }

    private TicketFare toFare(Fare fare) {
        return new TicketFare(fare.refundable(), fare.exchangeable());
    }
}
