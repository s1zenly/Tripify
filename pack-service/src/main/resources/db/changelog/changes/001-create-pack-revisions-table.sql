--liquibase formatted sql

--changeset tripify:001-create-pack-revisions
create table pack_revisions
(
    id                  uuid            not null,
    user_type           varchar(16)     not null,
    subject_id          varchar(255)    not null,
    user_id             varchar(255),
    anonymous_id        varchar(255),
    generation_id       varchar(255)    not null,
    pack_revision_id    integer         not null,
    generation_mode     varchar(16)     not null,
    status              varchar(32)     not null,
    hotel_revision_id   integer,
    ticket_revision_id  integer,
    hotel_snapshot_id   varchar(24),
    ticket_snapshot_id  varchar(24),
    pack_snapshot_id    varchar(24),
    failure_reason      varchar(64),
    created_at          timestamptz     not null,
    updated_at          timestamptz     not null,

    constraint pk_pack_revisions primary key (id),
    constraint uq_pack_revisions_subject_generation_revision
        unique (subject_id, generation_id, pack_revision_id),
    constraint chk_pack_revisions_user_type
        check (user_type in ('ANONYMOUS', 'AUTH')),
    constraint chk_pack_revisions_generation_mode
        check (generation_mode in ('FULL', 'HOTELS', 'TICKETS')),
    constraint chk_pack_revisions_status
        check (status in ('WAITING_ASPECTS', 'WAITING_PREVIOUS_PACK', 'COMPLETED', 'FAILED')),
    constraint chk_pack_revisions_failure_reason
        check (failure_reason is null or failure_reason in (
            'SECOND_ASPECT_NOT_RECEIVED',
            'PREVIOUS_PACK_NOT_FOUND',
            'INVALID_EVENT',
            'SNAPSHOT_NOT_FOUND',
            'UNKNOWN_ERROR'
        ))
);

create index idx_pack_revisions_subject_generation
    on pack_revisions (subject_id, generation_id);

create index idx_pack_revisions_subject_generation_status
    on pack_revisions (subject_id, generation_id, status);

create index idx_pack_revisions_status_updated_at
    on pack_revisions (status, updated_at);

comment on table pack_revisions is 'Состояние сборки pack snapshot по ревизиям пользовательской генерации';

comment on column pack_revisions.id is 'Внутренний идентификатор записи';
comment on column pack_revisions.user_type is 'Тип пользователя: ANONYMOUS или AUTH';
comment on column pack_revisions.subject_id is 'Ключ пользователя: user_id для AUTH, anonymous_id для ANONYMOUS';
comment on column pack_revisions.user_id is 'Идентификатор авторизованного пользователя из события';
comment on column pack_revisions.anonymous_id is 'Идентификатор анонимного пользователя из события';
comment on column pack_revisions.generation_id is 'Идентификатор пользовательской сессии генерации';
comment on column pack_revisions.pack_revision_id is 'Общая ревизия pack';
comment on column pack_revisions.generation_mode is 'Режим генерации: FULL, HOTELS или TICKETS';
comment on column pack_revisions.status is 'Статус сборки pack snapshot';
comment on column pack_revisions.hotel_revision_id is 'Ревизия hotel-аспекта';
comment on column pack_revisions.ticket_revision_id is 'Ревизия ticket-аспекта';
comment on column pack_revisions.hotel_snapshot_id is 'MongoDB id hotel snapshot';
comment on column pack_revisions.ticket_snapshot_id is 'MongoDB id ticket snapshot';
comment on column pack_revisions.pack_snapshot_id is 'MongoDB id собранного pack snapshot';
comment on column pack_revisions.failure_reason is 'Причина FAILED-статуса';
comment on column pack_revisions.created_at is 'Дата создания записи';
comment on column pack_revisions.updated_at is 'Дата последнего обновления записи';
