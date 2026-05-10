package com.tripify.auth.service.repository;

import com.tripify.auth.service.domain.model.RefreshToken;
import com.tripify.auth.service.infra.SqlParams;
import com.tripify.auth.service.repository.contracts.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private static final String SAVE_REFRESH_TOKEN_QUERY =
            """
            insert into refresh_tokens(id, user_id, token_hash, revoked, expires_at, created_at)
            values(:id, :userId, :tokenHash, :revoked, :expiresAt, :createdAt)
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
                        .addTimestamp("expiresAt", refreshToken.expiresAt())
                        .addTimestamp("createdAt", refreshToken.createdAt())
        );
    }
}
