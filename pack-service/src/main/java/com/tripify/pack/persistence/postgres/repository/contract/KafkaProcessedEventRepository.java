package com.tripify.pack.persistence.postgres.repository.contract;

import com.tripify.pack.persistence.postgres.model.KafkaProcessedEventRecord;

public interface KafkaProcessedEventRepository {

    boolean existsByTopicPartitionOffset(String topic, int kafkaPartition, long kafkaOffset);

    void insert(KafkaProcessedEventRecord record);
}
