package com.tripify.pack.persistence.postgres.repository.contract;

import com.tripify.pack.domain.PackRevisionStatus;
import com.tripify.pack.persistence.postgres.model.PackRevisionRecord;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PackRevisionRepository {

    Optional<PackRevisionRecord> findById(UUID id);

    Optional<PackRevisionRecord> findByKey(String subjectId, String generationId, int packRevisionId);

    Optional<PackRevisionRecord> findLatestCompleted(String subjectId, String generationId);

    Optional<PackRevisionRecord> findPreviousCompleted(String subjectId, String generationId, int packRevisionId);

    Optional<PackRevisionRecord> findNextWaitingPreviousPack(
            String subjectId,
            String generationId,
            int afterPackRevisionId
    );

    List<PackRevisionRecord> findCompletedByPackSnapshotIds(List<String> packSnapshotIds);

    List<PackRevisionRecord> findCompletedBySubjectIdOrderByCreatedAtAsc(String subjectId);

    List<PackRevisionRecord> findRandomCompleted(int limit);

    List<PackRevisionRecord> findExpiredWaiting(Instant updatedBefore, PackRevisionStatus... statuses);

    PackRevisionRecord insert(PackRevisionRecord record);

    PackRevisionRecord update(PackRevisionRecord record);
}
