--liquibase formatted sql

--changeset syuscherbina:create-refresh-tokens-table
create table refresh_tokens
(
    id         uuid         not null,
    user_id    uuid         not null,
    token_hash varchar(255) not null,
    revoked    boolean      not null default false,
    expires_at timestamptz  not null,
    created_at timestamptz  not null,

    constraint pk_refresh_tokens primary key (id),
    constraint uq_refresh_tokens_token_hash unique (token_hash),
    constraint fk_refresh_tokens_user
        foreign key (user_id)
            references users (id)
            on delete cascade
);

create index idx_refresh_tokens_user_id
    on refresh_tokens (user_id);

create index idx_refresh_tokens_user_id_revoked
    on refresh_tokens (user_id, revoked);

comment on table refresh_tokens is 'Refresh-токены пользователей для долгоживущих сессий';

comment on column refresh_tokens.id is 'Уникальный идентификатор refresh-токена';
comment on column refresh_tokens.user_id is 'Идентификатор пользователя, которому принадлежит refresh-токен';
comment on column refresh_tokens.token_hash is 'Хеш refresh-токена, исходный токен в БД не хранится';
comment on column refresh_tokens.revoked is 'Флаг отзыва refresh-токена';
comment on column refresh_tokens.expires_at is 'Дата и время истечения refresh-токена';
comment on column refresh_tokens.created_at is 'Дата и время создания refresh-токена';