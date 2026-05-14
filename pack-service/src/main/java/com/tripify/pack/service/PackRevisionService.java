package com.tripify.pack.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.pack.config.property.PackRevisionProperties;
import com.tripify.pack.domain.AspectType;
import com.tripify.pack.domain.FailureReason;
import com.tripify.pack.domain.GenerationMode;
import com.tripify.pack.domain.PackAspectEvent;
import com.tripify.pack.domain.PackRevisionStatus;
import com.tripify.pack.domain.PackSnapshot;
import com.tripify.pack.domain.SearchContext;
import com.tripify.pack.persistence.mongo.document.HotelSnapshotDocument;
import com.tripify.pack.persistence.mongo.document.TicketSnapshotDocument;
import com.tripify.pack.persistence.mongo.mapper.SnapshotDocumentMapper;
import com.tripify.pack.persistence.mongo.repository.SnapshotRepository;
import com.tripify.pack.persistence.postgres.model.KafkaProcessedEventRecord;
import com.tripify.pack.persistence.postgres.model.PackRevisionRecord;
import com.tripify.pack.persistence.postgres.repository.contract.KafkaProcessedEventRepository;
import com.tripify.pack.persistence.postgres.repository.contract.PackRevisionRepository;
import com.tripify.pack.service.exception.InvalidPackEventException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackRevisionService {

    private final PackRevisionRepository packRevisionRepository;
    private final KafkaProcessedEventRepository kafkaProcessedEventRepository;
    private final SnapshotRepository snapshotRepository;
    private final ObjectMapper objectMapper;
    private final PackRevisionProperties packRevisionProperties;

    @Transactional
    public void processAspectEvent(PackAspectEvent event) {
        validateEvent(event);

        var kafkaMetadata = event.kafkaMetadata();
        if (kafkaProcessedEventRepository.existsByTopicPartitionOffset(
                kafkaMetadata.topic(),
                kafkaMetadata.kafkaPartition(),
                kafkaMetadata.kafkaOffset()
        )) {
            log.debug(
                    "Skipping already processed event: topic={}, partition={}, offset={}",
                    kafkaMetadata.topic(),
                    kafkaMetadata.kafkaPartition(),
                    kafkaMetadata.kafkaOffset()
            );
            return;
        }

        Instant now = Instant.now();
        String hotelSnapshotId = null;
        String ticketSnapshotId = null;
        if (event.aspectType() == AspectType.HOTEL) {
            hotelSnapshotId = snapshotRepository.saveHotelSnapshot(event, now).id();
        } else {
            ticketSnapshotId = snapshotRepository.saveTicketSnapshot(event, now).id();
        }

        PackRevisionRecord revision = switch (event.generationMode()) {
            case FULL -> processFullEvent(event, hotelSnapshotId, ticketSnapshotId, now);
            case HOTELS -> processHotelsEvent(event, hotelSnapshotId, now);
            case TICKETS -> processTicketsEvent(event, ticketSnapshotId, now);
        };

        if (revision.status() == PackRevisionStatus.COMPLETED) {
            tryCompleteWaitingRevisions(event.subjectId(), event.generationId());
        }

        kafkaProcessedEventRepository.insert(new KafkaProcessedEventRecord(
                UUID.randomUUID(),
                kafkaMetadata.topic(),
                kafkaMetadata.kafkaPartition(),
                kafkaMetadata.kafkaOffset(),
                event.eventId(),
                event.subjectId(),
                event.generationId(),
                event.packRevisionId(),
                now
        ));
    }

    public PackRevisionRecord processFullEvent(
            PackAspectEvent event,
            String hotelSnapshotId,
            String ticketSnapshotId,
            Instant now
    ) {
        validateAspectForMode(event, GenerationMode.FULL);

        Optional<PackRevisionRecord> existing = packRevisionRepository.findByKey(
                event.subjectId(),
                event.generationId(),
                event.packRevisionId()
        );
        PackRevisionRecord revision = existing
                .map(record -> validateExistingRevision(record, event))
                .orElseGet(() -> newRevision(event, PackRevisionStatus.WAITING_ASPECTS, now));

        revision = applyAspectUpdate(revision, event, hotelSnapshotId, ticketSnapshotId, now);
        revision = persistRevision(existing, revision);

        return completeRevisionIfPossible(revision, now);
    }

    public PackRevisionRecord processHotelsEvent(
            PackAspectEvent event,
            String hotelSnapshotId,
            Instant now
    ) {
        validateAspectForMode(event, GenerationMode.HOTELS);

        Optional<PackRevisionRecord> existing = packRevisionRepository.findByKey(
                event.subjectId(),
                event.generationId(),
                event.packRevisionId()
        );
        PackRevisionRecord revision = existing
                .map(record -> validateExistingRevision(record, event))
                .orElseGet(() -> newRevision(event, PackRevisionStatus.WAITING_PREVIOUS_PACK, now));

        revision = applyAspectUpdate(revision, event, hotelSnapshotId, null, now);
        revision = persistRevision(existing, revision);

        Optional<PackRevisionRecord> previousCompleted = packRevisionRepository.findPreviousCompleted(
                event.subjectId(),
                event.generationId(),
                event.packRevisionId()
        );
        if (previousCompleted.isEmpty()) {
            return markWaitingPreviousPack(revision, now);
        }

        return completeHotelsRevision(revision, previousCompleted.get(), event.searchContext(), now);
    }

    public PackRevisionRecord processTicketsEvent(
            PackAspectEvent event,
            String ticketSnapshotId,
            Instant now
    ) {
        validateAspectForMode(event, GenerationMode.TICKETS);

        Optional<PackRevisionRecord> existing = packRevisionRepository.findByKey(
                event.subjectId(),
                event.generationId(),
                event.packRevisionId()
        );
        PackRevisionRecord revision = existing
                .map(record -> validateExistingRevision(record, event))
                .orElseGet(() -> newRevision(event, PackRevisionStatus.WAITING_PREVIOUS_PACK, now));

        revision = applyAspectUpdate(revision, event, null, ticketSnapshotId, now);
        revision = persistRevision(existing, revision);

        Optional<PackRevisionRecord> previousCompleted = packRevisionRepository.findPreviousCompleted(
                event.subjectId(),
                event.generationId(),
                event.packRevisionId()
        );
        if (previousCompleted.isEmpty()) {
            return markWaitingPreviousPack(revision, now);
        }

        return completeTicketsRevision(revision, previousCompleted.get(), event.searchContext(), now);
    }

    public PackRevisionRecord completeRevisionIfPossible(PackRevisionRecord revision, Instant now) {
        if (revision.generationMode() != GenerationMode.FULL) {
            return revision;
        }
        if (revision.hotelSnapshotId() == null || revision.ticketSnapshotId() == null) {
            return markWaitingAspects(revision, now);
        }

        SearchContext searchContext = loadSearchContextFromAspectSnapshots(revision);
        return completeFullRevision(revision, searchContext, now);
    }

    public void tryCompleteWaitingRevisions(String subjectId, String generationId) {
        while (true) {
            Optional<PackRevisionRecord> previousCompleted =
                    packRevisionRepository.findLatestCompleted(subjectId, generationId);
            if (previousCompleted.isEmpty()) {
                return;
            }

            Optional<PackRevisionRecord> nextWaiting = packRevisionRepository.findNextWaitingPreviousPack(
                    subjectId,
                    generationId,
                    previousCompleted.get().packRevisionId()
            );
            if (nextWaiting.isEmpty()) {
                return;
            }

            if (!tryCompleteWaitingRevision(nextWaiting.get(), previousCompleted.get())) {
                return;
            }
        }
    }

    @Transactional
    public int failExpiredWaitingRevisions() {
        Instant updatedBefore = Instant.now().minus(packRevisionProperties.waitingTimeout());
        var expiredRevisions = packRevisionRepository.findExpiredWaiting(
                updatedBefore,
                PackRevisionStatus.WAITING_ASPECTS,
                PackRevisionStatus.WAITING_PREVIOUS_PACK
        );

        Instant now = Instant.now();
        int failedCount = 0;
        for (PackRevisionRecord revision : expiredRevisions) {
            FailureReason failureReason = revision.status() == PackRevisionStatus.WAITING_ASPECTS
                    ? FailureReason.SECOND_ASPECT_NOT_RECEIVED
                    : FailureReason.PREVIOUS_PACK_NOT_FOUND;
            packRevisionRepository.update(toFailedRevision(revision, failureReason, now));
            failedCount++;
        }
        return failedCount;
    }

    private boolean tryCompleteWaitingRevision(
            PackRevisionRecord waitingRevision,
            PackRevisionRecord previousCompleted
    ) {
        Instant now = Instant.now();
        PackRevisionRecord completedRevision = switch (waitingRevision.generationMode()) {
            case HOTELS -> completeHotelsRevision(
                    waitingRevision,
                    previousCompleted,
                    loadHotelsSearchContext(waitingRevision),
                    now
            );
            case TICKETS -> completeTicketsRevision(
                    waitingRevision,
                    previousCompleted,
                    loadTicketsSearchContext(waitingRevision),
                    now
            );
            case FULL -> waitingRevision;
        };
        return completedRevision.status() == PackRevisionStatus.COMPLETED;
    }

    private PackRevisionRecord completeFullRevision(
            PackRevisionRecord revision,
            SearchContext searchContext,
            Instant now
    ) {
        JsonNode hotelPayload = loadHotelPayload(revision.hotelSnapshotId());
        JsonNode ticketPayload = loadTicketPayload(revision.ticketSnapshotId());
        return saveCompletedPack(
                revision,
                revision.hotelRevisionId(),
                revision.ticketRevisionId(),
                revision.hotelSnapshotId(),
                revision.ticketSnapshotId(),
                hotelPayload,
                ticketPayload,
                searchContext,
                now
        );
    }

    private PackRevisionRecord completeHotelsRevision(
            PackRevisionRecord revision,
            PackRevisionRecord previousCompleted,
            SearchContext searchContext,
            Instant now
    ) {
        if (revision.hotelSnapshotId() == null) {
            throw new IllegalStateException("Hotel snapshot is missing for revision id=" + revision.id());
        }
        if (previousCompleted.ticketSnapshotId() == null || previousCompleted.ticketRevisionId() == null) {
            return failRevision(revision, FailureReason.SNAPSHOT_NOT_FOUND, now);
        }

        JsonNode hotelPayload = loadHotelPayload(revision.hotelSnapshotId());
        JsonNode ticketPayload = loadTicketPayload(previousCompleted.ticketSnapshotId());
        return saveCompletedPack(
                revision,
                revision.hotelRevisionId(),
                previousCompleted.ticketRevisionId(),
                revision.hotelSnapshotId(),
                previousCompleted.ticketSnapshotId(),
                hotelPayload,
                ticketPayload,
                searchContext,
                now
        );
    }

    private PackRevisionRecord completeTicketsRevision(
            PackRevisionRecord revision,
            PackRevisionRecord previousCompleted,
            SearchContext searchContext,
            Instant now
    ) {
        if (revision.ticketSnapshotId() == null) {
            throw new IllegalStateException("Ticket snapshot is missing for revision id=" + revision.id());
        }
        if (previousCompleted.hotelSnapshotId() == null || previousCompleted.hotelRevisionId() == null) {
            return failRevision(revision, FailureReason.SNAPSHOT_NOT_FOUND, now);
        }

        JsonNode hotelPayload = loadHotelPayload(previousCompleted.hotelSnapshotId());
        JsonNode ticketPayload = loadTicketPayload(revision.ticketSnapshotId());
        return saveCompletedPack(
                revision,
                previousCompleted.hotelRevisionId(),
                revision.ticketRevisionId(),
                previousCompleted.hotelSnapshotId(),
                revision.ticketSnapshotId(),
                hotelPayload,
                ticketPayload,
                searchContext,
                now
        );
    }

    private PackRevisionRecord saveCompletedPack(
            PackRevisionRecord revision,
            Integer hotelRevisionId,
            Integer ticketRevisionId,
            String hotelSnapshotId,
            String ticketSnapshotId,
            JsonNode hotelPayload,
            JsonNode ticketPayload,
            SearchContext searchContext,
            Instant now
    ) {
        PackSnapshot packSnapshot = new PackSnapshot(
                revision.userType(),
                revision.userId(),
                revision.anonymousId(),
                revision.generationId(),
                revision.packRevisionId(),
                revision.generationMode(),
                hotelRevisionId,
                ticketRevisionId,
                hotelPayload,
                ticketPayload,
                searchContext
        );

        String packSnapshotId = snapshotRepository.savePackSnapshot(packSnapshot, now).id();
        PackRevisionRecord completedRevision = new PackRevisionRecord(
                revision.id(),
                revision.userType(),
                revision.subjectId(),
                revision.userId(),
                revision.anonymousId(),
                revision.generationId(),
                revision.packRevisionId(),
                revision.generationMode(),
                PackRevisionStatus.COMPLETED,
                hotelRevisionId,
                ticketRevisionId,
                hotelSnapshotId,
                ticketSnapshotId,
                packSnapshotId,
                null,
                revision.createdAt(),
                now
        );
        return packRevisionRepository.update(completedRevision);
    }

    private SearchContext loadSearchContextFromAspectSnapshots(PackRevisionRecord revision) {
        if (revision.hotelSnapshotId() != null) {
            return loadHotelsSearchContext(revision);
        }
        if (revision.ticketSnapshotId() != null) {
            return loadTicketsSearchContext(revision);
        }
        throw new IllegalStateException("Aspect snapshots are missing for revision id=" + revision.id());
    }

    private SearchContext loadHotelsSearchContext(PackRevisionRecord revision) {
        HotelSnapshotDocument hotelSnapshot = snapshotRepository.findHotelSnapshotById(revision.hotelSnapshotId())
                .orElseThrow(() -> new IllegalStateException(
                        "Hotel snapshot not found: id=" + revision.hotelSnapshotId()
                ));
        return SnapshotDocumentMapper.toSearchContext(hotelSnapshot.searchContext());
    }

    private SearchContext loadTicketsSearchContext(PackRevisionRecord revision) {
        TicketSnapshotDocument ticketSnapshot = snapshotRepository.findTicketSnapshotById(revision.ticketSnapshotId())
                .orElseThrow(() -> new IllegalStateException(
                        "Ticket snapshot not found: id=" + revision.ticketSnapshotId()
                ));
        return SnapshotDocumentMapper.toSearchContext(ticketSnapshot.searchContext());
    }

    private JsonNode loadHotelPayload(String snapshotId) {
        HotelSnapshotDocument hotelSnapshot = snapshotRepository.findHotelSnapshotById(snapshotId)
                .orElseThrow(() -> new IllegalStateException("Hotel snapshot not found: id=" + snapshotId));
        return documentToJsonNode(hotelSnapshot.payload());
    }

    private JsonNode loadTicketPayload(String snapshotId) {
        TicketSnapshotDocument ticketSnapshot = snapshotRepository.findTicketSnapshotById(snapshotId)
                .orElseThrow(() -> new IllegalStateException("Ticket snapshot not found: id=" + snapshotId));
        return documentToJsonNode(ticketSnapshot.payload());
    }

    private JsonNode documentToJsonNode(Document document) {
        try {
            return objectMapper.readTree(document.toJson());
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Failed to read snapshot payload", exception);
        }
    }

    private PackRevisionRecord applyAspectUpdate(
            PackRevisionRecord revision,
            PackAspectEvent event,
            String hotelSnapshotId,
            String ticketSnapshotId,
            Instant now
    ) {
        if (event.aspectType() == AspectType.HOTEL) {
            return new PackRevisionRecord(
                    revision.id(),
                    revision.userType(),
                    revision.subjectId(),
                    revision.userId(),
                    revision.anonymousId(),
                    revision.generationId(),
                    revision.packRevisionId(),
                    revision.generationMode(),
                    revision.status(),
                    event.aspectRevisionId(),
                    revision.ticketRevisionId(),
                    hotelSnapshotId,
                    revision.ticketSnapshotId(),
                    revision.packSnapshotId(),
                    revision.failureReason(),
                    revision.createdAt(),
                    now
            );
        }

        return new PackRevisionRecord(
                revision.id(),
                revision.userType(),
                revision.subjectId(),
                revision.userId(),
                revision.anonymousId(),
                revision.generationId(),
                revision.packRevisionId(),
                revision.generationMode(),
                revision.status(),
                revision.hotelRevisionId(),
                event.aspectRevisionId(),
                revision.hotelSnapshotId(),
                ticketSnapshotId,
                revision.packSnapshotId(),
                revision.failureReason(),
                revision.createdAt(),
                now
        );
    }

    private PackRevisionRecord persistRevision(Optional<PackRevisionRecord> existing, PackRevisionRecord revision) {
        return existing.isPresent()
                ? packRevisionRepository.update(revision)
                : packRevisionRepository.insert(revision);
    }

    private PackRevisionRecord markWaitingAspects(PackRevisionRecord revision, Instant now) {
        if (revision.status() == PackRevisionStatus.WAITING_ASPECTS) {
            return revision;
        }
        return packRevisionRepository.update(new PackRevisionRecord(
                revision.id(),
                revision.userType(),
                revision.subjectId(),
                revision.userId(),
                revision.anonymousId(),
                revision.generationId(),
                revision.packRevisionId(),
                revision.generationMode(),
                PackRevisionStatus.WAITING_ASPECTS,
                revision.hotelRevisionId(),
                revision.ticketRevisionId(),
                revision.hotelSnapshotId(),
                revision.ticketSnapshotId(),
                revision.packSnapshotId(),
                null,
                revision.createdAt(),
                now
        ));
    }

    private PackRevisionRecord markWaitingPreviousPack(PackRevisionRecord revision, Instant now) {
        if (revision.status() == PackRevisionStatus.WAITING_PREVIOUS_PACK) {
            return revision;
        }
        return packRevisionRepository.update(new PackRevisionRecord(
                revision.id(),
                revision.userType(),
                revision.subjectId(),
                revision.userId(),
                revision.anonymousId(),
                revision.generationId(),
                revision.packRevisionId(),
                revision.generationMode(),
                PackRevisionStatus.WAITING_PREVIOUS_PACK,
                revision.hotelRevisionId(),
                revision.ticketRevisionId(),
                revision.hotelSnapshotId(),
                revision.ticketSnapshotId(),
                revision.packSnapshotId(),
                null,
                revision.createdAt(),
                now
        ));
    }

    private PackRevisionRecord failRevision(
            PackRevisionRecord revision,
            FailureReason failureReason,
            Instant now
    ) {
        return packRevisionRepository.update(toFailedRevision(revision, failureReason, now));
    }

    private PackRevisionRecord toFailedRevision(
            PackRevisionRecord revision,
            FailureReason failureReason,
            Instant now
    ) {
        return new PackRevisionRecord(
                revision.id(),
                revision.userType(),
                revision.subjectId(),
                revision.userId(),
                revision.anonymousId(),
                revision.generationId(),
                revision.packRevisionId(),
                revision.generationMode(),
                PackRevisionStatus.FAILED,
                revision.hotelRevisionId(),
                revision.ticketRevisionId(),
                revision.hotelSnapshotId(),
                revision.ticketSnapshotId(),
                revision.packSnapshotId(),
                failureReason,
                revision.createdAt(),
                now
        );
    }

    private PackRevisionRecord newRevision(
            PackAspectEvent event,
            PackRevisionStatus status,
            Instant now
    ) {
        return new PackRevisionRecord(
                UUID.randomUUID(),
                event.userType(),
                event.subjectId(),
                event.userId(),
                event.anonymousId(),
                event.generationId(),
                event.packRevisionId(),
                event.generationMode(),
                status,
                null,
                null,
                null,
                null,
                null,
                null,
                now,
                now
        );
    }

    private PackRevisionRecord validateExistingRevision(
            PackRevisionRecord existing,
            PackAspectEvent event
    ) {
        if (existing.status() == PackRevisionStatus.COMPLETED) {
            throw new InvalidPackEventException(
                    "Pack revision already completed: subjectId=%s, generationId=%s, packRevisionId=%d"
                            .formatted(event.subjectId(), event.generationId(), event.packRevisionId())
            );
        }
        if (existing.status() == PackRevisionStatus.FAILED) {
            throw new InvalidPackEventException(
                    "Pack revision already failed: subjectId=%s, generationId=%s, packRevisionId=%d"
                            .formatted(event.subjectId(), event.generationId(), event.packRevisionId())
            );
        }
        if (existing.generationMode() != event.generationMode()) {
            throw new InvalidPackEventException(
                    "generation_mode mismatch for pack revision: expected=%s, actual=%s"
                            .formatted(existing.generationMode(), event.generationMode())
            );
        }
        return existing;
    }

    private void validateEvent(PackAspectEvent event) {
        if (event.generationId() == null || event.generationId().isBlank()) {
            throw new InvalidPackEventException("generation_id is required");
        }
        if (event.searchContext() == null) {
            throw new InvalidPackEventException("search_context is required");
        }
        event.subjectId();
    }

    private void validateAspectForMode(PackAspectEvent event, GenerationMode expectedMode) {
        if (event.generationMode() != expectedMode) {
            throw new InvalidPackEventException(
                    "Unexpected generation_mode=%s for handler expecting %s"
                            .formatted(event.generationMode(), expectedMode)
            );
        }
        if (expectedMode == GenerationMode.HOTELS && event.aspectType() != AspectType.HOTEL) {
            throw new InvalidPackEventException("HOTELS events must contain hotel aspect");
        }
        if (expectedMode == GenerationMode.TICKETS && event.aspectType() != AspectType.TICKET) {
            throw new InvalidPackEventException("TICKETS events must contain ticket aspect");
        }
    }
}
