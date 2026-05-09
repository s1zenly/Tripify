package com.tripify.auth.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import com.tripify.auth.service.domain.enums.UserStatus;
import com.tripify.auth.service.domain.model.User;
import com.tripify.auth.service.infra.SqlParams;
import com.tripify.auth.service.repository.contracts.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_USER_BY_PHONE =
            """
            select id, phone, status, created_at, updated_at
            from users
            where phone = :phone
            """;

    private static final String UPSERT_USER_QUERY =
            """
            insert into users(id, phone, status, created_at, updated_at)
            values(:id, :phone, :status, :created_at, :updated_at)
            on conflict(phone)
            do update set
                status = case
                    when users.status = 'DELETED'
                    then 'ACTIVE'
                    else users.status
                end,
                updated_at = excluded.updated_at
            returning id, phone, status, created_at, updated_at
            """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<User> findByPhone(String phone) {
        return namedParameterJdbcTemplate.query(
                FIND_USER_BY_PHONE,
                new SqlParams()
                        .addValue("phone", phone),
                this::mapUser
        ).stream().findFirst();
    }

    @Override
    public User upsertActiveUser(String phone) {
        Instant now = Instant.now();

        return namedParameterJdbcTemplate.queryForObject(
                UPSERT_USER_QUERY,
                new SqlParams()
                        .addValue("id", UUID.randomUUID())
                        .addValue("phone", phone)
                        .addValue("status", UserStatus.ACTIVE.name())
                        .addTimestamp("created_at", now)
                        .addTimestamp("updated_at", now),
                this::mapUser
        );
    }

    private User mapUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getObject("id", UUID.class),
                rs.getString("phone"),
                UserStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("updated_at").toInstant()
        );
    }
}