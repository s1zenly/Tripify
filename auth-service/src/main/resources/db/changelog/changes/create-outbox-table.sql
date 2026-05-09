--liquibase formatted sql

--changeset syuscherbina:create-outbox-events-table
create table outbox_events
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

    constraint pk_outbox_events primary key (id)
);

create index idx_outbox_events_status_created_at
    on outbox_events (status, created_at);

create index idx_outbox_events_aggregate
    on outbox_events (aggregate_type, aggregate_id);

comment on table outbox_events is 'Outbox-события для надежной отправки сообщений в Kafka';

comment on column outbox_events.id is 'Уникальный идентификатор outbox-события';
comment on column outbox_events.aggregate_type is 'Тип агрегата, например USER, OTP_REQUEST, SESSION';
comment on column outbox_events.aggregate_id is 'Идентификатор агрегата, связанного с событием';
comment on column outbox_events.event_type is 'Тип события, например OTP_REQUESTED, USER_REGISTERED, SESSION_CREATED';
comment on column outbox_events.topic is 'Kafka topic, в который нужно отправить событие';
comment on column outbox_events.payload is 'Тело события в JSON-формате';
comment on column outbox_events.status is 'Статус события: PENDING, PUBLISHED, FAILED';
comment on column outbox_events.attempts is 'Количество попыток отправки события';
comment on column outbox_events.error_message is 'Последняя ошибка отправки события';
comment on column outbox_events.created_at is 'Дата и время создания события';
comment on column outbox_events.updated_at is 'Дата и время последнего обновления события';
comment on column outbox_events.published_at is 'Дата и время успешной публикации события в Kafka';