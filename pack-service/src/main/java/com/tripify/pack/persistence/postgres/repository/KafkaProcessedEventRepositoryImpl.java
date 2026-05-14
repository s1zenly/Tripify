package com.tripify.pack.persistence.postgres.repository;

import com.tripify.pack.infra.SqlParams;
import com.tripify.pack.persistence.postgres.model.KafkaProcessedEventRecord;
import com.tripify.pack.persistence.postgres.repository.contract.KafkaProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KafkaProcessedEventRepositoryImpl implements KafkaProcessedEventRepository {

    private static final String EXISTS_QUERY = """
            select exists(
                select 1
                from kafka_processed_events
                where topic = :topic
                  and kafka_partition = :kafkaPartition
                  and kafka_offset = :kafkaOffset
            )
            """;

    private static final String INSERT_QUERY = """
            insert into kafka_processed_events (
                id,
                topic,
                kafka_partition,
                kafka_offset,
                event_id,
                subject_id,
                generation_id,
                pack_revision_id,
                created_at
            ) values (
                :id,
                :topic,
                :kafkaPartition,
                :kafkaOffset,
                :eventId,
                :subjectId,
                :generationId,
                :packRevisionId,
                :createdAt
            )
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean existsByTopicPartitionOffset(String topic, int kafkaPartition, long kafkaOffset) {
        Boolean exists = jdbcTemplate.queryForObject(
                EXISTS_QUERY,
                new SqlParams()
                        .addValue("topic", topic)
                        .addValue("kafkaPartition", kafkaPartition)
                        .addValue("kafkaOffset", kafkaOffset),
                Boolean.class
        );
        return Boolean.TRUE.equals(exists);
    }

    @Override
    public void insert(KafkaProcessedEventRecord record) {
        jdbcTemplate.update(
                INSERT_QUERY,
                new SqlParams()
                        .addValue("id", record.id())
                        .addValue("topic", record.topic())
                        .addValue("kafkaPartition", record.kafkaPartition())
                        .addValue("kafkaOffset", record.kafkaOffset())
                        .addValue("eventId", record.eventId())
                        .addValue("subjectId", record.subjectId())
                        .addValue("generationId", record.generationId())
                        .addValue("packRevisionId", record.packRevisionId())
                        .addTimestamp("createdAt", record.createdAt())
        );
    }
}
