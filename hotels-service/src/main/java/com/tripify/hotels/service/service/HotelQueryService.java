package com.tripify.hotels.service.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import com.tripify.hotels.generated.model.HotelCard;
import com.tripify.hotels.generated.model.HotelDetails;
import com.tripify.hotels.generated.model.HotelsResponse;
import com.tripify.hotels.service.domain.City;
import com.tripify.hotels.service.domain.Country;
import com.tripify.hotels.service.exception.BadRequestException;
import com.tripify.hotels.service.exception.HotelNotFoundException;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.model.HotelFacility;
import com.tripify.hotels.service.model.HotelNearbyPlace;
import com.tripify.hotels.service.model.HotelPhoto;
import com.tripify.hotels.service.model.HotelReviewsSummary;
import com.tripify.hotels.service.model.HotelSearchFilter;
import com.tripify.hotels.service.model.HotelSearchPage;
import com.tripify.hotels.service.model.HotelTag;
import com.tripify.hotels.service.model.HotelTermsPlacement;
import com.tripify.hotels.service.model.documents.HotelReviewsDocument;
import com.tripify.hotels.service.model.documents.HotelRoomsDocument;
import com.tripify.hotels.service.repository.contract.HotelFacilityRepository;
import com.tripify.hotels.service.repository.contract.HotelNearbyPlaceRepository;
import com.tripify.hotels.service.repository.contract.HotelPhotoRepository;
import com.tripify.hotels.service.repository.contract.HotelRepository;
import com.tripify.hotels.service.repository.contract.HotelReviewsSummaryRepository;
import com.tripify.hotels.service.repository.contract.HotelScoreRepository;
import com.tripify.hotels.service.repository.contract.HotelTagRepository;
import com.tripify.hotels.service.repository.contract.HotelTermsPlacementRepository;
import com.tripify.hotels.service.repository.mongo.contract.HotelReviewsRepository;
import com.tripify.hotels.service.repository.mongo.contract.HotelRoomsRepository;
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
    private final HotelScoreRepository scoreRepository;
    private final HotelReviewsRepository reviewsRepository;
    private final HotelRoomsRepository roomsRepository;
    private final HotelReadMapper readMapper;
    private final CurrencyConversionService currencyConversion;
    private final HotelFilterResolver filterResolver;

    public HotelsResponse getHotels(
            String country,
            String city,
            LocalDate checkIn,
            LocalDate checkOut,
            String currency,
            Integer adults,
            Integer children,
            Long budget,
            Integer limit,
            UUID cursor,
            List<String> filters
    ) {
        String displayCurrency = normalizeCurrency(currency);
        validateSearchParams(country, city, checkIn, checkOut, adults, children);

        Country destinationCountry = Country.fromAlpha2(country);
        City destinationCity = City.fromCode(city);

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        int minGuests = adults + (children != null ? children : 0);

        BigDecimal maxPricePerNightUsd = null;
        if (budget != null) {
            BigDecimal budgetUsd = currencyConversion.toStorageCurrency(BigDecimal.valueOf(budget), displayCurrency);
            maxPricePerNightUsd = budgetUsd.divide(BigDecimal.valueOf(nights), 2, RoundingMode.CEILING);
        }

        HotelSearchFilter filter = new HotelSearchFilter(
                destinationCountry.alpha2(),
                destinationCity.iataCode(),
                maxPricePerNightUsd,
                minGuests,
                nights,
                filterResolver.resolve(filters),
                limit != null ? limit : 20,
                cursor,
                cursor == null
        );

        return toHotelsResponse(filter, displayCurrency, nights);
    }

    public HotelDetails searchFirstHotelDetail(
            String country,
            String city,
            LocalDate checkIn,
            LocalDate checkOut,
            String currency,
            Integer adults,
            Integer children,
            Long budget,
            List<String> filters
    ) {
        String displayCurrency = normalizeCurrency(currency);
        validateSearchParams(country, city, checkIn, checkOut, adults, children);

        Country destinationCountry = Country.fromAlpha2(country);
        City destinationCity = City.fromCode(city);

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        int minGuests = adults + (children != null ? children : 0);

        BigDecimal maxPricePerNightUsd = null;
        if (budget != null) {
            BigDecimal budgetUsd = currencyConversion.toStorageCurrency(BigDecimal.valueOf(budget), displayCurrency);
            maxPricePerNightUsd = budgetUsd.divide(BigDecimal.valueOf(nights), 2, RoundingMode.CEILING);
        }

        HotelSearchFilter filter = new HotelSearchFilter(
                destinationCountry.alpha2(),
                destinationCity.iataCode(),
                maxPricePerNightUsd,
                minGuests,
                nights,
                filterResolver.resolve(filters),
                1,
                null,
                false
        );

        HotelsResponse searchResult = toHotelsResponse(filter, displayCurrency, nights);

        if (searchResult.getHotels() == null || searchResult.getHotels().isEmpty()) {
            throw HotelNotFoundException.forEmptySearch();
        }

        UUID hotelId = searchResult.getHotels().getFirst().getHotelId();
        return getHotelById(hotelId, currency, checkIn, checkOut);
    }

    private HotelsResponse toHotelsResponse(HotelSearchFilter filter, String displayCurrency, long nights) {
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
                        displayCurrency,
                        nights
                ))
                .toList();

        return readMapper.toHotelsResponse(cards, page.nextCursor());
    }

    public HotelDetails getHotelById(UUID hotelId, String currency, LocalDate checkIn, LocalDate checkOut) {
        String displayCurrency = normalizeCurrency(currency);

        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new BadRequestException("valid date_from and date_to are required");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(hotelId));

        List<HotelFacility> facilities = facilityRepository.findByHotelId(hotelId);
        List<String> tags = tagRepository.findByHotelId(hotelId).stream()
                .map(HotelTag::tag)
                .toList();
        List<HotelPhoto> photos = photoRepository.findByHotelId(hotelId);
        List<HotelNearbyPlace> nearbyPlaces = nearbyPlaceRepository.findByHotelId(hotelId);
        HotelTermsPlacement termsPlacement = termsPlacementRepository.findByHotelId(hotelId).orElse(null);
        HotelReviewsSummary reviewsSummary = reviewsSummaryRepository.findByHotelId(hotelId).orElse(null);
        com.tripify.hotels.service.model.HotelScore hotelScore =
                scoreRepository.findByHotelId(hotelId).orElse(null);

        HotelReviewsDocument reviewsDocument = reviewsRepository.findById(hotelId.toString()).orElse(null);
        HotelRoomsDocument roomsDocument = roomsRepository.findById(hotelId.toString()).orElse(null);

        return readMapper.toHotelDetails(
                hotel,
                facilities,
                tags,
                photos,
                nearbyPlaces,
                termsPlacement,
                reviewsSummary,
                reviewsDocument,
                roomsDocument,
                hotelScore,
                displayCurrency,
                nights
        );
    }

    private static void validateSearchParams(
            String country,
            String city,
            LocalDate checkIn,
            LocalDate checkOut,
            Integer adults,
            Integer children
    ) {
        if (country == null || country.isBlank()) {
            throw new BadRequestException("destination_country is required");
        }

        if (city == null || city.isBlank()) {
            throw new BadRequestException("destination_city is required");
        }

        if (checkIn == null || checkOut == null) {
            throw new BadRequestException("date_from and date_to are required");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new BadRequestException("date_to must be after date_from");
        }

        if (adults == null || adults < 1) {
            throw new BadRequestException("adults must be greater than 0");
        }

        if (children != null && children < 0) {
            throw new BadRequestException("children must be greater than or equal to 0");
        }
    }

    private String normalizeCurrency(String currency) {
        if (currency == null || currency.length() != 3) {
            throw new BadRequestException("currency must be a 3-letter code");
        }

        return currency.trim().toUpperCase(Locale.ROOT);
    }
}
