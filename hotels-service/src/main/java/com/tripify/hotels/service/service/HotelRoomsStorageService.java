package com.tripify.hotels.service.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.kafka.model.RoomDto;
import com.tripify.hotels.service.model.documents.HotelRoomsDocument;
import com.tripify.hotels.service.model.documents.RoomPhotoDocument;
import com.tripify.hotels.service.repository.mongo.contract.HotelRoomsRepository;
import com.tripify.hotels.service.service.mapper.RoomsDocumentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelRoomsStorageService {

    private final HotelRoomsRepository repository;
    private final RoomsDocumentMapper mapper;
    private final HotelPhotoStorageService photoStorageService;

    public void upsertRooms(UUID hotelId, List<RoomDto> rooms, Instant now) {
        if (rooms == null || rooms.isEmpty()) {
            return;
        }

        Instant createdAt = repository.findById(hotelId.toString())
                .map(HotelRoomsDocument::createdAt)
                .orElse(now);

        long start = System.currentTimeMillis();
        List<List<RoomPhotoDocument>> roomPhotos = rooms.stream()
                .map(room -> photoStorageService.uploadRoomPhotosFromUrls(
                        hotelId,
                        room.roomId(),
                        room.photos()
                ))
                .toList();

        int totalPhotos = roomPhotos.stream().mapToInt(List::size).sum();
        int totalInput = rooms.stream()
                .mapToInt(r -> r.photos() != null ? r.photos().size() : 0)
                .sum();
        log.info("Room photos processed. hotelId={}, rooms={}, uploaded={}/{}, time={}ms",
                hotelId, rooms.size(), totalPhotos, totalInput, System.currentTimeMillis() - start);

        HotelRoomsDocument document = mapper.toDocument(hotelId, rooms, roomPhotos, createdAt, now);
        repository.save(document);
    }
}
