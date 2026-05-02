--liquibase formatted sql

--changeset tripify:005-create-hotel-refund-conditions
create table hotel_refund_conditions
(
    id                    uuid        not null,
    hotel_id              uuid        not null,

    quantity_percent      integer     not null,
    condition_description text        not null,

    created_at            timestamptz not null,

    constraint pk_hotel_refund_conditions primary key (id),

    constraint fk_hotel_refund_conditions_hotel_id
        foreign key (hotel_id)
            references hotels (id)
            on delete cascade
);

comment on table hotel_refund_conditions is 'Условия возврата и отмены бронирования';

comment on column hotel_refund_conditions.id is 'Уникальный идентификатор refund condition';
comment on column hotel_refund_conditions.hotel_id is 'Внутренний идентификатор отеля';
comment on column hotel_refund_conditions.quantity_percent is 'Процент возврата';
comment on column hotel_refund_conditions.condition_description is 'Описание условия возврата';
comment on column hotel_refund_conditions.created_at is 'Дата создания записи';