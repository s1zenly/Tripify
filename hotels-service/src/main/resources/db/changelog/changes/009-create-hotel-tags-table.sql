--liquibase formatted sql

--changeset tripify:009-create-hotel-tags
create table hotel_tags
(
    hotel_id   uuid         not null,
    tag        varchar(128) not null,
    created_at timestamptz  not null,

    constraint pk_hotel_tags primary key (hotel_id, tag),

    constraint fk_hotel_tags_hotel_id
        foreign key (hotel_id)
            references hotels (id)
            on delete cascade
);

comment on table hotel_tags is 'Теги отеля для фильтрации и отображения';

comment on column hotel_tags.hotel_id is 'Внутренний идентификатор отеля';
comment on column hotel_tags.tag is 'Тег отеля';
comment on column hotel_tags.created_at is 'Дата создания записи';
