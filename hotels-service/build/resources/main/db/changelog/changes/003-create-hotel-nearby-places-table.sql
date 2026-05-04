--liquibase formatted sql

--changeset tripify:003-create-hotel-nearby-places
create table hotel_nearby_places
(
    id             uuid          not null,
    hotel_id       uuid          not null,

    category       varchar(64)   not null,
    title          varchar(256)  not null,

    distance_value numeric(8, 2) not null,
    distance_unit  varchar(16)   not null,

    created_at     timestamptz   not null,

    constraint pk_hotel_nearby_places primary key (id),

    constraint fk_hotel_nearby_places_hotel_id
        foreign key (hotel_id)
            references hotels (id)
            on delete cascade
);

comment on table hotel_nearby_places is 'Ближайшие места рядом с отелем';

comment on column hotel_nearby_places.id is 'Уникальный идентификатор nearby place';
comment on column hotel_nearby_places.hotel_id is 'Внутренний идентификатор отеля';
comment on column hotel_nearby_places.category is 'Категория nearby place: food, beaches и другие';
comment on column hotel_nearby_places.title is 'Название nearby place';
comment on column hotel_nearby_places.distance_value is 'Расстояние до объекта';
comment on column hotel_nearby_places.distance_unit is 'Единица измерения расстояния';
comment on column hotel_nearby_places.created_at is 'Дата создания записи';