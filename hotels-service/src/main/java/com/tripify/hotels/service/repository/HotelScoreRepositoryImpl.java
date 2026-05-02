package com.tripify.hotels.service.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.HotelScore;
import com.tripify.hotels.service.repository.contract.HotelScoreRepository;
import com.tripify.hotels.service.utils.ResultSetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HotelScoreRepositoryImpl implements HotelScoreRepository {

    private static final String SCORE_COLUMNS = """
            hotel_id, final_score, price_score, rating_score, location_score, facilities_score,
            created_at, updated_at
            """;

    private static final String FIND_BY_HOTEL_ID_QUERY =
            "select " + SCORE_COLUMNS + " from hotel_scores where hotel_id = :hotelId";

    private static final String UPSERT_QUERY =
            """
            insert into hotel_scores (
                hotel_id, final_score, price_score, rating_score, location_score, facilities_score,
                created_at, updated_at
            )
            values (
                :hotelId, :finalScore, :priceScore, :ratingScore, :locationScore, :facilitiesScore,
                :createdAt, :updatedAt
            )
            on conflict (hotel_id) do update set
                final_score = excluded.final_score,
                price_score = excluded.price_score,
                rating_score = excluded.rating_score,
                location_score = excluded.location_score,
                facilities_score = excluded.facilities_score,
                updated_at = excluded.updated_at
            returning %s
            """.formatted(SCORE_COLUMNS);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<HotelScore> findByHotelId(UUID hotelId) {
        return jdbcTemplate.query(
                FIND_BY_HOTEL_ID_QUERY,
                new SqlParams().addValue("hotelId", hotelId),
                this::mapScore
        ).stream().findFirst();
    }

    @Override
    public HotelScore upsert(HotelScore hotelScore) {
        return jdbcTemplate.query(
                UPSERT_QUERY,
                new SqlParams()
                        .addValue("hotelId", hotelScore.hotelId())
                        .addValue("finalScore", hotelScore.finalScore())
                        .addValue("priceScore", hotelScore.priceScore())
                        .addValue("ratingScore", hotelScore.ratingScore())
                        .addValue("locationScore", hotelScore.locationScore())
                        .addValue("facilitiesScore", hotelScore.facilitiesScore())
                        .addTimestamp("createdAt", hotelScore.createdAt())
                        .addTimestamp("updatedAt", hotelScore.updatedAt()),
                this::mapScore
        ).getFirst();
    }

    private HotelScore mapScore(ResultSet rs, int rowNum) throws SQLException {
        return new HotelScore(
                rs.getObject("hotel_id", UUID.class),
                rs.getBigDecimal("final_score"),
                rs.getBigDecimal("price_score"),
                rs.getBigDecimal("rating_score"),
                rs.getBigDecimal("location_score"),
                rs.getBigDecimal("facilities_score"),
                ResultSetUtils.getTimestamp("created_at", rs),
                ResultSetUtils.getTimestamp("updated_at", rs)
        );
    }
}
