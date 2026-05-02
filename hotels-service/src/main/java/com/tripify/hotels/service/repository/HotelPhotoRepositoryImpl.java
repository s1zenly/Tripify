package com.tripify.hotels.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.HotelPhoto;
import com.tripify.hotels.service.repository.contract.HotelPhotoRepository;
import com.tripify.hotels.service.utils.ResultSetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotelPhotoRepositoryImpl implements HotelPhotoRepository {

    private static final String FIND_BY_HOTEL_ID_QUERY =
            """
            select id, hotel_id, s3_key, sort_order, description, created_at
            from hotel_photos
            where hotel_id = :hotelId
            order by sort_order, created_at
            """;

    private static final String DELETE_BY_HOTEL_ID_QUERY =
            "delete from hotel_photos where hotel_id = :hotelId";

    private static final String INSERT_QUERY =
            """
            insert into hotel_photos (id, hotel_id, s3_key, sort_order, description, created_at)
            values (:id, :hotelId, :s3Key, :sortOrder, :description, :createdAt)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<HotelPhoto> findByHotelId(UUID hotelId) {
        return jdbcTemplate.query(
                FIND_BY_HOTEL_ID_QUERY,
                new SqlParams().addValue("hotelId", hotelId),
                this::mapPhoto
        );
    }

    @Override
    @Transactional
    public void replaceAll(UUID hotelId, List<HotelPhoto> photos) {
        jdbcTemplate.update(DELETE_BY_HOTEL_ID_QUERY, new SqlParams().addValue("hotelId", hotelId));

        if (photos == null || photos.isEmpty()) {
            return;
        }

        Instant now = Instant.now();
        SqlParams[] batch = photos.stream()
                .map(photo -> new SqlParams()
                        .addValue("id", photo.id() != null ? photo.id() : UUID.randomUUID())
                        .addValue("hotelId", hotelId)
                        .addValue("s3Key", photo.s3Key())
                        .addValue("sortOrder", photo.sortOrder() != null ? photo.sortOrder() : 0)
                        .addValue("description", photo.description())
                        .addTimestamp("createdAt", photo.createdAt() != null ? photo.createdAt() : now))
                .toArray(SqlParams[]::new);

        jdbcTemplate.batchUpdate(INSERT_QUERY, batch);
    }

    private HotelPhoto mapPhoto(ResultSet rs, int rowNum) throws SQLException {
        return new HotelPhoto(
                rs.getObject("id", UUID.class),
                rs.getObject("hotel_id", UUID.class),
                rs.getString("s3_key"),
                rs.getInt("sort_order"),
                rs.getString("description"),
                ResultSetUtils.getTimestamp("created_at", rs)
        );
    }
}
