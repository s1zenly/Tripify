package com.tripify.tickets.service.mapper;

import com.tripify.tickets.generated.model.AirlineSummary;
import com.tripify.tickets.generated.model.CabinClass;
import com.tripify.tickets.generated.model.Currency;
import com.tripify.tickets.generated.model.Duration;
import com.tripify.tickets.generated.model.JourneyType;
import com.tripify.tickets.generated.model.LocationDetail;
import com.tripify.tickets.generated.model.TicketBaggage;
import com.tripify.tickets.generated.model.TicketDetail;
import com.tripify.tickets.generated.model.TicketDetailFare;
import com.tripify.tickets.generated.model.TicketDetailResponse;
import com.tripify.tickets.generated.model.TicketJourneyDetail;
import com.tripify.tickets.generated.model.TicketPrice;
import com.tripify.tickets.generated.model.TicketSegment;
import com.tripify.tickets.generated.model.TransfersDetail;
import com.tripify.tickets.service.model.unified.Airline;
import com.tripify.tickets.service.model.unified.Baggage;
import com.tripify.tickets.service.model.unified.Fare;
import com.tripify.tickets.service.model.unified.Journey;
import com.tripify.tickets.service.model.unified.LocationPoint;
import com.tripify.tickets.service.model.unified.Price;
import com.tripify.tickets.service.model.unified.Segment;
import com.tripify.tickets.service.model.unified.Transfers;
import com.tripify.tickets.service.model.unified.UnifiedOffer;
import com.tripify.tickets.service.service.currency.TicketOfferCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketDetailApiMapper {

    private final TicketOfferCurrencyService ticketOfferCurrencyService;

    public TicketDetailResponse toTicketDetailResponse(UnifiedOffer offer, String displayCurrency) {
        return new TicketDetailResponse(toTicketDetail(offer, displayCurrency));
    }

    private TicketDetail toTicketDetail(UnifiedOffer offer, String displayCurrency) {
        return new TicketDetail(
                offer.unifiedOfferId(),
                URI.create(offer.deeplink()),
                toPrice(offer.price(), displayCurrency),
                toApiPassengers(offer.passengers()),
                offer.journeys().stream().map(this::toJourneyDetail).toList(),
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
                passengers.children()
        );
    }

    private TicketJourneyDetail toJourneyDetail(Journey journey) {
        return new TicketJourneyDetail(
                JourneyType.fromValue(journey.type().getValue()),
                toLocationDetail(journey.origin()),
                toLocationDetail(journey.destination()),
                toSchedulePoint(journey.departure()),
                toSchedulePoint(journey.arrival()),
                new Duration(journey.durationMinutes()),
                toTransfersDetail(journey.transfers()),
                journey.segments().stream().map(this::toSegment).toList()
        );
    }

    private LocationDetail toLocationDetail(LocationPoint point) {
        return new LocationDetail(point.cityCode(), point.airportCode(), point.airportName())
                .terminal(point.terminal());
    }

    private com.tripify.tickets.generated.model.SchedulePoint toSchedulePoint(
            com.tripify.tickets.service.model.unified.SchedulePoint schedule
    ) {
        return new com.tripify.tickets.generated.model.SchedulePoint(schedule.datetime(), schedule.timezone());
    }

    private TransfersDetail toTransfersDetail(Transfers transfers) {
        List<com.tripify.tickets.generated.model.TransferPlace> places = transfers.places() != null
                ? transfers.places().stream().map(this::toTransferPlace).toList()
                : Collections.emptyList();

        return new TransfersDetail(
                transfers.count(),
                transfers.isDirect(),
                transfers.hasOvernight(),
                transfers.requiresAirportChange(),
                transfers.requiresSelfTransfer(),
                places
        );
    }

    private com.tripify.tickets.generated.model.TransferPlace toTransferPlace(
            com.tripify.tickets.service.model.unified.TransferPlace place
    ) {
        return new com.tripify.tickets.generated.model.TransferPlace()
                .airportCode(place.airportCode())
                .airportName(place.airportName())
                .layoverMinutes(place.layoverMinutes());
    }

    private TicketSegment toSegment(Segment segment) {
        return new TicketSegment(
                segment.segmentId(),
                toLocationDetail(segment.origin()),
                toLocationDetail(segment.destination()),
                toSchedulePoint(segment.departure()),
                toSchedulePoint(segment.arrival()),
                new Duration(segment.durationMinutes()),
                toAirline(segment.marketingAirline()),
                toAirline(segment.operatingAirline()),
                segment.flightNumber(),
                segment.aircraft(),
                CabinClass.fromValue(segment.cabinClass().getValue())
        );
    }

    private AirlineSummary toAirline(Airline airline) {
        return new AirlineSummary(airline.code(), airline.name(), URI.create(airline.logoUrl()));
    }

    private TicketBaggage toBaggage(Baggage baggage) {
        return new TicketBaggage(
                toBaggageAllowance(baggage.checked()),
                toBaggageAllowance(baggage.handLuggage())
        );
    }

    private com.tripify.tickets.generated.model.BaggageAllowance toBaggageAllowance(
            com.tripify.tickets.service.model.unified.BaggageAllowance allowance
    ) {
        return new com.tripify.tickets.generated.model.BaggageAllowance(allowance.included())
                .pieces(allowance.pieces())
                .weightKg(allowance.weightKg());
    }

    private TicketDetailFare toFare(Fare fare) {
        return new TicketDetailFare(fare.refundable(), fare.exchangeable(), fare.fareFamily());
    }
}
