--liquibase formatted sql

--changeset tripify:002-create-kafka-processed-events
create table kafka_processed_events
(
    id               uuid            not null,
    topic            varchar(255)    not null,
    kafka_partition  integer         not null,
    kafka_offset     bigint          not null,
    event_id         varchar(255),
    subject_id       varchar(255)    not null,
    generation_id    varchar(255)    not null,
    pack_revision_id integer         not null,
    created_at       timestamptz     not null,

    constraint pk_kafka_processed_events primary key (id),
    constraint uq_kafka_processed_events_topic_partition_offset
        unique (topic, kafka_partition, kafka_offset)
);

create index idx_kafka_processed_events_event_id
    on kafka_processed_events (event_id)
    where event_id is not null;

create index idx_kafka_processed_events_subject_generation
    on kafka_processed_events (subject_id, generation_id);

comment on table kafka_processed_events is 'Идемпотентная обработка Kafka-событий pack-service';

comment on column kafka_processed_events.id is 'Внутренний идентификатор записи';
comment on column kafka_processed_events.topic is 'Kafka topic';
comment on column kafka_processed_events.kafka_partition is 'Kafka partition';
comment on column kafka_processed_events.kafka_offset is 'Kafka offset';
comment on column kafka_processed_events.event_id is 'Бизнес-идентификатор события, если есть';
comment on column kafka_processed_events.subject_id is 'Ключ пользователя из события';
comment on column kafka_processed_events.generation_id is 'Идентификатор пользовательской сессии генерации';
comment on column kafka_processed_events.pack_revision_id is 'Общая ревизия pack из события';
comment on column kafka_processed_events.created_at is 'Дата обработки события';
