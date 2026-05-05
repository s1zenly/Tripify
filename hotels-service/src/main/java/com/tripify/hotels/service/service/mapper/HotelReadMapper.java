package com.tripify.hotels.service.service.mapper;

import java.math.BigDecimal;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tripify.hotels.generated.model.Bathrooms;
import com.tripify.hotels.generated.model.Bed;
import com.tripify.hotels.generated.model.CancelPenalty;
import com.tripify.hotels.generated.model.CancellationPolicy;
import com.tripify.hotels.generated.model.CheckInOut;
import com.tripify.hotels.generated.model.Discount;
import com.tripify.hotels.generated.model.Facility;
import com.tripify.hotels.generated.model.GpsCoordinates;
import com.tripify.hotels.generated.model.HotelCard;
import com.tripify.hotels.generated.model.HotelDetails;
import com.tripify.hotels.generated.model.HotelReviewsFull;
import com.tripify.hotels.generated.model.HotelReviewsShort;
import com.tripify.hotels.generated.model.HotelScore;
import com.tripify.hotels.generated.model.HotelScoreBreakdown;
import com.tripify.hotels.generated.model.HotelsResponse;
import com.tripify.hotels.generated.model.MealPlan;
import com.tripify.hotels.generated.model.Money;
import com.tripify.hotels.generated.model.NearbyPlace;
import com.tripify.hotels.generated.model.Occupancy;
import com.tripify.hotels.generated.model.Photo;
import com.tripify.hotels.generated.model.Rate;
import com.tripify.hotels.generated.model.RateAvailability;
import com.tripify.hotels.generated.model.RatePayment;
import com.tripify.hotels.generated.model.RatePricing;
import com.tripify.hotels.generated.model.ReviewComment;
import com.tripify.hotels.generated.model.ReviewsClasses;
import com.tripify.hotels.generated.model.Room;
import com.tripify.hotels.generated.model.RoomArea;
import com.tripify.hotels.generated.model.RoomPhoto;
import com.tripify.hotels.generated.model.TermsPlacement;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.model.HotelFacility;
import com.tripify.hotels.service.model.HotelNearbyPlace;
import com.tripify.hotels.service.model.HotelPhoto;
import com.tripify.hotels.service.model.HotelReviewsSummary;
import com.tripify.hotels.service.model.HotelTermsPlacement;
import com.tripify.hotels.service.model.documents.CancelPenaltyDocument;
import com.tripify.hotels.service.model.documents.CancellationPolicyDocument;
import com.tripify.hotels.service.model.documents.HotelReviewsDocument;
import com.tripify.hotels.service.model.documents.HotelRoomsDocument;
import com.tripify.hotels.service.model.documents.MoneyDocument;
import com.tripify.hotels.service.model.documents.PricingDocument;
import com.tripify.hotels.service.model.documents.RateDocument;
import com.tripify.hotels.service.model.documents.ReviewCommentDocument;
import com.tripify.hotels.service.model.documents.RoomDocument;
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
            String displayCurrency,
            long nights
    ) {
        BigDecimal totalPrice = hotel.price().multiply(BigDecimal.valueOf(nights));

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
                .price(toLongPrice(currencyConversion.fromStorageCurrency(totalPrice, displayCurrency)))
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
            HotelReviewsSummary reviewsSummary,
            HotelReviewsDocument reviewsDocument,
            HotelRoomsDocument roomsDocument,
            com.tripify.hotels.service.model.HotelScore hotelScore,
            String displayCurrency,
            long nights
    ) {
        BigDecimal totalPrice = hotel.price().multiply(BigDecimal.valueOf(nights));

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
                .price(toLongPrice(currencyConversion.fromStorageCurrency(totalPrice, displayCurrency)))
                .hotelClass(normalizeHotelClass(hotel.hotelClass()))
                .reviews(toReviewsFull(hotel, reviewsSummary, reviewsDocument))
                .facilities(toFacilities(facilities))
                .tags(tags != null ? tags : List.of())
                .photos(toHotelPhotos(photos))
                .nearbyPlaces(toNearbyPlaces(nearbyPlaces))
                .termsPlacement(toTermsPlacement(termsPlacement))
                .rooms(toRooms(roomsDocument))
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
                        .photos(toReviewPhotos(comment.photoS3Keys())))
                .toList();
    }

    private List<Photo> toReviewPhotos(List<String> s3Keys) {
        if (s3Keys == null || s3Keys.isEmpty()) {
            return List.of();
        }

        return s3Keys.stream()
                .map(key -> new Photo().link(URI.create(photoStorageService.buildReviewPhotoUrl(key))))
                .toList();
    }

    private List<Photo> toHotelPhotos(List<HotelPhoto> photos) {
        if (photos == null || photos.isEmpty()) {
            return List.of();
        }

        return photos.stream()
                .map(photo -> new Photo().link(URI.create(photoStorageService.buildHotelPhotoUrl(photo.s3Key()))))
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

    private TermsPlacement toTermsPlacement(HotelTermsPlacement terms) {
        if (terms == null) {
            return null;
        }

        TermsPlacement result = new TermsPlacement()
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

        return result;
    }

    private List<Room> toRooms(HotelRoomsDocument roomsDocument) {
        if (roomsDocument == null || roomsDocument.rooms() == null || roomsDocument.rooms().isEmpty()) {
            return List.of();
        }

        return roomsDocument.rooms().stream()
                .map(this::toRoom)
                .toList();
    }

    private Room toRoom(RoomDocument doc) {
        Room room = new Room()
                .roomId(doc.roomId())
                .name(doc.name())
                .roomType(doc.roomType())
                .description(doc.description())
                .floor(doc.floor())
                .smokingAllowed(doc.smokingAllowed())
                .views(doc.views() != null ? doc.views() : List.of())
                .amenities(doc.amenities() != null ? doc.amenities() : List.of())
                .accessibility(doc.accessibility() != null ? doc.accessibility() : List.of());

        if (doc.area() != null) {
            room.area(new RoomArea()
                    .value(doc.area().value())
                    .unit(doc.area().unit()));
        }

        if (doc.photos() != null) {
            room.photos(doc.photos().stream()
                    .map(p -> new RoomPhoto()
                            .url(p.s3Key() != null
                                    ? URI.create(photoStorageService.buildRoomPhotoUrl(p.s3Key()))
                                    : null))
                    .toList());
        }

        if (doc.beds() != null) {
            room.beds(doc.beds().stream()
                    .map(b -> new Bed().type(b.type()).count(b.count()))
                    .toList());
        }

        if (doc.bathrooms() != null) {
            room.bathrooms(new Bathrooms()
                    .count(doc.bathrooms().count())
                    ._private(doc.bathrooms().isPrivate()));
        }

        if (doc.occupancy() != null) {
            room.occupancy(new Occupancy()
                    .minAdults(doc.occupancy().minAdults())
                    .maxAdults(doc.occupancy().maxAdults())
                    .maxChildren(doc.occupancy().maxChildren())
                    .maxGuests(doc.occupancy().maxGuests()));
        }

        if (doc.rates() != null) {
            room.rates(doc.rates().stream().map(this::toRate).toList());
        }

        return room;
    }

    private Rate toRate(RateDocument doc) {
        Rate rate = new Rate()
                .rateId(doc.rateId())
                .title(doc.title())
                .tags(doc.tags() != null ? doc.tags() : List.of())
                .instantConfirmation(doc.instantConfirmation())
                .perks(doc.perks() != null ? doc.perks() : List.of());

        if (doc.pricing() != null) {
            rate.pricing(toRatePricing(doc.pricing()));
        }

        if (doc.payment() != null) {
            rate.payment(new RatePayment()
                    .type(doc.payment().type())
                    .prepaymentRequired(doc.payment().prepaymentRequired())
                    .cards(doc.payment().cards() != null ? doc.payment().cards() : List.of()));
        }

        if (doc.mealPlan() != null) {
            rate.mealPlan(new MealPlan()
                    .type(doc.mealPlan().type())
                    .description(doc.mealPlan().description()));
        }

        if (doc.cancellationPolicy() != null) {
            rate.cancellationPolicy(toCancellationPolicy(doc.cancellationPolicy()));
        }

        if (doc.availability() != null) {
            rate.availability(new RateAvailability()
                    .roomsLeft(doc.availability().roomsLeft())
                    .soldOut(doc.availability().soldOut()));
        }

        return rate;
    }

    private RatePricing toRatePricing(PricingDocument doc) {
        RatePricing pricing = new RatePricing();
        pricing.basePrice(toMoney(doc.basePrice()));
        pricing.taxesAndFees(toMoney(doc.taxesAndFees()));
        pricing.totalPrice(toMoney(doc.totalPrice()));
        pricing.pricePerNight(toMoney(doc.pricePerNight()));

        if (doc.discount() != null) {
            pricing.discount(new Discount()
                    .percent(doc.discount().percent())
                    .amount(doc.discount().amount()));
        }

        return pricing;
    }

    private Money toMoney(MoneyDocument doc) {
        if (doc == null) {
            return null;
        }
        return new Money().amount(doc.amount()).currency(doc.currency());
    }

    private CancellationPolicy toCancellationPolicy(CancellationPolicyDocument doc) {
        CancellationPolicy policy = new CancellationPolicy()
                .refundable(doc.refundable())
                .freeCancellationUntil(doc.freeCancellationUntil() != null
                        ? OffsetDateTime.ofInstant(doc.freeCancellationUntil(), ZoneOffset.UTC)
                        : null);

        if (doc.cancelPenalty() != null) {
            policy.cancelPenalty(toCancelPenalty(doc.cancelPenalty()));
        }

        if (doc.noShowPenalty() != null) {
            policy.noShowPenalty(toCancelPenalty(doc.noShowPenalty()));
        }

        return policy;
    }

    private CancelPenalty toCancelPenalty(CancelPenaltyDocument doc) {
        return new CancelPenalty()
                .type(doc.type())
                .amount(doc.amount())
                .percent(doc.percent());
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
