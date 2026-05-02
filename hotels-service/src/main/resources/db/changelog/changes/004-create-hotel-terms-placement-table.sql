--liquibase formatted sql

--changeset tripify:004-create-hotel-terms-placement
create table hotel_terms_placement
(
    hotel_id              uuid        not null,

    check_in_after_time   varchar(16),
    check_in_before_time  varchar(16),

    check_out_after_time  varchar(16),
    check_out_before_time varchar(16),

    timezone              varchar(64),

    cancellation          boolean     not null,
    refund_prepayment     boolean,

    smoking               boolean     not null,
    pet_friendly          boolean     not null,
    party_friendly        boolean     not null,

    age_restriction       integer,
    additional_info       text,

    created_at            timestamptz not null,
    updated_at            timestamptz not null,

    constraint pk_hotel_terms_placement primary key (hotel_id),

    constraint fk_hotel_terms_placement_hotel_id
        foreign key (hotel_id)
            references hotels (id)
            on delete cascade
);

comment on table hotel_terms_placement is 'Правила проживания и размещения отеля';

comment on column hotel_terms_placement.hotel_id is 'Внутренний идентификатор отеля';
comment on column hotel_terms_placement.check_in_after_time is 'Время начала check-in';
comment on column hotel_terms_placement.check_in_before_time is 'Время окончания check-in';
comment on column hotel_terms_placement.check_out_after_time is 'Время начала check-out';
comment on column hotel_terms_placement.check_out_before_time is 'Время окончания check-out';
comment on column hotel_terms_placement.timezone is 'Таймзона правил размещения';
comment on column hotel_terms_placement.cancellation is 'Доступна ли отмена бронирования';
comment on column hotel_terms_placement.refund_prepayment is 'Доступен ли возврат предоплаты';
comment on column hotel_terms_placement.smoking is 'Разрешено ли курение';
comment on column hotel_terms_placement.pet_friendly is 'Разрешено ли проживание с животными';
comment on column hotel_terms_placement.party_friendly is 'Разрешены ли вечеринки';
comment on column hotel_terms_placement.age_restriction is 'Возрастное ограничение';
comment on column hotel_terms_placement.additional_info is 'Дополнительная информация';
comment on column hotel_terms_placement.created_at is 'Дата создания записи';
comment on column hotel_terms_placement.updated_at is 'Дата обновления записи';