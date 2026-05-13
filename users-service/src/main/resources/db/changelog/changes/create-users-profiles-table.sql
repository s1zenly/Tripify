--liquibase formatted sql

--changeset tripify:001-create-user-profiles
create table user_profiles
(
    user_id       uuid         not null,
    email         varchar(255),
    first_name    varchar(128),
    last_name     varchar(128),
    phone_number  varchar(32)  not null,
    city          varchar(128),
    country       varchar(128),
    currency      varchar(128) not null default 'RUB',
    citizenship   varchar(128),
    created_at    timestamptz  not null,
    updated_at    timestamptz  not null,

    constraint pk_user_profiles primary key (user_id),
    constraint uq_user_profiles_email unique (email)
);

comment on table user_profiles is 'Профили пользователей user-service';

comment on column user_profiles.user_id is 'Глобальный идентификатор пользователя из auth-service';
comment on column user_profiles.email is 'Email пользователя';
comment on column user_profiles.first_name is 'Имя пользователя';
comment on column user_profiles.last_name is 'Фамилия пользователя';
comment on column user_profiles.phone_number is 'Номер телефона пользователя в формате E.164';
comment on column user_profiles.city is 'Город проживания пользователя';
comment on column user_profiles.country is 'Страна проживания пользователя';
comment on column user_profiles.currency is 'Предпочитаемая валюта пользователя';
comment on column user_profiles.citizenship is 'Гражданство пользователя';
comment on column user_profiles.created_at is 'Дата и время создания профиля';
comment on column user_profiles.updated_at is 'Дата и время последнего обновления профиля';