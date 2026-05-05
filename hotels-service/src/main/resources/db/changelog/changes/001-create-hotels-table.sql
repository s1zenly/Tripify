--liquibase formatted sql

--changeset tripify:001-create-hotels
create table hotels
(
    id                 uuid            not null,

    external_hotel_id  bigint          not null,
    provider_name      varchar(64)     not null,

    title              varchar(512)    not null,
    external_link      text            not null,
    description        text            not null,

    address            varchar(512)    not null,
    city               varchar(128)    not null,
    country            varchar(8)      not null,

    currency           varchar(8)      not null,
    price              numeric(12, 2)  not null,

    max_guests         integer         not null default 2,

    hotel_class        smallint        not null,

    latitude           numeric(10, 7)  not null,
    longitude          numeric(10, 7)  not null,

    reviews_total      integer         not null,
    reviews_rating     numeric(3, 2)   not null,

    parsed_at          timestamptz     not null,
    provided_at        timestamptz     not null,

    created_at         timestamptz     not null,
    updated_at         timestamptz     not null,

    constraint pk_hotels primary key (id),
    constraint uq_hotels_provider_external_id unique (provider_name, external_hotel_id)
);

create index idx_hotels_search
    on hotels (country, lower(city), id);

comment on table hotels is 'Основная информация об отелях';

comment on column hotels.id is 'Уникальный id отеля';
comment on column hotels.external_hotel_id is 'Внешний идентификатор отеля от provider';
comment on column hotels.provider_name is 'Название provider источника данных';
comment on column hotels.title is 'Название отеля';
comment on column hotels.external_link is 'Ссылка на страницу отеля у provider';
comment on column hotels.description is 'Описание отеля';
comment on column hotels.address is 'Адрес отеля';
comment on column hotels.city is 'Город расположения';
comment on column hotels.country is 'Код страны ISO Alpha-2';
comment on column hotels.currency is 'Валюта хранения цены (USD)';
comment on column hotels.price is 'Минимальная цена за ночь в USD (среди всех номеров/тарифов)';
comment on column hotels.max_guests is 'Максимальная вместимость гостей (среди всех номеров)';
comment on column hotels.hotel_class is 'Класс отеля в звездах';
comment on column hotels.latitude is 'Широта расположения';
comment on column hotels.longitude is 'Долгота расположения';
comment on column hotels.reviews_total is 'Количество отзывов';
comment on column hotels.reviews_rating is 'Средний рейтинг отеля';
comment on column hotels.parsed_at is 'Время парсинга provider';
comment on column hotels.provided_at is 'Время предоставления данных provider';
comment on column hotels.created_at is 'Дата создания записи';
comment on column hotels.updated_at is 'Дата обновления записи';
