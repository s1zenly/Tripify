package com.tripify.hotels.service.service.mapper;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tripify.hotels.generated.model.CheckInOut;
import com.tripify.hotels.generated.model.Facility;
import com.tripify.hotels.generated.model.GpsCoordinates;
import com.tripify.hotels.generated.model.HotelCard;
import com.tripify.hotels.generated.model.HotelDetails;
import com.tripify.hotels.generated.model.HotelReviewsFull;
import com.tripify.hotels.generated.model.HotelReviewsShort;
import com.tripify.hotels.generated.model.HotelScore;
import com.tripify.hotels.generated.model.HotelScoreBreakdown;
import com.tripify.hotels.generated.model.HotelsResponse;
import com.tripify.hotels.generated.model.NearbyPlace;
import com.tripify.hotels.generated.model.PaymentMethods;
import com.tripify.hotels.generated.model.PaymentMethodsCardsInfo;
import com.tripify.hotels.generated.model.PaymentMethodsCashInfo;
import com.tripify.hotels.generated.model.Photo;
import com.tripify.hotels.generated.model.RefundCondition;
import com.tripify.hotels.generated.model.RefundRule;
import com.tripify.hotels.generated.model.ReviewComment;
import com.tripify.hotels.generated.model.ReviewsClasses;
import com.tripify.hotels.generated.model.TermsPlacement;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.model.HotelFacility;
import com.tripify.hotels.service.model.HotelNearbyPlace;
import com.tripify.hotels.service.model.HotelPhoto;
import com.tripify.hotels.service.model.HotelPaymentMethods;
import com.tripify.hotels.service.model.HotelRefundCondition;
import com.tripify.hotels.service.model.HotelReviewsSummary;
import com.tripify.hotels.service.model.HotelTermsPlacement;
import com.tripify.hotels.service.model.documents.HotelReviewsDocument;
import com.tripify.hotels.service.model.documents.ReviewCommentDocument;
import com.tripify.hotels.service.service.HotelPhotoStorageService;
import com.tripify.hotels.service.service.currency.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelReadMapper {

    private final HotelPhotoStorageService photoStorageService;
    private final CurrencyConversionService currencyConversion;

    public HotelsResponse toHotelsResponse(List<HotelCard> hotels, UUID nextCursor) {
        HotelsResponse response = new HotelsResponse().hotels(hotels);
        response.setNextCursor(nextCursor);
        return response;
    }

    public HotelCard toHotelCard(
            Hotel hotel,
            List<HotelFacility> facilities,
            List<String> tags,
            List<HotelPhoto> photos,
            HotelReviewsSummary reviewsSummary,
            String displayCurrency
    ) {
        return new HotelCard()
                .hotelId(hotel.id())
                .title(hotel.title())
                .link(toUri(hotel.externalLink()))
                .description(emptyToNull(hotel.description()))
                .address(emptyToNull(hotel.address()))
                .gpsCoordinates(toGpsCoordinates(hotel))
                .city(hotel.city())
                .country(hotel.country())
                .currency(displayCurrency)
                .price(toLongPrice(currencyConversion.fromStorageCurrency(hotel.price(), displayCurrency)))
                .hotelClass(normalizeHotelClass(hotel.hotelClass()))
                .reviews(toReviewsShort(hotel, reviewsSummary))
                .facilities(toFacilities(facilities))
                .tags(tags != null ? tags : List.of())
                .photos(toHotelPhotos(photos));
    }

    public HotelDetails toHotelDetails(
            Hotel hotel,
            List<HotelFacility> facilities,
            List<String> tags,
            List<HotelPhoto> photos,
            List<HotelNearbyPlace> nearbyPlaces,
            HotelTermsPlacement termsPlacement,
            List<HotelRefundCondition> refundConditions,
            HotelPaymentMethods paymentMethods,
            HotelReviewsSummary reviewsSummary,
            HotelReviewsDocument reviewsDocument,
            com.tripify.hotels.service.model.HotelScore hotelScore,
            String displayCurrency
    ) {
        return new HotelDetails()
                .hotelId(hotel.id())
                .title(hotel.title())
                .link(toUri(hotel.externalLink()))
                .description(emptyToNull(hotel.description()))
                .address(emptyToNull(hotel.address()))
                .gpsCoordinates(toGpsCoordinates(hotel))
                .city(hotel.city())
                .country(hotel.country())
                .currency(displayCurrency)
                .price(toLongPrice(currencyConversion.fromStorageCurrency(hotel.price(), displayCurrency)))
                .hotelClass(normalizeHotelClass(hotel.hotelClass()))
                .reviews(toReviewsFull(hotel, reviewsSummary, reviewsDocument))
                .facilities(toFacilities(facilities))
                .tags(tags != null ? tags : List.of())
                .photos(toHotelPhotos(photos))
                .nearbyPlaces(toNearbyPlaces(nearbyPlaces))
                .termsPlacement(toTermsPlacement(termsPlacement, refundConditions))
                .paymentMethods(toPaymentMethods(paymentMethods))
                .score(toHotelScore(hotelScore));
    }

    private HotelReviewsShort toReviewsShort(Hotel hotel, HotelReviewsSummary reviewsSummary) {
        HotelReviewsShort reviews = new HotelReviewsShort()
                .total(hotel.reviewsTotal())
                .rating(hotel.reviewsRating() != null ? hotel.reviewsRating().doubleValue() : 0.0);

        if (reviewsSummary != null) {
            reviews.reviewsClasses(toReviewsClasses(reviewsSummary));
        }

        return reviews;
    }

    private HotelReviewsFull toReviewsFull(
            Hotel hotel,
            HotelReviewsSummary reviewsSummary,
            HotelReviewsDocument reviewsDocument
    ) {
        HotelReviewsFull reviews = new HotelReviewsFull()
                .total(hotel.reviewsTotal())
                .rating(hotel.reviewsRating() != null ? hotel.reviewsRating().doubleValue() : 0.0);

        if (reviewsSummary != null) {
            reviews.reviewsClasses(toReviewsClasses(reviewsSummary));
        }

        if (reviewsDocument != null) {
            reviews.reviewsHistogram(reviewsDocument.reviewsHistogram());
            reviews.totalComments(reviewsDocument.totalComments());
            reviews.comments(toReviewComments(reviewsDocument.comments()));
        } else {
            reviews.reviewsHistogram(Map.of());
            reviews.totalComments(0);
            reviews.comments(List.of());
        }

        return reviews;
    }

    private ReviewsClasses toReviewsClasses(HotelReviewsSummary summary) {
        return new ReviewsClasses()
                .cleanliness(toDouble(summary.cleanliness()))
                .service(toDouble(summary.service()))
                .priceQuality(toDouble(summary.priceQuality()))
                .room(toDouble(summary.room()))
                .location(toDouble(summary.location()));
    }

    private List<ReviewComment> toReviewComments(List<ReviewCommentDocument> comments) {
        if (comments == null || comments.isEmpty()) {
            return List.of();
        }

        return comments.stream()
                .map(comment -> new ReviewComment()
                        .author(comment.author())
                        .country(comment.country())
                        .vacationType(comment.vacationType())
                        .rating(comment.rating())
                        .goodPart(comment.goodPart())
                        .badPart(comment.badPart())
                        .commonText(comment.commonText())
                        .reviewDate(comment.reviewDate())
                        .photos(toPhotos(comment.photoS3Keys())))
                .toList();
    }

    private List<Photo> toPhotos(List<String> s3Keys) {
        if (s3Keys == null || s3Keys.isEmpty()) {
            return List.of();
        }

        return s3Keys.stream()
                .map(key -> new Photo().link(URI.create(photoStorageService.buildPublicUrl(key))))
                .toList();
    }

    private List<Photo> toHotelPhotos(List<HotelPhoto> photos) {
        if (photos == null || photos.isEmpty()) {
            return List.of();
        }

        return photos.stream()
                .map(photo -> new Photo().link(URI.create(photoStorageService.buildPublicUrl(photo.s3Key()))))
                .toList();
    }

    private List<Facility> toFacilities(List<HotelFacility> facilities) {
        if (facilities == null || facilities.isEmpty()) {
            return List.of();
        }

        return facilities.stream()
                .map(facility -> new Facility()
                        .type(facility.facilityType())
                        .isFree(facility.isFree()))
                .toList();
    }

    private Map<String, List<NearbyPlace>> toNearbyPlaces(List<HotelNearbyPlace> nearbyPlaces) {
        if (nearbyPlaces == null || nearbyPlaces.isEmpty()) {
            return Map.of();
        }

        Map<String, List<NearbyPlace>> result = new LinkedHashMap<>();

        for (HotelNearbyPlace place : nearbyPlaces) {
            result.computeIfAbsent(place.category(), ignored -> new ArrayList<>())
                    .add(new NearbyPlace()
                            .title(place.title())
                            .distance(place.distanceValue() != null ? place.distanceValue().doubleValue() : 0.0)
                            .unit(place.distanceUnit()));
        }

        return result;
    }

    private TermsPlacement toTermsPlacement(
            HotelTermsPlacement terms,
            List<HotelRefundCondition> refundConditions
    ) {
        if (terms == null) {
            return null;
        }

        TermsPlacement result = new TermsPlacement()
                .cancellation(terms.cancellation())
                .smoking(terms.smoking())
                .petFriendly(terms.petFriendly())
                .partyFriendly(terms.partyFriendly())
                .ageRestriction(terms.ageRestriction() != null ? terms.ageRestriction().toString() : null)
                .additionalInfo(terms.additionalInfo());

        result.checkIn(new CheckInOut()
                .afterTime(terms.checkInAfterTime())
                .beforeTime(terms.checkInBeforeTime())
                .timezone(terms.timezone()));

        result.checkOut(new CheckInOut()
                .afterTime(terms.checkOutAfterTime())
                .beforeTime(terms.checkOutBeforeTime())
                .timezone(terms.timezone()));

        if (refundConditions != null && !refundConditions.isEmpty()) {
            result.refundRule(new RefundRule()
                    .refundPrepayment(terms.refundPrepayment())
                    .conditions(refundConditions.stream()
                            .map(condition -> new RefundCondition()
                                    .quantityPercent(condition.quantityPercent())
                                    .condition(condition.conditionDescription()))
                            .toList()));
        }

        return result;
    }

    private PaymentMethods toPaymentMethods(HotelPaymentMethods paymentMethods) {
        if (paymentMethods == null) {
            return null;
        }

        return new PaymentMethods()
                .cashInfo(new PaymentMethodsCashInfo()
                        .isCash(paymentMethods.isCash())
                        .currency(paymentMethods.cashCurrencies()))
                .cardsInfo(new PaymentMethodsCardsInfo()
                        .isCard(paymentMethods.isCard())
                        .cardTypes(paymentMethods.cardTypes()));
    }

    private HotelScore toHotelScore(com.tripify.hotels.service.model.HotelScore hotelScore) {
        if (hotelScore == null) {
            return null;
        }

        return new HotelScore()
                ._final(hotelScore.finalScore() != null ? hotelScore.finalScore().doubleValue() : null)
                .breakdown(new HotelScoreBreakdown()
                        .price(toDouble(hotelScore.priceScore()))
                        .rating(toDouble(hotelScore.ratingScore()))
                        .location(toDouble(hotelScore.locationScore()))
                        .facilities(toDouble(hotelScore.facilitiesScore())));
    }

    private GpsCoordinates toGpsCoordinates(Hotel hotel) {
        return new GpsCoordinates()
                .latitude(hotel.latitude() != null ? hotel.latitude().doubleValue() : 0.0)
                .longitude(hotel.longitude() != null ? hotel.longitude().doubleValue() : 0.0);
    }

    private static URI toUri(String link) {
        if (link == null || link.isBlank()) {
            return null;
        }

        return URI.create(link);
    }

    private static String emptyToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private static long toLongPrice(BigDecimal price) {
        return price != null ? price.longValue() : 0L;
    }

    private static int normalizeHotelClass(Integer hotelClass) {
        if (hotelClass == null || hotelClass < 1) {
            return 1;
        }

        return Math.min(hotelClass, 5);
    }

    private static Double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }
}
