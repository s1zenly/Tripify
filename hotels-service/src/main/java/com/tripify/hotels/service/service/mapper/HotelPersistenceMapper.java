package com.tripify.hotels.service.service.mapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tripify.hotels.service.kafka.model.CardsInfoDto;
import com.tripify.hotels.service.kafka.model.CashInfoDto;
import com.tripify.hotels.service.kafka.model.FacilityDto;
import com.tripify.hotels.service.kafka.model.GpsCoordinatesDto;
import com.tripify.hotels.service.kafka.model.HotelsParsedEvent;
import com.tripify.hotels.service.kafka.model.KafkaHotelDto;
import com.tripify.hotels.service.kafka.model.NearbyPlaceDto;
import com.tripify.hotels.service.kafka.model.PaymentMethodsDto;
import com.tripify.hotels.service.kafka.model.PlacementTimeDto;
import com.tripify.hotels.service.kafka.model.RefundConditionDto;
import com.tripify.hotels.service.kafka.model.RefundRuleDto;
import com.tripify.hotels.service.kafka.model.ReviewsDto;
import com.tripify.hotels.service.kafka.model.TermsPlacementDto;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.model.HotelFacility;
import com.tripify.hotels.service.model.HotelNearbyPlace;
import com.tripify.hotels.service.model.HotelPaymentMethods;
import com.tripify.hotels.service.model.HotelRefundCondition;
import com.tripify.hotels.service.model.HotelReviewsSummary;
import com.tripify.hotels.service.model.HotelTermsPlacement;
import org.springframework.stereotype.Component;

@Component
public class HotelPersistenceMapper {

    public Hotel toHotel(
            KafkaHotelDto hotelDto,
            HotelsParsedEvent event,
            UUID hotelInternalId,
            Instant now
    ) {
        ReviewsDto reviews = hotelDto.reviews();
        GpsCoordinatesDto gps = hotelDto.gpsCoordinates();

        return new Hotel(
                hotelInternalId,
                hotelDto.hid(),
                event.providerName(),
                defaultString(hotelDto.title()),
                defaultString(hotelDto.link()),
                defaultString(hotelDto.description()),
                defaultString(hotelDto.address()),
                defaultString(hotelDto.city()),
                defaultString(hotelDto.country()),
                defaultString(hotelDto.currency()),
                hotelDto.price() != null ? hotelDto.price() : BigDecimal.ZERO,
                hotelDto.hotelClass() != null ? hotelDto.hotelClass() : 0,
                reviews != null ? hotelInternalId.toString() : null,
                gps != null && gps.latitude() != null ? gps.latitude() : BigDecimal.ZERO,
                gps != null && gps.longitude() != null ? gps.longitude() : BigDecimal.ZERO,
                reviews != null && reviews.total() != null ? reviews.total() : 0,
                reviews != null && reviews.rating() != null ? reviews.rating() : BigDecimal.ZERO,
                event.parsedAt(),
                event.providedAt(),
                now,
                now
        );
    }

    public List<HotelFacility> toFacilities(UUID hotelInternalId, List<FacilityDto> facilities, Instant now) {
        if (facilities == null || facilities.isEmpty()) {
            return List.of();
        }

        return facilities.stream()
                .filter(facility -> facility.type() != null && !facility.type().isBlank())
                .map(facility -> new HotelFacility(
                        hotelInternalId,
                        facility.type(),
                        Boolean.TRUE.equals(facility.free()),
                        now
                ))
                .toList();
    }

    public List<HotelNearbyPlace> toNearbyPlaces(
            UUID hotelInternalId,
            Map<String, List<NearbyPlaceDto>> nearbyPlaces,
            Instant now
    ) {
        if (nearbyPlaces == null || nearbyPlaces.isEmpty()) {
            return List.of();
        }

        List<HotelNearbyPlace> result = new ArrayList<>();

        nearbyPlaces.forEach((category, places) -> {
            if (places == null) {
                return;
            }

            for (NearbyPlaceDto place : places) {
                if (place.title() == null || place.title().isBlank()) {
                    continue;
                }

                result.add(new HotelNearbyPlace(
                        UUID.randomUUID(),
                        hotelInternalId,
                        category,
                        place.title(),
                        place.distance() != null ? place.distance() : BigDecimal.ZERO,
                        defaultString(place.unit()),
                        now
                ));
            }
        });

        return List.copyOf(result);
    }

