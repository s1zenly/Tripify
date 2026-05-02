package com.tripify.hotels.service.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.HotelNearbyPlace;
import com.tripify.hotels.service.repository.contract.HotelNearbyPlaceRepository;
import com.tripify.hotels.service.utils.ResultSetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotelNearbyPlaceRepositoryImpl implements HotelNearbyPlaceRepository {

    private static final String FIND_BY_HOTEL_ID_QUERY =
            """
            select id, hotel_id, category, title, distance_value, distance_unit, created_at
            from hotel_nearby_places
            where hotel_id = :hotelId
            order by category, title
            """;

    private static final String DELETE_BY_HOTEL_ID_QUERY =
            "delete from hotel_nearby_places where hotel_id = :hotelId";

    private static final String INSERT_QUERY =
            """
            insert into hotel_nearby_places (
                id, hotel_id, category, title, distance_value, distance_unit, created_at
            )
            values (
                :id, :hotelId, :category, :title, :distanceValue, :distanceUnit, :createdAt
            )
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<HotelNearbyPlace> findByHotelId(UUID hotelId) {
        return jdbcTemplate.query(
                FIND_BY_HOTEL_ID_QUERY,
                new SqlParams().addValue("hotelId", hotelId),
                this::mapNearbyPlace
        );
    }

    @Override
    @Transactional
    public void replaceAll(UUID hotelId, List<HotelNearbyPlace> nearbyPlaces) {
        jdbcTemplate.update(DELETE_BY_HOTEL_ID_QUERY, new SqlParams().addValue("hotelId", hotelId));

        if (nearbyPlaces == null || nearbyPlaces.isEmpty()) {
            return;
        }

        Instant now = Instant.now();
        SqlParams[] batch = nearbyPlaces.stream()
                .map(place -> new SqlParams()
                        .addValue("id", place.id() != null ? place.id() : UUID.randomUUID())
                        .addValue("hotelId", hotelId)
                        .addValue("category", place.category())
                        .addValue("title", place.title())
                        .addValue("distanceValue", place.distanceValue())
                        .addValue("distanceUnit", place.distanceUnit())
                        .addTimestamp("createdAt", place.createdAt() != null ? place.createdAt() : now))
                .toArray(SqlParams[]::new);

        jdbcTemplate.batchUpdate(INSERT_QUERY, batch);
    }

    private HotelNearbyPlace mapNearbyPlace(ResultSet rs, int rowNum) throws SQLException {
        return new HotelNearbyPlace(
                rs.getObject("id", UUID.class),
                rs.getObject("hotel_id", UUID.class),
                rs.getString("category"),
                rs.getString("title"),
                rs.getBigDecimal("distance_value"),
                rs.getString("distance_unit"),
                ResultSetUtils.getTimestamp("created_at", rs)
        );
    }
}
