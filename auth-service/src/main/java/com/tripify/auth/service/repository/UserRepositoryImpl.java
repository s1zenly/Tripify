package com.tripify.auth.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import static com.tripify.auth.service.utils.ResultSetUtils.getTimestamp;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_USER_BY_PHONE =
            """
            select id, phone, status, created_at, updated_at
            from users
            where phone = :phone
            """;

    private static final String CREATE_OR_ACTIVATE_USER_QUERY =
            """
            insert into users (id, phone, status, created_at, updated_at)
            values (:id, :phone, :activeStatus, :createdAt, :updatedAt)
            on conflict (phone)
            do update set
                status = :activeStatus,
                updated_at = :updatedAt
            where users.status != :blockedStatus
            returning
                id,
                phone,
                status,
                created_at,
                updated_at
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
    public User createOrActivateUser(String phone, Instant now) {
        return namedParameterJdbcTemplate.queryForObject(
                CREATE_OR_ACTIVATE_USER_QUERY,
                new SqlParams()
                        .addValue("id", UUID.randomUUID())
                        .addValue("phone", phone)
                        .addValue("activeStatus", UserStatus.ACTIVE.name())
                        .addValue("blockedStatus", UserStatus.BLOCKED.name())
                        .addTimestamp("createdAt", now)
                        .addTimestamp("updatedAt", now),
                this::mapUser
        );
    }

    private User mapUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getObject("id", UUID.class),
                rs.getString("phone"),
                UserStatus.valueOf(rs.getString("status")),
                getTimestamp("created_at", rs),
                getTimestamp("updated_at", rs)
        );
    }
}