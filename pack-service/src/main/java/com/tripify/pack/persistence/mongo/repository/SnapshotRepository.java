package com.tripify.pack.persistence.mongo.repository;

import com.tripify.pack.domain.PackAspectEvent;
import com.tripify.pack.domain.PackSnapshot;
import com.tripify.pack.persistence.mongo.document.HotelSnapshotDocument;
import com.tripify.pack.persistence.mongo.document.PackSnapshotDocument;
import com.tripify.pack.persistence.mongo.document.TicketSnapshotDocument;
import com.tripify.pack.persistence.mongo.mapper.SnapshotDocumentMapper;
import com.tripify.pack.service.model.PackSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SnapshotRepository {

    private final HotelSnapshotRepository hotelSnapshotRepository;
    private final TicketSnapshotRepository ticketSnapshotRepository;
    private final PackSnapshotRepository packSnapshotRepository;
    private final PackSnapshotQueryRepository packSnapshotQueryRepository;

    public HotelSnapshotDocument saveHotelSnapshot(PackAspectEvent event, Instant createdAt) {
        return hotelSnapshotRepository.save(SnapshotDocumentMapper.toHotelSnapshotDocument(event, createdAt));
    }

    public TicketSnapshotDocument saveTicketSnapshot(PackAspectEvent event, Instant createdAt) {
        return ticketSnapshotRepository.save(SnapshotDocumentMapper.toTicketSnapshotDocument(event, createdAt));
    }

    public PackSnapshotDocument savePackSnapshot(PackSnapshot snapshot, Instant createdAt) {
        return packSnapshotRepository.save(SnapshotDocumentMapper.toPackSnapshotDocument(snapshot, createdAt));
    }

    public Optional<HotelSnapshotDocument> findHotelSnapshotById(String id) {
        return hotelSnapshotRepository.findById(id);
    }

    public Optional<TicketSnapshotDocument> findTicketSnapshotById(String id) {
        return ticketSnapshotRepository.findById(id);
    }

    public Optional<PackSnapshotDocument> findPackSnapshotById(String id) {
        return packSnapshotRepository.findById(id);
    }

    public List<PackSnapshotDocument> findPackSnapshotsBySearchCriteria(PackSearchCriteria criteria) {
        return packSnapshotQueryRepository.findBySearchCriteria(criteria);
    }
}
