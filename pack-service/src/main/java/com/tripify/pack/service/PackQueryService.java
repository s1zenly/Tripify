package com.tripify.pack.service;

import com.tripify.pack.domain.PackRevisionStatus;
import com.tripify.pack.generated.model.Pack;
import com.tripify.pack.generated.model.PackCard;
import com.tripify.pack.generated.model.PacksResponse;
import com.tripify.pack.mapper.PackApiMapper;
import com.tripify.pack.persistence.mongo.document.PackSnapshotDocument;
import com.tripify.pack.persistence.mongo.repository.SnapshotRepository;
import com.tripify.pack.persistence.postgres.model.PackRevisionRecord;
import com.tripify.pack.persistence.postgres.repository.contract.PackRevisionRepository;
import com.tripify.pack.service.exception.PackNotFoundException;
import com.tripify.pack.service.model.PackSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PackQueryService {

    private static final int POPULAR_PACKS_LIMIT = 6;

    private final PackRevisionRepository packRevisionRepository;
    private final SnapshotRepository snapshotRepository;
    private final PackApiMapper packApiMapper;

    public PacksResponse getPacks(PackSearchCriteria criteria) {
        List<PackSnapshotDocument> snapshots = snapshotRepository.findPackSnapshotsBySearchCriteria(criteria);
        if (snapshots.isEmpty()) {
            return packApiMapper.toPacksResponse(List.of());
        }

        List<String> snapshotIds = snapshots.stream().map(PackSnapshotDocument::id).toList();
        Map<String, PackRevisionRecord> revisionsBySnapshotId = packRevisionRepository
                .findCompletedByPackSnapshotIds(snapshotIds)
                .stream()
                .collect(Collectors.toMap(PackRevisionRecord::packSnapshotId, Function.identity(), (left, right) -> left));

        List<PackCard> items = snapshots.stream()
                .map(snapshot -> Optional.ofNullable(revisionsBySnapshotId.get(snapshot.id()))
                        .map(revision -> packApiMapper.toPackCard(revision, snapshot)))
                .flatMap(Optional::stream)
                .toList();

        return packApiMapper.toPacksResponse(items);
    }

    public Pack getPackById(UUID packId) {
        PackRevisionRecord revision = packRevisionRepository.findById(packId)
                .filter(record -> record.status() == PackRevisionStatus.COMPLETED)
                .orElseThrow(() -> new PackNotFoundException(packId));

        PackSnapshotDocument snapshot = loadSnapshot(revision);
        return packApiMapper.toPack(revision, snapshot);
    }

    public PacksResponse getPopularPacks() {
        List<PackCard> items = packRevisionRepository.findRandomCompleted(POPULAR_PACKS_LIMIT)
                .stream()
                .flatMap(revision -> findSnapshot(revision)
                        .map(snapshot -> packApiMapper.toPackCard(revision, snapshot))
                        .stream())
                .toList();
        return packApiMapper.toPacksResponse(items);
    }

    public PacksResponse getPacksUserView(String subjectId) {
        List<PackCard> items = packRevisionRepository
                .findCompletedBySubjectIdOrderByCreatedAtAsc(subjectId)
                .stream()
                .flatMap(revision -> findSnapshot(revision)
                        .map(snapshot -> packApiMapper.toPackCard(revision, snapshot))
                        .stream())
                .toList();
        return packApiMapper.toPacksResponse(items);
    }

    private PackSnapshotDocument loadSnapshot(PackRevisionRecord revision) {
        return findSnapshot(revision)
                .orElseThrow(() -> new PackNotFoundException(revision.id()));
    }

    private Optional<PackSnapshotDocument> findSnapshot(PackRevisionRecord revision) {
        String packSnapshotId = revision.packSnapshotId();
        if (packSnapshotId == null || packSnapshotId.isBlank()) {
            return Optional.empty();
        }
        return snapshotRepository.findPackSnapshotById(packSnapshotId);
    }
}
