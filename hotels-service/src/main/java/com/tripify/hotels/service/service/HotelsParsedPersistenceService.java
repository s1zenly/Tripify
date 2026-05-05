package com.tripify.hotels.service.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.exception.HotelPersistenceException;
import com.tripify.hotels.service.kafka.model.HotelsParsedEvent;
import com.tripify.hotels.service.kafka.model.KafkaHotelDto;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.repository.contract.HotelRepository;
import com.tripify.hotels.service.service.dto.HotelPersistenceFailure;
import com.tripify.hotels.service.service.dto.HotelsPersistenceResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelsParsedPersistenceService {

    private final HotelRepository hotelRepository;
    private final HotelPostgresPersistenceService postgresPersistenceService;
    private final HotelReviewsStorageService reviewsStorageService;
    private final HotelRoomsStorageService roomsStorageService;

    public HotelsPersistenceResult persist(HotelsParsedEvent event) {
        validateEvent(event);

        List<HotelPersistenceFailure> failures = new ArrayList<>();
        int savedHotels = 0;

        for (KafkaHotelDto hotelDto : event.hotels()) {
            try {
                persistSingleHotel(hotelDto, event);
                savedHotels++;
            } catch (HotelPersistenceException exception) {
                failures.add(new HotelPersistenceFailure(
                        exception.externalHotelId(),
                        exception.getMessage()
                ));
                log.error(
                        "Failed to persist hotel. providerName={}, externalHotelId={}",
                        event.providerName(),
                        exception.externalHotelId(),
                        exception
                );
            } catch (Exception exception) {
                Long externalHotelId = hotelDto != null ? hotelDto.hid() : null;
                failures.add(new HotelPersistenceFailure(
                        externalHotelId,
                        exception.getMessage()
                ));
                log.error(
                        "Unexpected error while persisting hotel. providerName={}, externalHotelId={}",
                        event.providerName(),
                        externalHotelId,
                        exception
                );
            }
        }

        HotelsPersistenceResult result = new HotelsPersistenceResult(
                event.hotels().size(),
                savedHotels,
                failures.size(),
                List.copyOf(failures)
        );

        log.info(
                "Hotels parsed event persistence finished. providerName={}, total={}, saved={}, failed={}",
                event.providerName(),
                result.totalHotels(),
                result.savedHotels(),
                result.failedHotels()
        );

        return result;
    }

    private void persistSingleHotel(KafkaHotelDto hotelDto, HotelsParsedEvent event) {
        if (hotelDto == null || hotelDto.hid() == null) {
            throw new HotelPersistenceException(null, "Hotel payload or hid is missing");
        }

        UUID hotelId = Hotel.generateId(hotelDto.hid(), event.providerName());
        Instant now = Instant.now();

        persistReviews(hotelId, hotelDto, event, now);
        persistRooms(hotelId, hotelDto, event, now);
        postgresPersistenceService.save(hotelDto, event, hotelId, now);
    }

    private void persistReviews(UUID hotelId, KafkaHotelDto hotelDto, HotelsParsedEvent event, Instant now) {
        if (hotelDto.reviews() == null) {
            return;
        }

        try {
            reviewsStorageService.upsertReviews(hotelId, hotelDto.reviews(), now);
        } catch (Exception exception) {
            throw new HotelPersistenceException(
                    hotelDto.hid(),
                    "Failed to persist reviews in MongoDB/S3",
                    exception
            );
        }
    }

    private void persistRooms(UUID hotelId, KafkaHotelDto hotelDto, HotelsParsedEvent event, Instant now) {
        if (hotelDto.rooms() == null || hotelDto.rooms().isEmpty()) {
            return;
        }

        try {
            roomsStorageService.upsertRooms(hotelId, hotelDto.rooms(), now);
        } catch (Exception exception) {
            throw new HotelPersistenceException(
                    hotelDto.hid(),
                    "Failed to persist rooms in MongoDB",
                    exception
            );
        }
    }

    private static void validateEvent(HotelsParsedEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Hotels parsed event must not be null");
        }

        if (event.providerName() == null || event.providerName().isBlank()) {
            throw new IllegalArgumentException("Provider name must not be blank");
        }

        if (event.hotels() == null || event.hotels().isEmpty()) {
            throw new IllegalArgumentException("Hotels list must not be empty");
        }

        if (event.parsedAt() == null || event.providedAt() == null) {
            throw new IllegalArgumentException("parsedAt and providedAt must not be null");
        }
    }
}
