--liquibase formatted sql

--changeset tripify:007-create-hotel-reviews-summary
create table hotel_reviews_summary
(
    hotel_id      uuid          not null,

    rating        numeric(3, 2) not null,
    reviews_total integer       not null,

    cleanliness   numeric(4, 2),
    service       numeric(4, 2),
    price_quality numeric(4, 2),
    room          numeric(4, 2),
    location      numeric(4, 2),

    created_at    timestamptz   not null,
    updated_at    timestamptz   not null,

    constraint pk_hotel_reviews_summary primary key (hotel_id),

    constraint fk_hotel_reviews_summary_hotel_id
        foreign key (hotel_id)
            references hotels (id)
            on delete cascade
);

comment on table hotel_reviews_summary is 'Агрегированная информация по отзывам отеля';

comment on column hotel_reviews_summary.hotel_id is 'Внутренний идентификатор отеля';
comment on column hotel_reviews_summary.rating is 'Средний рейтинг';
comment on column hotel_reviews_summary.reviews_total is 'Общее количество отзывов';
comment on column hotel_reviews_summary.cleanliness is 'Оценка чистоты';
comment on column hotel_reviews_summary.service is 'Оценка сервиса';
comment on column hotel_reviews_summary.price_quality is 'Оценка соотношения цена/качество';
comment on column hotel_reviews_summary.room is 'Оценка номера';
comment on column hotel_reviews_summary.location is 'Оценка расположения';
comment on column hotel_reviews_summary.created_at is 'Дата создания записи';
comment on column hotel_reviews_summary.updated_at is 'Дата обновления записи';