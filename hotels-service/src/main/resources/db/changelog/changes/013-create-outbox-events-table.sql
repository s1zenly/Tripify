--liquibase formatted sql

--changeset tripify:013-create-hotels-outbox-events-table
create table hotels_outbox_events
(
    id             uuid         not null,
    aggregate_type varchar(64)  not null,
    aggregate_id   varchar(128) not null,
    event_type     varchar(128) not null,
    topic          varchar(128) not null,
    payload        jsonb        not null,
    status         varchar(32)  not null,
    attempts       integer      not null default 0,
    error_message  text,
    created_at     timestamptz  not null,
    updated_at     timestamptz  not null,
    published_at   timestamptz,

    constraint pk_hotels_outbox_events primary key (id)
);

create index idx_hotels_outbox_events_status_created_at
    on hotels_outbox_events (status, created_at);

create index idx_hotels_outbox_events_aggregate
    on hotels_outbox_events (aggregate_type, aggregate_id);

comment on table hotels_outbox_events is 'Outbox hotels-service для надежной отправки сообщений в Kafka';
