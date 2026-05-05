package com.tripify.hotels.service.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.kafka.model.KafkaHotelDto;
import com.tripify.hotels.service.kafka.model.HotelsParsedEvent;
import com.tripify.hotels.service.kafka.model.PhotoDto;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.model.HotelFacility;
import com.tripify.hotels.service.model.HotelNearbyPlace;
import com.tripify.hotels.service.model.HotelPhoto;
import com.tripify.hotels.service.model.HotelReviewsSummary;
import com.tripify.hotels.service.model.HotelSearchFacet;
import com.tripify.hotels.service.model.HotelTermsPlacement;
import com.tripify.hotels.service.repository.contract.HotelFacilityRepository;
import com.tripify.hotels.service.repository.contract.HotelNearbyPlaceRepository;
import com.tripify.hotels.service.repository.contract.HotelPhotoRepository;
import com.tripify.hotels.service.repository.contract.HotelRepository;
import com.tripify.hotels.service.repository.contract.HotelReviewsSummaryRepository;
import com.tripify.hotels.service.repository.contract.HotelSearchFacetRepository;
import com.tripify.hotels.service.repository.contract.HotelTermsPlacementRepository;
import com.tripify.hotels.service.service.filter.HotelFilterDerivationService;
import com.tripify.hotels.service.service.mapper.HotelPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelPostgresPersistenceService {

    private final HotelRepository hotelRepository;
    private final HotelFacilityRepository facilityRepository;
    private final HotelNearbyPlaceRepository nearbyPlaceRepository;
    private final HotelTermsPlacementRepository termsPlacementRepository;
    private final HotelReviewsSummaryRepository reviewsSummaryRepository;
    private final HotelSearchFacetRepository searchFacetRepository;
    private final HotelPhotoRepository photoRepository;
    private final HotelPhotoStorageService photoStorageService;
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

        long photosStart = System.currentTimeMillis();
        List<HotelPhoto> photos = uploadAndMapPhotos(hotelId, hotelDto.photos(), now);
        photoRepository.replaceAll(hotelId, photos);
        log.info("Hotel photos processed. hotelId={}, uploaded={}/{}, time={}ms",
                hotelId, photos.size(),
                hotelDto.photos() != null ? hotelDto.photos().size() : 0,
                System.currentTimeMillis() - photosStart);

        if (termsPlacement != null) {
            termsPlacementRepository.upsert(termsPlacement);
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

    private List<HotelPhoto> uploadAndMapPhotos(UUID hotelId, List<PhotoDto> photos, Instant now) {
        if (photos == null || photos.isEmpty()) {
            return List.of();
        }

        List<HotelPhoto> result = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            PhotoDto photo = photos.get(i);
            if (photo.link() == null || photo.link().isBlank()) {
                continue;
            }
            int order = photo.order() != null ? photo.order() : i;
            photoStorageService.uploadHotelPhotoFromUrl(hotelId, photo.link())
                    .ifPresent(s3Key -> result.add(
                            new HotelPhoto(UUID.randomUUID(), hotelId, s3Key, order, null, now)
                    ));
        }
        return result;
    }
}
