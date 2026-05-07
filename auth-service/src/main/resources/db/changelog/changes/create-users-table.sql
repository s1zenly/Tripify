--liquibase formatted sql

--changeset tripify:001-create-users
create table users
(
    id         uuid        not null,
    phone      varchar(32) not null,
    status     varchar(32) not null,
    created_at timestamptz not null,
    updated_at timestamptz not null,

    constraint pk_users primary key (id),
    constraint uq_users_phone unique (phone)
);

comment on table users is 'Пользователи auth-service, идентифицируемые по номеру телефона';

comment on column users.id is 'Уникальный идентификатор пользователя';
comment on column users.phone is 'Номер телефона пользователя в нормализованном E.164 формате';
comment on column users.status is 'Статус пользователя: ACTIVE, BLOCKED, DELETED';
comment on column users.created_at is 'Дата и время создания пользователя';
comment on column users.updated_at is 'Дата и время последнего обновления пользователя';