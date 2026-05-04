package com.tripify.hotels.service.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import com.tripify.hotels.generated.model.HotelCard;
import com.tripify.hotels.generated.model.HotelDetails;
import com.tripify.hotels.generated.model.HotelsResponse;
import com.tripify.hotels.service.exception.BadRequestException;
import com.tripify.hotels.service.exception.HotelNotFoundException;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.model.HotelFacility;
import com.tripify.hotels.service.model.HotelNearbyPlace;
import com.tripify.hotels.service.model.HotelPhoto;
import com.tripify.hotels.service.model.HotelPaymentMethods;
import com.tripify.hotels.service.model.HotelRefundCondition;
import com.tripify.hotels.service.model.HotelReviewsSummary;
import com.tripify.hotels.service.model.HotelSearchFilter;
import com.tripify.hotels.service.model.HotelSearchPage;
import com.tripify.hotels.service.model.HotelTag;
import com.tripify.hotels.service.model.HotelTermsPlacement;
import com.tripify.hotels.service.model.documents.HotelReviewsDocument;
import com.tripify.hotels.service.repository.contract.HotelFacilityRepository;
import com.tripify.hotels.service.repository.contract.HotelNearbyPlaceRepository;
import com.tripify.hotels.service.repository.contract.HotelPhotoRepository;
import com.tripify.hotels.service.repository.contract.HotelPaymentMethodsRepository;
import com.tripify.hotels.service.repository.contract.HotelRefundConditionRepository;
import com.tripify.hotels.service.repository.contract.HotelRepository;
import com.tripify.hotels.service.repository.contract.HotelReviewsSummaryRepository;
import com.tripify.hotels.service.repository.contract.HotelScoreRepository;
import com.tripify.hotels.service.repository.contract.HotelTagRepository;
import com.tripify.hotels.service.repository.contract.HotelTermsPlacementRepository;
import com.tripify.hotels.service.repository.mongo.contract.HotelReviewsRepository;
import com.tripify.hotels.service.service.currency.CurrencyConversionService;
import com.tripify.hotels.service.service.filter.HotelFilterResolver;
import com.tripify.hotels.service.service.mapper.HotelReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelQueryService {

    private final HotelRepository hotelRepository;
    private final HotelFacilityRepository facilityRepository;
    private final HotelTagRepository tagRepository;
    private final HotelReviewsSummaryRepository reviewsSummaryRepository;
    private final HotelNearbyPlaceRepository nearbyPlaceRepository;
    private final HotelPhotoRepository photoRepository;
    private final HotelTermsPlacementRepository termsPlacementRepository;
    private final HotelRefundConditionRepository refundConditionRepository;
    private final HotelPaymentMethodsRepository paymentMethodsRepository;
    private final HotelScoreRepository scoreRepository;
    private final HotelReviewsRepository reviewsRepository;
    private final HotelReadMapper readMapper;
    private final CurrencyConversionService currencyConversion;
    private final HotelFilterResolver filterResolver;

    public HotelsResponse getHotels(
            String country,
            String city,
            LocalDate checkIn,
            LocalDate checkOut,
            String currency,
            Integer guests,
            Long budget,
            Integer limit,
            UUID cursor,
            List<String> filters
    ) {
        String displayCurrency = normalizeCurrency(currency);
        validateSearchParams(country, city, checkIn, checkOut, guests);

        BigDecimal maxPriceUsd = budget != null
                ? currencyConversion.toStorageCurrency(BigDecimal.valueOf(budget), displayCurrency)
                : null;

        BigDecimal lastPriceUsd = resolveCursorPrice(cursor, maxPriceUsd);

        HotelSearchFilter filter = new HotelSearchFilter(
                country,
                city,
                maxPriceUsd,
                filterResolver.resolve(filters),
                limit != null ? limit : 20,
                cursor,
                lastPriceUsd
        );

        HotelSearchPage page = hotelRepository.search(filter);

        if (page.hotels().isEmpty()) {
            return readMapper.toHotelsResponse(List.of(), null);
        }

        List<UUID> hotelIds = page.hotels().stream().map(Hotel::id).toList();

        Map<UUID, List<HotelFacility>> facilitiesByHotelId = facilityRepository.findByHotelIds(hotelIds);
        Map<UUID, List<String>> tagsByHotelId = tagRepository.findTagsByHotelIds(hotelIds);
        Map<UUID, HotelReviewsSummary> reviewsSummaryByHotelId = reviewsSummaryRepository.findByHotelIds(hotelIds);
        Map<UUID, List<HotelPhoto>> photosByHotelId = photoRepository.findByHotelIds(hotelIds);

        List<HotelCard> cards = page.hotels().stream()
                .map(hotel -> readMapper.toHotelCard(
                        hotel,
                        facilitiesByHotelId.getOrDefault(hotel.id(), List.of()),
                        tagsByHotelId.getOrDefault(hotel.id(), List.of()),
                        photosByHotelId.getOrDefault(hotel.id(), List.of()),
                        reviewsSummaryByHotelId.get(hotel.id()),
                        displayCurrency
                ))
                .toList();

        return readMapper.toHotelsResponse(cards, page.nextCursor());
    }

    public HotelDetails getHotelById(UUID hotelId, String currency) {
        String displayCurrency = normalizeCurrency(currency);

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(hotelId));

        List<HotelFacility> facilities = facilityRepository.findByHotelId(hotelId);
        List<String> tags = tagRepository.findByHotelId(hotelId).stream()
                .map(HotelTag::tag)
                .toList();
        List<HotelPhoto> photos = photoRepository.findByHotelId(hotelId);
        List<HotelNearbyPlace> nearbyPlaces = nearbyPlaceRepository.findByHotelId(hotelId);
        HotelTermsPlacement termsPlacement = termsPlacementRepository.findByHotelId(hotelId).orElse(null);
        List<HotelRefundCondition> refundConditions = refundConditionRepository.findByHotelId(hotelId);
        HotelPaymentMethods paymentMethods = paymentMethodsRepository.findByHotelId(hotelId).orElse(null);
        HotelReviewsSummary reviewsSummary = reviewsSummaryRepository.findByHotelId(hotelId).orElse(null);
        com.tripify.hotels.service.model.HotelScore hotelScore =
                scoreRepository.findByHotelId(hotelId).orElse(null);

        HotelReviewsDocument reviewsDocument = reviewsRepository.findById(hotelId.toString()).orElse(null);

        return readMapper.toHotelDetails(
                hotel,
                facilities,
                tags,
                photos,
                nearbyPlaces,
                termsPlacement,
                refundConditions,
                paymentMethods,
                reviewsSummary,
                reviewsDocument,
                hotelScore,
                displayCurrency
        );
    }

    private static void validateSearchParams(
            String country,
            String city,
            LocalDate checkIn,
            LocalDate checkOut,
            Integer guests
    ) {
        if (country == null || country.isBlank()) {
            throw new BadRequestException("country is required");
        }

        if (city == null || city.isBlank()) {
            throw new BadRequestException("city is required");
        }

        if (checkIn == null || checkOut == null) {
            throw new BadRequestException("check_in and check_out are required");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new BadRequestException("check_out must be after check_in");
        }

        if (guests == null || guests < 1) {
            throw new BadRequestException("guests must be greater than 0");
        }
    }

    private BigDecimal resolveCursorPrice(UUID cursor, BigDecimal maxPriceUsd) {
        if (cursor == null || maxPriceUsd == null) {
            return null;
        }

        return hotelRepository.findById(cursor)
                .map(Hotel::price)
                .orElseThrow(() -> new BadRequestException("cursor references unknown hotel"));
    }

    private String normalizeCurrency(String currency) {
        if (currency == null || currency.length() != 3) {
            throw new BadRequestException("currency must be a 3-letter code");
        }

        return currency.trim().toUpperCase(Locale.ROOT);
    }
}
