package com.tripify.hotels.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.HotelTag;
import com.tripify.hotels.service.repository.contract.HotelTagRepository;
import com.tripify.hotels.service.utils.ResultSetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotelTagRepositoryImpl implements HotelTagRepository {

    private static final String FIND_BY_HOTEL_IDS_QUERY =
            """
            select hotel_id, tag, created_at
            from hotel_tags
            where hotel_id in (:hotelIds)
            order by hotel_id, tag
            """;

    private static final String FIND_BY_HOTEL_ID_QUERY =
            """
            select hotel_id, tag, created_at
            from hotel_tags
            where hotel_id = :hotelId
            order by tag
            """;

    private static final String DELETE_BY_HOTEL_ID_QUERY =
            "delete from hotel_tags where hotel_id = :hotelId";

    private static final String INSERT_QUERY =
            """
            insert into hotel_tags (hotel_id, tag, created_at)
            values (:hotelId, :tag, :createdAt)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Map<UUID, List<String>> findTagsByHotelIds(List<UUID> hotelIds) {
        if (hotelIds == null || hotelIds.isEmpty()) {
            return Map.of();
        }

        Map<UUID, List<String>> result = new HashMap<>();
        jdbcTemplate.query(
                FIND_BY_HOTEL_IDS_QUERY,
                new SqlParams().addValue("hotelIds", hotelIds),
                (rs, rowNum) -> {
                    UUID hotelId = rs.getObject("hotel_id", UUID.class);
                    result.computeIfAbsent(hotelId, ignored -> new ArrayList<>()).add(rs.getString("tag"));
                    return null;
                }
        );

        return result;
    }

    @Override
    public List<HotelTag> findByHotelId(UUID hotelId) {
        return jdbcTemplate.query(
                FIND_BY_HOTEL_ID_QUERY,
                new SqlParams().addValue("hotelId", hotelId),
                this::mapTag
        );
    }

    @Override
    @Transactional
    public void replaceAll(UUID hotelId, List<HotelTag> tags) {
        jdbcTemplate.update(DELETE_BY_HOTEL_ID_QUERY, new SqlParams().addValue("hotelId", hotelId));

        if (tags == null || tags.isEmpty()) {
            return;
        }

        Instant now = Instant.now();
        SqlParams[] batch = tags.stream()
                .map(tag -> new SqlParams()
                        .addValue("hotelId", hotelId)
                        .addValue("tag", tag.tag())
                        .addTimestamp("createdAt", tag.createdAt() != null ? tag.createdAt() : now))
                .toArray(SqlParams[]::new);

        jdbcTemplate.batchUpdate(INSERT_QUERY, batch);
    }

    private HotelTag mapTag(ResultSet rs, int rowNum) throws SQLException {
        return new HotelTag(
                rs.getObject("hotel_id", UUID.class),
                rs.getString("tag"),
                ResultSetUtils.getTimestamp("created_at", rs)
        );
    }
}
