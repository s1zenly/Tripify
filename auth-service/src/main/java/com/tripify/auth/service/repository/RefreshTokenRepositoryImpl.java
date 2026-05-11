package com.tripify.auth.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.tripify.auth.service.domain.model.RefreshToken;
import com.tripify.auth.service.infra.SqlParams;
import com.tripify.auth.service.repository.contracts.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.tripify.auth.service.utils.ResultSetUtils.getTimestamp;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private static final String SAVE_REFRESH_TOKEN_QUERY =
            """
            insert into refresh_tokens(id, user_id, token_hash, revoked, revoked_at, expires_at, created_at)
            values(:id, :userId, :tokenHash, :revoked, :revokedAt, :expiresAt, :createdAt)
            """;

    private static final String FIND_BY_TOKEN_HASH_QUERY =
            """
            select id, user_id, token_hash, revoked, revoked_at, expires_at, created_at
            from refresh_tokens
            where token_hash = :tokenHash
            """;

    private static final String REVOKE_OLD_REFRESH_TOKEN =
            """
            update refresh_tokens
            set revoked = true,
                revoked_at = :revokedAt
            where id = :id
              and revoked = false
            """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void save(RefreshToken refreshToken) {
        namedParameterJdbcTemplate.update(
                SAVE_REFRESH_TOKEN_QUERY,
                new SqlParams()
                        .addValue("id", refreshToken.id())
                        .addValue("userId", refreshToken.userId())
                        .addValue("tokenHash", refreshToken.tokenHash())
                        .addValue("revoked", refreshToken.revoked())
                        .addTimestamp("revokedAt", refreshToken.revokedAt())
                        .addTimestamp("expiresAt", refreshToken.expiresAt())
                        .addTimestamp("createdAt", refreshToken.createdAt())
        );
    }

    @Override
    public Optional<RefreshToken> findByTokenHash(String tokenHash) {
        return namedParameterJdbcTemplate.query(
                FIND_BY_TOKEN_HASH_QUERY,
                new SqlParams()
                        .addValue("tokenHash", tokenHash),
                this::mapRefreshToken
        ).stream().findFirst();
    }

    @Override
    public int revoke(UUID id, Instant now) {
        return namedParameterJdbcTemplate.update(
                REVOKE_OLD_REFRESH_TOKEN,
                new SqlParams()
                        .addValue("id", id)
                        .addTimestamp("revokedAt", now)
        );
    }

    private RefreshToken mapRefreshToken(ResultSet rs, int rowNum) throws SQLException {
        return new RefreshToken(
                rs.getObject("id", UUID.class),
                rs.getObject("user_id", UUID.class),
                rs.getString("token_hash"),
                rs.getBoolean("revoked"),
                getTimestamp("revoked_at", rs),
                getTimestamp("expires_at", rs),
                getTimestamp("created_at", rs)
        );
    }
}
