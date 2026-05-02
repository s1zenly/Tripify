--liquibase formatted sql

--changeset tripify:010-create-hotel-scores
create table hotel_scores
(
    hotel_id          uuid          not null,

    final_score       numeric(5, 4) not null,

    price_score       numeric(5, 4),
    rating_score      numeric(5, 4),
    location_score    numeric(5, 4),
    facilities_score  numeric(5, 4),

    created_at        timestamptz   not null,
    updated_at        timestamptz   not null,

    constraint pk_hotel_scores primary key (hotel_id),

    constraint fk_hotel_scores_hotel_id
        foreign key (hotel_id)
            references hotels (id)
            on delete cascade
);

comment on table hotel_scores is 'Итоговый score отеля для ранжирования';

comment on column hotel_scores.hotel_id is 'Внутренний идентификатор отеля';
comment on column hotel_scores.final_score is 'Итоговый score';
comment on column hotel_scores.price_score is 'Компонент score по цене';
comment on column hotel_scores.rating_score is 'Компонент score по рейтингу';
comment on column hotel_scores.location_score is 'Компонент score по расположению';
comment on column hotel_scores.facilities_score is 'Компонент score по удобствам';
comment on column hotel_scores.created_at is 'Дата создания записи';
comment on column hotel_scores.updated_at is 'Дата обновления записи';
