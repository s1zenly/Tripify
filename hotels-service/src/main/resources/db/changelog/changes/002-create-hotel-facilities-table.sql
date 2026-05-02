--liquibase formatted sql

--changeset tripify:002-create-hotel-facilities
create table hotel_facilities
(
    hotel_id       uuid        not null,
    facility_type  varchar(64) not null,
    is_free        boolean     not null,
    created_at     timestamptz not null,

    constraint pk_hotel_facilities primary key (hotel_id, facility_type),

    constraint fk_hotel_facilities_hotel_id
        foreign key (hotel_id)
            references hotels (id)
            on delete cascade
);

comment on table hotel_facilities is 'Удобства и сервисы отеля';

comment on column hotel_facilities.hotel_id is 'Внутренний идентификатор отеля';
comment on column hotel_facilities.facility_type is 'Тип удобства: wifi, parking, breakfast, pool';
comment on column hotel_facilities.is_free is 'Признак бесплатного удобства';
comment on column hotel_facilities.created_at is 'Дата создания записи';
