package com.tripify.pack.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.pack.domain.FailureReason;
import com.tripify.pack.domain.KafkaEventMetadata;
import com.tripify.pack.domain.PackAspectEvent;
import com.tripify.pack.domain.PackRevisionStatus;
import com.tripify.pack.persistence.mongo.document.PackSnapshotDocument;
import com.tripify.pack.persistence.mongo.repository.HotelSnapshotRepository;
import com.tripify.pack.persistence.mongo.repository.PackSnapshotRepository;
import com.tripify.pack.persistence.mongo.repository.TicketSnapshotRepository;
import com.tripify.pack.persistence.postgres.model.PackRevisionRecord;
import com.tripify.pack.persistence.postgres.repository.contract.KafkaProcessedEventRepository;
import com.tripify.pack.persistence.postgres.repository.contract.PackRevisionRepository;
import com.tripify.pack.support.PackAspectEventFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class PackRevisionServiceIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("pack_db")
            .withUsername("pack_user")
            .withPassword("pack_password");

    @Container
    @ServiceConnection
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0");

    @Autowired
    private PackRevisionService packRevisionService;

    @Autowired
    private PackRevisionRepository packRevisionRepository;

    @Autowired
    private KafkaProcessedEventRepository kafkaProcessedEventRepository;

    @Autowired
    private PackSnapshotRepository packSnapshotRepository;

    @Autowired
    private HotelSnapshotRepository hotelSnapshotRepository;

    @Autowired
    private TicketSnapshotRepository ticketSnapshotRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String subjectId;
    private String generationId;

    @BeforeEach
    void setUp() {
        subjectId = "anon-" + UUID.randomUUID();
        generationId = "gen-" + UUID.randomUUID();
        jdbcTemplate.update("delete from kafka_processed_events");
        jdbcTemplate.update("delete from pack_revisions");
        hotelSnapshotRepository.deleteAll();
        ticketSnapshotRepository.deleteAll();
        packSnapshotRepository.deleteAll();
    }

    @Test
    void fullHotelFirstThenTicketCompletesRevision() {
        packRevisionService.processAspectEvent(hotelFull(1, 1, "H1"));
        assertRevision(1, PackRevisionStatus.WAITING_ASPECTS, 1, null);

        packRevisionService.processAspectEvent(ticketFull(1, 1, "T1"));
        PackRevisionRecord revision = assertRevision(1, PackRevisionStatus.COMPLETED, 1, 1);
        assertPackSnapshot(revision, "H1", "T1");
    }

    @Test
    void fullTicketFirstThenHotelCompletesRevision() {
        packRevisionService.processAspectEvent(ticketFull(1, 1, "T1"));
        assertRevision(1, PackRevisionStatus.WAITING_ASPECTS, null, 1);

        packRevisionService.processAspectEvent(hotelFull(1, 1, "H1"));
        PackRevisionRecord revision = assertRevision(1, PackRevisionStatus.COMPLETED, 1, 1);
        assertPackSnapshot(revision, "H1", "T1");
    }

    @Test
    void hotelsAfterCompletedFullUsesNewHotelAndPreviousTicket() {
        completeFullRevision(1, "H1", "T1");

        packRevisionService.processAspectEvent(hotelsOnly(2, 2, "H2"));
        PackRevisionRecord revision = assertRevision(2, PackRevisionStatus.COMPLETED, 2, 1);
        assertPackSnapshot(revision, "H2", "T1");
    }

    @Test
    void ticketsAfterCompletedHotelsUsesNewTicketAndPreviousHotel() {
        packRevisionService.processAspectEvent(hotelFull(1, 1, "H1"));
        packRevisionService.processAspectEvent(ticketFull(1, 1, "T1"));
        packRevisionService.processAspectEvent(hotelsOnly(2, 2, "H2"));
        assertRevision(2, PackRevisionStatus.COMPLETED, 2, 1);

        packRevisionService.processAspectEvent(ticketsOnly(3, 2, "T2"));
        PackRevisionRecord revision = assertRevision(3, PackRevisionStatus.COMPLETED, 2, 2);
        assertPackSnapshot(revision, "H2", "T2");
    }

    @Test
    void hotelsBeforePreviousCompletedStaysWaitingPreviousPack() {
        packRevisionService.processAspectEvent(hotelsOnly(2, 2, "H2"));
        assertRevision(2, PackRevisionStatus.WAITING_PREVIOUS_PACK, 2, null);
    }

    @Test
    void waitingHotelsCompletesAfterPreviousRevisionCompleted() {
        packRevisionService.processAspectEvent(hotelsOnly(2, 2, "H2"));
        assertRevision(2, PackRevisionStatus.WAITING_PREVIOUS_PACK, 2, null);

        completeFullRevision(1, "H1", "T1");

        PackRevisionRecord revision = assertRevision(2, PackRevisionStatus.COMPLETED, 2, 1);
        assertPackSnapshot(revision, "H2", "T1");
    }

    @Test
    void multipleWaitingRevisionsCompleteInChain() {
        packRevisionService.processAspectEvent(hotelFull(1, 1, "H1"));
        assertRevision(1, PackRevisionStatus.WAITING_ASPECTS, 1, null);

        packRevisionService.processAspectEvent(hotelsOnly(2, 2, "H2"));
        assertRevision(2, PackRevisionStatus.WAITING_PREVIOUS_PACK, 2, null);

        packRevisionService.processAspectEvent(ticketsOnly(3, 2, "T2"));
        assertRevision(3, PackRevisionStatus.WAITING_PREVIOUS_PACK, null, 2);

        packRevisionService.processAspectEvent(ticketFull(1, 1, "T1"));

        assertRevision(1, PackRevisionStatus.COMPLETED, 1, 1);
        assertRevision(2, PackRevisionStatus.COMPLETED, 2, 1);
        PackRevisionRecord revision3 = assertRevision(3, PackRevisionStatus.COMPLETED, 2, 2);
        assertPackSnapshot(revision3, "H2", "T2");
    }

    @Test
    void duplicateKafkaEventDoesNotCreateDuplicates() {
        PackAspectEvent hotelEvent = hotelFull(1, 1, "H1");

        packRevisionService.processAspectEvent(hotelEvent);
        packRevisionService.processAspectEvent(hotelEvent);

        assertThat(packRevisionRepository.findByKey(subjectId, generationId, 1)).isPresent();
        assertThat(countPackRevisions()).isEqualTo(1);
        assertThat(countKafkaProcessedEvents()).isEqualTo(1);
        assertThat(countHotelSnapshots()).isEqualTo(1);
    }

    @Test
    void expiredWaitingAspectsMarkedAsFailed() {
        packRevisionService.processAspectEvent(hotelFull(1, 1, "H1"));
        PackRevisionRecord revision = assertRevision(1, PackRevisionStatus.WAITING_ASPECTS, 1, null);

        backdateUpdatedAt(revision.id(), Duration.ofMinutes(31));

        int failedCount = packRevisionService.failExpiredWaitingRevisions();

        assertThat(failedCount).isEqualTo(1);
        PackRevisionRecord failed = assertRevision(1, PackRevisionStatus.FAILED, 1, null);
        assertThat(failed.failureReason()).isEqualTo(FailureReason.SECOND_ASPECT_NOT_RECEIVED);
    }

    @Test
    void expiredWaitingPreviousPackMarkedAsFailed() {
        packRevisionService.processAspectEvent(hotelsOnly(2, 2, "H2"));
        PackRevisionRecord revision = assertRevision(2, PackRevisionStatus.WAITING_PREVIOUS_PACK, 2, null);

        backdateUpdatedAt(revision.id(), Duration.ofMinutes(31));

        int failedCount = packRevisionService.failExpiredWaitingRevisions();

        assertThat(failedCount).isEqualTo(1);
        PackRevisionRecord failed = assertRevision(2, PackRevisionStatus.FAILED, 2, null);
        assertThat(failed.failureReason()).isEqualTo(FailureReason.PREVIOUS_PACK_NOT_FOUND);
    }

    private void completeFullRevision(int packRevisionId, String hotelKey, String ticketKey) {
        packRevisionService.processAspectEvent(hotelFull(packRevisionId, packRevisionId, hotelKey));
        packRevisionService.processAspectEvent(ticketFull(packRevisionId, packRevisionId, ticketKey));
    }

    private PackAspectEvent hotelFull(int packRevisionId, int hotelRevisionId, String hotelKey) {
        return PackAspectEventFactory.hotelFull(
                objectMapper,
                subjectId,
                generationId,
                packRevisionId,
                hotelRevisionId,
                hotelKey
        );
    }

    private PackAspectEvent ticketFull(int packRevisionId, int ticketRevisionId, String ticketKey) {
        return PackAspectEventFactory.ticketFull(
                objectMapper,
                subjectId,
                generationId,
                packRevisionId,
                ticketRevisionId,
                ticketKey
        );
    }

    private PackAspectEvent hotelsOnly(int packRevisionId, int hotelRevisionId, String hotelKey) {
        return PackAspectEventFactory.hotelsOnly(
                objectMapper,
                subjectId,
                generationId,
                packRevisionId,
                hotelRevisionId,
                hotelKey
        );
    }

    private PackAspectEvent ticketsOnly(int packRevisionId, int ticketRevisionId, String ticketKey) {
        return PackAspectEventFactory.ticketsOnly(
                objectMapper,
                subjectId,
                generationId,
                packRevisionId,
                ticketRevisionId,
                ticketKey
        );
    }

    private PackRevisionRecord assertRevision(
            int packRevisionId,
            PackRevisionStatus status,
            Integer hotelRevisionId,
            Integer ticketRevisionId
    ) {
        PackRevisionRecord revision = packRevisionRepository
                .findByKey(subjectId, generationId, packRevisionId)
                .orElseThrow();

        assertThat(revision.status()).isEqualTo(status);
        assertThat(revision.hotelRevisionId()).isEqualTo(hotelRevisionId);
        assertThat(revision.ticketRevisionId()).isEqualTo(ticketRevisionId);

        if (status == PackRevisionStatus.COMPLETED) {
            assertThat(revision.packSnapshotId()).isNotBlank();
        }

        return revision;
    }

    private void assertPackSnapshot(PackRevisionRecord revision, String expectedHotelKey, String expectedTicketKey) {
        PackSnapshotDocument snapshot = packSnapshotRepository.findById(revision.packSnapshotId()).orElseThrow();

        assertThat(readEntityKey(snapshot.hotel(), "hotelKey")).isEqualTo(expectedHotelKey);
        assertThat(readEntityKey(snapshot.ticket(), "ticketKey")).isEqualTo(expectedTicketKey);
        assertThat(snapshot.hotelRevisionId()).isEqualTo(revision.hotelRevisionId());
        assertThat(snapshot.ticketRevisionId()).isEqualTo(revision.ticketRevisionId());
    }

    private String readEntityKey(org.bson.Document document, String fieldName) {
        try {
            JsonNode node = objectMapper.readTree(document.toJson());
            return node.get(fieldName).asText();
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to read snapshot payload field " + fieldName, exception);
        }
    }

    private void backdateUpdatedAt(UUID revisionId, Duration age) {
        jdbcTemplate.update(
                "update pack_revisions set updated_at = ? where id = ?",
                Timestamp.from(Instant.now().minus(age)),
                revisionId
        );
    }

    private int countPackRevisions() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from pack_revisions", Integer.class);
        return count == null ? 0 : count;
    }

    private int countKafkaProcessedEvents() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from kafka_processed_events", Integer.class);
        return count == null ? 0 : count;
    }

    private int countHotelSnapshots() {
        return (int) hotelSnapshotRepository.count();
    }
}