    public HotelTermsPlacement toTermsPlacement(UUID hotelInternalId, TermsPlacementDto terms, Instant now) {
        if (terms == null) {
            return null;
        }

        PlacementTimeDto checkIn = terms.checkIn();
        PlacementTimeDto checkOut = terms.checkOut();
        RefundRuleDto refundRule = terms.refundRule();

        return new HotelTermsPlacement(
                hotelInternalId,
                checkIn != null ? checkIn.afterTime() : null,
                checkIn != null ? checkIn.beforeTime() : null,
                checkOut != null ? checkOut.afterTime() : null,
                checkOut != null ? checkOut.beforeTime() : null,
                checkIn != null ? checkIn.timezone() : null,
                Boolean.TRUE.equals(terms.cancellation()),
                refundRule != null ? refundRule.refundPrepayment() : null,
                Boolean.TRUE.equals(terms.smoking()),
                Boolean.TRUE.equals(terms.petFriendly()),
                Boolean.TRUE.equals(terms.partyFriendly()),
                terms.ageRestriction(),
                terms.additionalInfo(),
                now,
                now
        );
    }

    public List<HotelRefundCondition> toRefundConditions(
            UUID hotelInternalId,
            RefundRuleDto refundRule,
            Instant now
    ) {
        if (refundRule == null || refundRule.conditions() == null || refundRule.conditions().isEmpty()) {
            return List.of();
        }

        return refundRule.conditions().stream()
                .filter(condition -> condition.condition() != null && !condition.condition().isBlank())
                .map(condition -> toRefundCondition(hotelInternalId, condition, now))
                .toList();
    }

    public HotelPaymentMethods toPaymentMethods(UUID hotelInternalId, PaymentMethodsDto paymentMethods, Instant now) {
        if (paymentMethods == null) {
            return null;
        }

        CashInfoDto cashInfo = paymentMethods.cashInfo();
        CardsInfoDto cardsInfo = paymentMethods.cardsInfo();

        return new HotelPaymentMethods(
                hotelInternalId,
                cashInfo != null && Boolean.TRUE.equals(cashInfo.cash()),
                cashInfo != null ? cashInfo.currency() : List.of(),
                cardsInfo != null && Boolean.TRUE.equals(cardsInfo.card()),
                cardsInfo != null ? cardsInfo.cardTypes() : List.of(),
                now,
                now
        );
    }

    public HotelReviewsSummary toReviewsSummary(UUID hotelInternalId, ReviewsDto reviews, Instant now) {
        if (reviews == null) {
            return null;
        }

        Map<String, Object> reviewsClasses = reviews.reviewsClasses();

        return new HotelReviewsSummary(
                hotelInternalId,
                reviews.rating() != null ? reviews.rating() : BigDecimal.ZERO,
                reviews.total() != null ? reviews.total() : 0,
                toBigDecimal(reviewsClasses, "cleanliness"),
                toBigDecimal(reviewsClasses, "service"),
                toBigDecimal(reviewsClasses, "priceQuality"),
                toBigDecimal(reviewsClasses, "room"),
                toBigDecimal(reviewsClasses, "location"),
                now,
                now
        );
    }

    private HotelRefundCondition toRefundCondition(
            UUID hotelInternalId,
            RefundConditionDto condition,
            Instant now
    ) {
        return new HotelRefundCondition(
                UUID.randomUUID(),
                hotelInternalId,
                condition.quantityPercent() != null ? condition.quantityPercent() : 0,
                condition.condition(),
                now
        );
    }

    private static BigDecimal toBigDecimal(Map<String, Object> values, String key) {
        if (values == null || !values.containsKey(key)) {
            return null;
        }

        Object value = values.get(key);
        return switch (value) {
            case BigDecimal bigDecimal -> bigDecimal;
            case Number number -> BigDecimal.valueOf(number.doubleValue());
            case String string -> new BigDecimal(string);
            case null, default -> null;
        };
    }

    private static String defaultString(String value) {
        return value != null ? value : "";
    }
}
