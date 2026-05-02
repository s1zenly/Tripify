package com.tripify.hotels.service.kafka.handler;

import java.util.List;

import com.tripify.hotels.service.kafka.model.HotelsParsedEvent;
import com.tripify.hotels.service.service.HotelsParsedPersistenceService;
import com.tripify.hotels.service.service.dto.HotelsPersistenceResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelsParsedEventHandler {

    private final HotelsParsedPersistenceService persistenceService;

    public HotelsPersistenceResult handle(HotelsParsedEvent event) {
        if (event.hotels() == null || event.hotels().isEmpty()) {
            log.warn(
                    "Received empty hotels parsed event. providerName={}, country={}",
                    event.providerName(),
                    event.countryInfo()
            );
            return new HotelsPersistenceResult(0, 0, 0, List.of());
        }

        log.info(
                "Received hotels parsed event. providerName={}, country={}, totalHotels={}, actualHotelsSize={}, parsedAt={}, providedAt={}",
                event.providerName(),
                event.countryInfo() != null ? event.countryInfo().alpha2() : null,
                event.totalHotels(),
                event.hotels().size(),
                event.parsedAt(),
                event.providedAt()
        );

        return persistenceService.persist(event);
    }
}
