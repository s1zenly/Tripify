--liquibase formatted sql

--changeset syuscherbina:create-otp-requests-table
create table otp_requests
(
    id         uuid         not null,
    phone      varchar(32)  not null,
    otp_hash   varchar(255) not null,
    purpose    varchar(32)  not null,
    status     varchar(32)  not null,
    attempts   integer      not null default 0,
    expires_at timestamptz  not null,
    created_at timestamptz  not null,
    updated_at timestamptz  not null,

    constraint pk_otp_requests primary key (id)
);

create index idx_otp_requests_phone_created_at
    on otp_requests (phone, created_at desc);

create index idx_otp_requests_phone_status
    on otp_requests (phone, status);

comment on table otp_requests is 'OTP-запросы для входа и подтверждения действий по телефону';

comment on column otp_requests.id is 'Уникальный идентификатор OTP-запроса';
comment on column otp_requests.phone is 'Номер телефона, на который был отправлен OTP';
comment on column otp_requests.otp_hash is 'Хеш OTP-кода, исходный код в БД не хранится';
comment on column otp_requests.purpose is 'Назначение OTP: LOGIN, REGISTER';
comment on column otp_requests.status is 'Статус OTP-запроса: PENDING, VERIFIED, EXPIRED, FAILED';
comment on column otp_requests.attempts is 'Количество неуспешных попыток проверки OTP';
comment on column otp_requests.expires_at is 'Дата и время истечения OTP';
comment on column otp_requests.created_at is 'Дата и время создания OTP-запроса';
comment on column otp_requests.updated_at is 'Дата и время последней попытки ввода OTP-кода'