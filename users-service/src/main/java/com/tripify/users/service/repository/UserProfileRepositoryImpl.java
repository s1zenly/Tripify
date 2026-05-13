package com.tripify.users.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.tripify.users.service.infra.SqlParams;
import com.tripify.users.service.model.UpdateUserProfileRequest;
import com.tripify.users.service.model.UserProfile;
import com.tripify.users.service.model.UserRegisteredEvent;
import com.tripify.users.service.repository.contract.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.tripify.users.service.utils.ResultSetUtils.getTimestamp;

@Repository
@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileRepository {

    private static final String FIND_USER_PROFILE_BY_ID_QUERY =
            """
            select user_id, email, first_name, last_name, phone_number, city, country, currency, citizenship, created_at, updated_at
            from user_profiles
            where user_id = :userId
            """;

    private static final String CREATE_PROFILE_QUERY =
            """
            insert into user_profiles (user_id, phone_number, created_at, updated_at)
            values (:userId, :phoneNumber, :createdAt, :updatedAt)
            on conflict (user_id) do nothing
            """;

    private static final String UPDATE_PROFILE_QUERY =
            """
            update user_profiles
            set
                email       = coalesce(:email, email),
                first_name  = coalesce(:firstName, first_name),
                last_name   = coalesce(:lastName, last_name),
                city        = coalesce(:city, city),
                country     = coalesce(:country, country),
                currency    = coalesce(:currency, currency),
                citizenship = coalesce(:citizenship, citizenship),
                updated_at  = :updatedAt
            where user_id = :userId
            returning user_id, email, first_name, last_name, phone_number, city, country, currency, citizenship, created_at, updated_at
            """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<UserProfile> findById(UUID user_id) {
        return namedParameterJdbcTemplate.query(
                FIND_USER_PROFILE_BY_ID_QUERY,
                new SqlParams()
                        .addValue("userId", user_id),
                this::mapUserProfile
        ).stream().findAny();
    }

    @Override
    public void createProfile(UserRegisteredEvent userRegisteredEvent) {
        namedParameterJdbcTemplate.update(
                CREATE_PROFILE_QUERY,
                new SqlParams()
                        .addValue("userId", userRegisteredEvent.userId())
                        .addValue("phoneNumber", userRegisteredEvent.phoneNumber())
                        .addTimestamp("createdAt", userRegisteredEvent.registeredAt())
                        .addTimestamp("updatedAt", userRegisteredEvent.registeredAt())
        );
    }

    @Override
    public Optional<UserProfile> updateProfile(UpdateUserProfileRequest updateUserProfileRequest) {
        return namedParameterJdbcTemplate.query(
                UPDATE_PROFILE_QUERY,
                new SqlParams()
                        .addValue("userId", updateUserProfileRequest.userId())
                        .addValue("email", updateUserProfileRequest.email())
                        .addValue("firstName", updateUserProfileRequest.firstName())
                        .addValue("lastName", updateUserProfileRequest.lastName())
                        .addValue("city", updateUserProfileRequest.city())
                        .addValue("country", updateUserProfileRequest.country())
                        .addValue("currency", updateUserProfileRequest.currency())
                        .addValue("citizenship", updateUserProfileRequest.citizenship())
                        .addTimestamp("updatedAt", Instant.now()),
                this::mapUserProfile
        ).stream().findAny();
    }

    private UserProfile mapUserProfile(ResultSet rs, int rowNum) throws SQLException {
        return UserProfile.builder()
                .userId(rs.getObject("user_id", UUID.class))
                .email(rs.getString("email"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .phoneNumber(rs.getString("phone_number"))
                .city(rs.getString("city"))
                .country(rs.getString("country"))
                .currency(rs.getString("currency"))
                .citizenship(rs.getString("citizenship"))
                .createdAt(getTimestamp("created_at", rs))
                .updatedAt(getTimestamp("updated_at", rs))
                .build();
    }
}
