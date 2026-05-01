package com.tripify.tickets.service.service.filter;

import com.tripify.tickets.service.model.filter.TicketFilterCatalog;
import com.tripify.tickets.service.model.unified.BaggageAllowance;
import com.tripify.tickets.service.model.unified.Journey;
import com.tripify.tickets.service.model.unified.Transfers;
import com.tripify.tickets.service.model.unified.UnifiedOffer;
import org.springframework.stereotype.Component;

@Component
public class TicketFilterMatcher {

    public boolean matches(UnifiedOffer offer, String filterId) {
        return switch (TicketFilterCatalog.normalizeId(filterId)) {
            case TicketFilterCatalog.DIRECT -> journeysMatch(offer, transfers -> transfers.isDirect());
            case TicketFilterCatalog.MAX_1_STOP -> journeysMatch(offer, transfers -> transfers.count() <= 1);
            case TicketFilterCatalog.MAX_2_STOPS -> journeysMatch(offer, transfers -> transfers.count() <= 2);
            case TicketFilterCatalog.NO_OVERNIGHT -> journeysMatch(offer, transfers -> !transfers.hasOvernight());
            case TicketFilterCatalog.NO_AIRPORT_CHANGE ->
                    journeysMatch(offer, transfers -> !transfers.requiresAirportChange());
            case TicketFilterCatalog.NO_SELF_TRANSFER ->
                    journeysMatch(offer, transfers -> !transfers.requiresSelfTransfer());
            case TicketFilterCatalog.REFUNDABLE -> offer.fare() != null && offer.fare().refundable();
            case TicketFilterCatalog.CHECKED_BAGGAGE -> hasCheckedBaggage(offer);
            default -> false;
        };
    }

    private static boolean journeysMatch(UnifiedOffer offer, java.util.function.Predicate<Transfers> predicate) {
        if (offer.journeys() == null || offer.journeys().isEmpty()) {
            return false;
        }
        return offer.journeys().stream()
                .map(Journey::transfers)
                .allMatch(transfers -> transfers != null && predicate.test(transfers));
    }

    private static boolean hasCheckedBaggage(UnifiedOffer offer) {
        if (offer.baggage() == null || offer.baggage().checked() == null) {
            return false;
        }
        BaggageAllowance checked = offer.baggage().checked();
        return checked.included();
    }
}
