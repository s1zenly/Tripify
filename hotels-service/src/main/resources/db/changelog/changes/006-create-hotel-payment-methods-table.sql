--liquibase formatted sql

--changeset tripify:006-create-hotel-payment-methods
create table hotel_payment_methods
(
    hotel_id        uuid           not null,

    is_cash         boolean        not null,
    cash_currencies varchar(256)[],

    is_card         boolean        not null,
    card_types      varchar(256)[],

    created_at      timestamptz    not null,
    updated_at      timestamptz    not null,

    constraint pk_hotel_payment_methods primary key (hotel_id),

    constraint fk_hotel_payment_methods_hotel_id
        foreign key (hotel_id)
            references hotels (id)
            on delete cascade
);

comment on table hotel_payment_methods is 'Способы оплаты отеля';

comment on column hotel_payment_methods.hotel_id is 'Внутренний идентификатор отеля';
comment on column hotel_payment_methods.is_cash is 'Доступна ли оплата наличными';
comment on column hotel_payment_methods.cash_currencies is 'Список валют для наличной оплаты';
comment on column hotel_payment_methods.is_card is 'Доступна ли оплата картой';
comment on column hotel_payment_methods.card_types is 'Поддерживаемые типы банковских карт';
comment on column hotel_payment_methods.created_at is 'Дата создания записи';
comment on column hotel_payment_methods.updated_at is 'Дата обновления записи';
