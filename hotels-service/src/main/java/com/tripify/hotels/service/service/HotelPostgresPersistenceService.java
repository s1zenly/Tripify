package com.tripify.hotels.service.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.kafka.model.KafkaHotelDto;
import com.tripify.hotels.service.kafka.model.HotelsParsedEvent;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.model.HotelFacility;
import com.tripify.hotels.service.model.HotelNearbyPlace;
import com.tripify.hotels.service.model.HotelPaymentMethods;
import com.tripify.hotels.service.model.HotelRefundCondition;
import com.tripify.hotels.service.model.HotelReviewsSummary;
import com.tripify.hotels.service.model.HotelSearchFacet;
import com.tripify.hotels.service.model.HotelTermsPlacement;
import com.tripify.hotels.service.repository.contract.HotelFacilityRepository;
import com.tripify.hotels.service.repository.contract.HotelNearbyPlaceRepository;
import com.tripify.hotels.service.repository.contract.HotelPaymentMethodsRepository;
import com.tripify.hotels.service.repository.contract.HotelRefundConditionRepository;
import com.tripify.hotels.service.repository.contract.HotelRepository;
import com.tripify.hotels.service.repository.contract.HotelReviewsSummaryRepository;
import com.tripify.hotels.service.repository.contract.HotelSearchFacetRepository;
import com.tripify.hotels.service.repository.contract.HotelTermsPlacementRepository;
import com.tripify.hotels.service.service.filter.HotelFilterDerivationService;
import com.tripify.hotels.service.service.mapper.HotelPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelPostgresPersistenceService {

    private final HotelRepository hotelRepository;
    private final HotelFacilityRepository facilityRepository;
    private final HotelNearbyPlaceRepository nearbyPlaceRepository;
    private final HotelTermsPlacementRepository termsPlacementRepository;
    private final HotelRefundConditionRepository refundConditionRepository;
    private final HotelPaymentMethodsRepository paymentMethodsRepository;
    private final HotelReviewsSummaryRepository reviewsSummaryRepository;
    private final HotelSearchFacetRepository searchFacetRepository;
    private final HotelFilterDerivationService filterDerivationService;
    private final HotelPersistenceMapper mapper;

    @Transactional
    public Hotel save(
            KafkaHotelDto hotelDto,
            HotelsParsedEvent event,
            UUID hotelId,
            Instant now
    ) {
        Hotel hotel = mapper.toHotel(hotelDto, event, hotelId, now);
        Hotel savedHotel = hotelRepository.upsert(hotel);

        List<HotelFacility> facilities = mapper.toFacilities(hotelId, hotelDto.facilities(), now);
        List<HotelNearbyPlace> nearbyPlaces = mapper.toNearbyPlaces(hotelId, hotelDto.nearbyPlaces(), now);
        HotelTermsPlacement termsPlacement = mapper.toTermsPlacement(hotelId, hotelDto.termsPlacement(), now);

        facilityRepository.replaceAll(hotelId, facilities);
        nearbyPlaceRepository.replaceAll(hotelId, nearbyPlaces);

        if (termsPlacement != null) {
            termsPlacementRepository.upsert(termsPlacement);
        }

        refundConditionRepository.replaceAll(
                hotelId,
                mapper.toRefundConditions(
                        hotelId,
                        hotelDto.termsPlacement() != null ? hotelDto.termsPlacement().refundRule() : null,
                        now
                )
        );

        HotelPaymentMethods paymentMethods = mapper.toPaymentMethods(hotelId, hotelDto.paymentMethods(), now);
        if (paymentMethods != null) {
            paymentMethodsRepository.upsert(paymentMethods);
        }

        HotelReviewsSummary reviewsSummary = mapper.toReviewsSummary(hotelId, hotelDto.reviews(), now);
        if (reviewsSummary != null) {
            reviewsSummaryRepository.upsert(reviewsSummary);
        }

        List<HotelSearchFacet> searchFacets = filterDerivationService.derive(
                hotelId,
                hotelDto,
                facilities,
                nearbyPlaces,
                termsPlacement,
                now
        );
        searchFacetRepository.replaceAll(hotelId, searchFacets);

        return savedHotel;
    }
}
