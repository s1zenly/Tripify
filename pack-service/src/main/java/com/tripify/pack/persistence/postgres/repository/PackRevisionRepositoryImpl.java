package com.tripify.pack.persistence.postgres.repository;

import com.tripify.pack.domain.FailureReason;
import com.tripify.pack.domain.GenerationMode;
import com.tripify.pack.domain.PackRevisionStatus;
import com.tripify.pack.domain.UserType;
import com.tripify.pack.infra.ResultSetUtils;
import com.tripify.pack.infra.SqlParams;
import com.tripify.pack.persistence.postgres.model.PackRevisionRecord;
import com.tripify.pack.persistence.postgres.repository.contract.PackRevisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PackRevisionRepositoryImpl implements PackRevisionRepository {

    private static final String SELECT_COLUMNS = """
            select id,
                   user_type,
                   subject_id,
                   user_id,
                   anonymous_id,
                   generation_id,
                   pack_revision_id,
                   generation_mode,
                   status,
                   hotel_revision_id,
                   ticket_revision_id,
                   hotel_snapshot_id,
                   ticket_snapshot_id,
                   pack_snapshot_id,
                   failure_reason,
                   created_at,
                   updated_at
            from pack_revisions
            """;

    private static final String FIND_BY_ID_QUERY = SELECT_COLUMNS + " where id = :id";

    private static final String FIND_BY_KEY_QUERY = SELECT_COLUMNS + """
            where subject_id = :subjectId
              and generation_id = :generationId
              and pack_revision_id = :packRevisionId
            """;

    private static final String FIND_LATEST_COMPLETED_QUERY = SELECT_COLUMNS + """
            where subject_id = :subjectId
              and generation_id = :generationId
              and status = 'COMPLETED'
            order by pack_revision_id desc
            limit 1
            """;

    private static final String FIND_PREVIOUS_COMPLETED_QUERY = SELECT_COLUMNS + """
            where subject_id = :subjectId
              and generation_id = :generationId
              and status = 'COMPLETED'
              and pack_revision_id < :packRevisionId
            order by pack_revision_id desc
            limit 1
            """;

    private static final String FIND_NEXT_WAITING_PREVIOUS_PACK_QUERY = SELECT_COLUMNS + """
            where subject_id = :subjectId
              and generation_id = :generationId
              and status = 'WAITING_PREVIOUS_PACK'
              and pack_revision_id > :afterPackRevisionId
            order by pack_revision_id asc
            limit 1
            """;

    private static final String FIND_COMPLETED_BY_PACK_SNAPSHOT_IDS_QUERY = SELECT_COLUMNS + """
            where status = 'COMPLETED'
              and pack_snapshot_id in (:packSnapshotIds)
            order by created_at desc
            """;

    private static final String FIND_COMPLETED_BY_SUBJECT_ID_QUERY = SELECT_COLUMNS + """
            where subject_id = :subjectId
              and status = 'COMPLETED'
            order by created_at asc
            """;

    private static final String FIND_RANDOM_COMPLETED_QUERY = SELECT_COLUMNS + """
            where status = 'COMPLETED'
              and pack_snapshot_id is not null
            order by random()
            limit :limit
            """;

    private static final String FIND_EXPIRED_WAITING_QUERY = SELECT_COLUMNS + """
            where status in (:statuses)
              and updated_at < :updatedBefore
            order by updated_at asc
            """;

    private static final String INSERT_QUERY = """
            insert into pack_revisions (
                id,
                user_type,
                subject_id,
                user_id,
                anonymous_id,
                generation_id,
                pack_revision_id,
                generation_mode,
                status,
                hotel_revision_id,
                ticket_revision_id,
                hotel_snapshot_id,
                ticket_snapshot_id,
                pack_snapshot_id,
                failure_reason,
                created_at,
                updated_at
            ) values (
                :id,
                :userType,
                :subjectId,
                :userId,
                :anonymousId,
                :generationId,
                :packRevisionId,
                :generationMode,
                :status,
                :hotelRevisionId,
                :ticketRevisionId,
                :hotelSnapshotId,
                :ticketSnapshotId,
                :packSnapshotId,
                :failureReason,
                :createdAt,
                :updatedAt
            )
            returning id,
                      user_type,
                      subject_id,
                      user_id,
                      anonymous_id,
                      generation_id,
                      pack_revision_id,
                      generation_mode,
                      status,
                      hotel_revision_id,
                      ticket_revision_id,
                      hotel_snapshot_id,
                      ticket_snapshot_id,
                      pack_snapshot_id,
                      failure_reason,
                      created_at,
                      updated_at
            """;

    private static final String UPDATE_QUERY = """
            update pack_revisions
            set user_type = :userType,
                subject_id = :subjectId,
                user_id = :userId,
                anonymous_id = :anonymousId,
                generation_id = :generationId,
                pack_revision_id = :packRevisionId,
                generation_mode = :generationMode,
                status = :status,
                hotel_revision_id = :hotelRevisionId,
                ticket_revision_id = :ticketRevisionId,
                hotel_snapshot_id = :hotelSnapshotId,
                ticket_snapshot_id = :ticketSnapshotId,
                pack_snapshot_id = :packSnapshotId,
                failure_reason = :failureReason,
                updated_at = :updatedAt
            where id = :id
            returning id,
                      user_type,
                      subject_id,
                      user_id,
                      anonymous_id,
                      generation_id,
                      pack_revision_id,
                      generation_mode,
                      status,
                      hotel_revision_id,
                      ticket_revision_id,
                      hotel_snapshot_id,
                      ticket_snapshot_id,
                      pack_snapshot_id,
                      failure_reason,
                      created_at,
                      updated_at
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<PackRevisionRecord> findById(UUID id) {
        return querySingle(
                FIND_BY_ID_QUERY,
                new SqlParams().addValue("id", id)
        );
    }

    @Override
    public Optional<PackRevisionRecord> findByKey(String subjectId, String generationId, int packRevisionId) {
        return querySingle(
                FIND_BY_KEY_QUERY,
                new SqlParams()
                        .addValue("subjectId", subjectId)
                        .addValue("generationId", generationId)
                        .addValue("packRevisionId", packRevisionId)
        );
    }

    @Override
    public Optional<PackRevisionRecord> findLatestCompleted(String subjectId, String generationId) {
        return querySingle(
                FIND_LATEST_COMPLETED_QUERY,
                new SqlParams()
                        .addValue("subjectId", subjectId)
                        .addValue("generationId", generationId)
        );
    }

    @Override
    public Optional<PackRevisionRecord> findPreviousCompleted(
            String subjectId,
            String generationId,
            int packRevisionId
    ) {
        return querySingle(
                FIND_PREVIOUS_COMPLETED_QUERY,
                new SqlParams()
                        .addValue("subjectId", subjectId)
                        .addValue("generationId", generationId)
                        .addValue("packRevisionId", packRevisionId)
        );
    }

    @Override
    public Optional<PackRevisionRecord> findNextWaitingPreviousPack(
            String subjectId,
            String generationId,
            int afterPackRevisionId
    ) {
        return querySingle(
                FIND_NEXT_WAITING_PREVIOUS_PACK_QUERY,
                new SqlParams()
                        .addValue("subjectId", subjectId)
                        .addValue("generationId", generationId)
                        .addValue("afterPackRevisionId", afterPackRevisionId)
        );
    }

    @Override
    public List<PackRevisionRecord> findCompletedByPackSnapshotIds(List<String> packSnapshotIds) {
        if (packSnapshotIds.isEmpty()) {
            return List.of();
        }

        return jdbcTemplate.query(
                FIND_COMPLETED_BY_PACK_SNAPSHOT_IDS_QUERY,
                new SqlParams().addValue("packSnapshotIds", packSnapshotIds),
                this::mapRecord
        );
    }

    @Override
    public List<PackRevisionRecord> findCompletedBySubjectIdOrderByCreatedAtAsc(String subjectId) {
        return jdbcTemplate.query(
                FIND_COMPLETED_BY_SUBJECT_ID_QUERY,
                new SqlParams().addValue("subjectId", subjectId),
                this::mapRecord
        );
    }

    @Override
    public List<PackRevisionRecord> findRandomCompleted(int limit) {
        if (limit <= 0) {
            return List.of();
        }

        return jdbcTemplate.query(
                FIND_RANDOM_COMPLETED_QUERY,
                new SqlParams().addValue("limit", limit),
                this::mapRecord
        );
    }

    @Override
    public List<PackRevisionRecord> findExpiredWaiting(Instant updatedBefore, PackRevisionStatus... statuses) {
        if (statuses.length == 0) {
            return List.of();
        }

        return jdbcTemplate.query(
                FIND_EXPIRED_WAITING_QUERY,
                new SqlParams()
                        .addValue("statuses", Arrays.stream(statuses).map(Enum::name).toList())
                        .addTimestamp("updatedBefore", updatedBefore),
                this::mapRecord
        );
    }

    @Override
    public PackRevisionRecord insert(PackRevisionRecord record) {
        return requireSingle(
                jdbcTemplate.query(INSERT_QUERY, toParams(record), this::mapRecord),
                "Failed to insert pack revision"
        );
    }

    @Override
    public PackRevisionRecord update(PackRevisionRecord record) {
        return requireSingle(
                jdbcTemplate.query(UPDATE_QUERY, toParams(record), this::mapRecord),
                "Failed to update pack revision id=" + record.id()
        );
    }

    private Optional<PackRevisionRecord> querySingle(String query, SqlParams params) {
        return jdbcTemplate.query(query, params, this::mapRecord).stream().findFirst();
    }

    private PackRevisionRecord requireSingle(List<PackRevisionRecord> records, String message) {
        if (records.isEmpty()) {
            throw new IllegalStateException(message);
        }
        return records.getFirst();
    }

    private SqlParams toParams(PackRevisionRecord record) {
        return new SqlParams()
                .addValue("id", record.id())
                .addValue("userType", record.userType().name())
                .addValue("subjectId", record.subjectId())
                .addValue("userId", record.userId())
                .addValue("anonymousId", record.anonymousId())
                .addValue("generationId", record.generationId())
                .addValue("packRevisionId", record.packRevisionId())
                .addValue("generationMode", record.generationMode().name())
                .addValue("status", record.status().name())
                .addValue("hotelRevisionId", record.hotelRevisionId())
                .addValue("ticketRevisionId", record.ticketRevisionId())
                .addValue("hotelSnapshotId", record.hotelSnapshotId())
                .addValue("ticketSnapshotId", record.ticketSnapshotId())
                .addValue("packSnapshotId", record.packSnapshotId())
                .addValue("failureReason", record.failureReason() == null ? null : record.failureReason().name())
                .addTimestamp("createdAt", record.createdAt())
                .addTimestamp("updatedAt", record.updatedAt());
    }

    private PackRevisionRecord mapRecord(ResultSet resultSet, int rowNum) throws SQLException {
        return new PackRevisionRecord(
                resultSet.getObject("id", UUID.class),
                UserType.valueOf(resultSet.getString("user_type")),
                resultSet.getString("subject_id"),
                resultSet.getString("user_id"),
                resultSet.getString("anonymous_id"),
                resultSet.getString("generation_id"),
                resultSet.getInt("pack_revision_id"),
                GenerationMode.valueOf(resultSet.getString("generation_mode")),
                PackRevisionStatus.valueOf(resultSet.getString("status")),
                (Integer) resultSet.getObject("hotel_revision_id"),
                (Integer) resultSet.getObject("ticket_revision_id"),
                resultSet.getString("hotel_snapshot_id"),
                resultSet.getString("ticket_snapshot_id"),
                resultSet.getString("pack_snapshot_id"),
                mapFailureReason(resultSet.getString("failure_reason")),
                ResultSetUtils.getInstant("created_at", resultSet),
                ResultSetUtils.getInstant("updated_at", resultSet)
        );
    }

    private FailureReason mapFailureReason(String value) {
        return value == null ? null : FailureReason.valueOf(value);
    }
}
