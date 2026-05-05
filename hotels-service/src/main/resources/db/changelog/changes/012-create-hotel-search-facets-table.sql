--liquibase formatted sql

--changeset tripify:012-create-hotel-search-facets
create table hotel_search_facets
(
    hotel_id   uuid         not null,
    facet      varchar(64)  not null,
    created_at timestamptz  not null,

    constraint pk_hotel_search_facets primary key (hotel_id, facet),

    constraint fk_hotel_search_facets_hotel_id
        foreign key (hotel_id)
            references hotels (id)
            on delete cascade
);

create index idx_hotel_search_facets_facet on hotel_search_facets (facet);

comment on table hotel_search_facets is 'Поисковые фасеты отеля (для GET /hotels?filters=)';
comment on column hotel_search_facets.hotel_id is 'Внутренний идентификатор отеля';
comment on column hotel_search_facets.facet is 'Идентификатор фасета из каталога фильтров';
comment on column hotel_search_facets.created_at is 'Дата создания записи';
