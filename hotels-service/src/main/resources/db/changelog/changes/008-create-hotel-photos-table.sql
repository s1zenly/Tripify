--liquibase formatted sql

--changeset tripify:008-create-hotel-photos
create table hotel_photos
(
    id          uuid          not null,
    hotel_id    uuid          not null,

    s3_key      text          not null,
    sort_order  integer       not null default 0,
    description text,

    created_at  timestamptz   not null,

    constraint pk_hotel_photos primary key (id),

    constraint fk_hotel_photos_hotel_id
        foreign key (hotel_id)
            references hotels (id)
            on delete cascade
);

create index idx_hotel_photos_hotel_id_sort_order on hotel_photos (hotel_id, sort_order);

comment on table hotel_photos is 'Фотографии отеля, хранящиеся в S3';

comment on column hotel_photos.id is 'Уникальный идентификатор фото';
comment on column hotel_photos.hotel_id is 'Внутренний идентификатор отеля';
comment on column hotel_photos.s3_key is 'Путь к объекту в S3';
comment on column hotel_photos.sort_order is 'Порядок отображения фото';
comment on column hotel_photos.description is 'Описание фото';
comment on column hotel_photos.created_at is 'Дата создания записи';
