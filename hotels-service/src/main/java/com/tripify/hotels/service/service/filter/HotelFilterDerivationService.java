package com.tripify.hotels.service.service.filter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.tripify.hotels.service.kafka.model.FacilityDto;
import com.tripify.hotels.service.kafka.model.KafkaHotelDto;
import com.tripify.hotels.service.kafka.model.NearbyPlaceDto;
import com.tripify.hotels.service.kafka.model.RateDto;
import com.tripify.hotels.service.kafka.model.RoomDto;
import com.tripify.hotels.service.model.HotelFacility;
import com.tripify.hotels.service.model.HotelNearbyPlace;
import com.tripify.hotels.service.model.HotelSearchFacet;
import com.tripify.hotels.service.model.HotelTermsPlacement;
import com.tripify.hotels.service.model.filter.HotelFilterCatalog;
import com.tripify.hotels.service.model.filter.HotelFilterType;
import org.springframework.stereotype.Service;

@Service
public class HotelFilterDerivationService {

    private static final BigDecimal BEACHFRONT_MAX_DISTANCE_METERS = BigDecimal.valueOf(500);

    public List<HotelSearchFacet> derive(
            UUID hotelId,
            KafkaHotelDto hotelDto,
            List<HotelFacility> facilities,
            List<HotelNearbyPlace> nearbyPlaces,
            HotelTermsPlacement termsPlacement,
            Instant now
    ) {
        Set<String> facets = new LinkedHashSet<>();

        addFacilityFacets(facets, facilities, hotelDto != null ? hotelDto.facilities() : null);
        addNearbyFacets(facets, nearbyPlaces, hotelDto != null ? hotelDto.nearbyPlaces() : null);
        addTermsFacets(facets, termsPlacement, hotelDto != null ? hotelDto.termsPlacement() : null);
        addRoomFacets(facets, hotelDto != null ? hotelDto.rooms() : null);

        return facets.stream()
                .map(facet -> new HotelSearchFacet(hotelId, facet, now))
                .toList();
    }

    private static void addFacilityFacets(
            Set<String> facets,
            List<HotelFacility> facilities,
            List<FacilityDto> facilityDtos
    ) {
        if (facilities != null) {
            for (HotelFacility facility : facilities) {
                addIfCatalogFacet(facets, facility.facilityType());
            }
        }

        if (facilityDtos != null) {
            for (FacilityDto facility : facilityDtos) {
                addIfCatalogFacet(facets, facility.type());
            }
        }
    }

    private static void addIfCatalogFacet(Set<String> facets, String rawKey) {
        if (rawKey == null || rawKey.isBlank()) {
            return;
        }

        String normalized = HotelFilterCatalog.normalizeId(rawKey);
        if (HotelFilterCatalog.find(normalized)
                .filter(definition -> definition.type() == HotelFilterType.FACET)
                .isPresent()) {
            facets.add(normalized);
        }
    }

    private static void addNearbyFacets(
            Set<String> facets,
            List<HotelNearbyPlace> nearbyPlaces,
            Map<String, List<NearbyPlaceDto>> nearbyDtos
    ) {
        if (nearbyPlaces != null) {
            for (HotelNearbyPlace place : nearbyPlaces) {
                applyBeachRule(facets, place.category(), place.distanceValue(), place.distanceUnit());
            }
        }

        if (nearbyDtos != null) {
            nearbyDtos.forEach((category, places) -> {
                if (places == null) {
                    return;
                }

                for (NearbyPlaceDto place : places) {
                    applyBeachRule(facets, category, place.distance(), place.unit());
                }
            });
        }
    }

    private static void applyBeachRule(
            Set<String> facets,
            String category,
            BigDecimal distance,
            String unit
    ) {
        if (!isBeachCategory(category)) {
            return;
        }

        BigDecimal meters = toMeters(distance, unit);
        if (meters == null || meters.compareTo(BEACHFRONT_MAX_DISTANCE_METERS) <= 0) {
            facets.add(HotelFilterCatalog.BEACHFRONT);
        }
    }

    private static void addTermsFacets(
            Set<String> facets,
            HotelTermsPlacement termsPlacement,
            com.tripify.hotels.service.kafka.model.TermsPlacementDto termsDto
    ) {
        Boolean petFriendly = termsPlacement != null ? termsPlacement.petFriendly() : null;
        if (petFriendly == null && termsDto != null) {
            petFriendly = termsDto.petFriendly();
        }
        if (Boolean.TRUE.equals(petFriendly)) {
            facets.add(HotelFilterCatalog.PET_FRIENDLY);
        }
    }

    private static void addRoomFacets(Set<String> facets, List<RoomDto> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return;
        }

        for (RoomDto room : rooms) {
            if (room.rates() == null) {
                continue;
            }
            for (RateDto rate : room.rates()) {
                if (rate.cancellationPolicy() != null
                        && Boolean.TRUE.equals(rate.cancellationPolicy().refundable())) {
                    facets.add(HotelFilterCatalog.FREE_CANCELLATION);
                    return;
                }
            }
        }
    }

    private static boolean isBeachCategory(String category) {
        if (category == null) {
            return false;
        }

        String normalized = category.trim().toLowerCase(Locale.ROOT);
        return normalized.contains("beach")
                || normalized.contains("sea")
                || normalized.contains("ocean")
                || normalized.equals("coast");
    }

    private static BigDecimal toMeters(BigDecimal distance, String unit) {
        if (distance == null) {
            return null;
        }

        if (unit == null || unit.isBlank() || "m".equals(unit)) {
            return distance;
        }

        String normalizedUnit = unit.trim().toLowerCase(Locale.ROOT);
        if (normalizedUnit.startsWith("km")) {
            return distance.multiply(BigDecimal.valueOf(1000));
        }

        if (normalizedUnit.startsWith("mi")) {
            return distance.multiply(BigDecimal.valueOf(1609.34));
        }

        return distance;
    }
}
