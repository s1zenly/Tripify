package com.tripify.hotels.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.HotelFacility;
import com.tripify.hotels.service.repository.contract.HotelFacilityRepository;
import com.tripify.hotels.service.utils.ResultSetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotelFacilityRepositoryImpl implements HotelFacilityRepository {

    private static final String FIND_BY_HOTEL_ID_QUERY =
            """
            select hotel_id, facility_type, is_free, created_at
            from hotel_facilities
            where hotel_id = :hotelId
            order by facility_type
            """;

    private static final String DELETE_BY_HOTEL_ID_QUERY =
            "delete from hotel_facilities where hotel_id = :hotelId";

    private static final String INSERT_QUERY =
            """
            insert into hotel_facilities (hotel_id, facility_type, is_free, created_at)
            values (:hotelId, :facilityType, :isFree, :createdAt)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<HotelFacility> findByHotelId(UUID hotelId) {
        return jdbcTemplate.query(
                FIND_BY_HOTEL_ID_QUERY,
                new SqlParams().addValue("hotelId", hotelId),
                this::mapFacility
        );
    }

    @Override
    @Transactional
    public void replaceAll(UUID hotelId, List<HotelFacility> facilities) {
        jdbcTemplate.update(DELETE_BY_HOTEL_ID_QUERY, new SqlParams().addValue("hotelId", hotelId));

        if (facilities == null || facilities.isEmpty()) {
            return;
        }

        Instant now = Instant.now();
        SqlParams[] batch = facilities.stream()
                .map(facility -> new SqlParams()
                        .addValue("hotelId", hotelId)
                        .addValue("facilityType", facility.facilityType())
                        .addValue("isFree", facility.isFree())
                        .addTimestamp("createdAt", facility.createdAt() != null ? facility.createdAt() : now))
                .toArray(SqlParams[]::new);

        jdbcTemplate.batchUpdate(INSERT_QUERY, batch);
    }

    private HotelFacility mapFacility(ResultSet rs, int rowNum) throws SQLException {
        return new HotelFacility(
                rs.getObject("hotel_id", UUID.class),
                rs.getString("facility_type"),
                rs.getBoolean("is_free"),
                ResultSetUtils.getTimestamp("created_at", rs)
        );
    }
}
