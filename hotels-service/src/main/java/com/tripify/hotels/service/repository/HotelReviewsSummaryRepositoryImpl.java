package com.tripify.hotels.service.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.HotelReviewsSummary;
import com.tripify.hotels.service.repository.contract.HotelReviewsSummaryRepository;
import com.tripify.hotels.service.utils.ResultSetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HotelReviewsSummaryRepositoryImpl implements HotelReviewsSummaryRepository {

    private static final String SUMMARY_COLUMNS = """
            hotel_id, rating, reviews_total,
            cleanliness, service, price_quality, room, location,
            created_at, updated_at
            """;

    private static final String FIND_BY_HOTEL_ID_QUERY =
            "select " + SUMMARY_COLUMNS + " from hotel_reviews_summary where hotel_id = :hotelId";

    private static final String FIND_BY_HOTEL_IDS_QUERY =
            "select " + SUMMARY_COLUMNS + " from hotel_reviews_summary where hotel_id in (:hotelIds)";

    private static final String UPSERT_QUERY =
            """
            insert into hotel_reviews_summary (
                hotel_id, rating, reviews_total,
                cleanliness, service, price_quality, room, location,
                created_at, updated_at
            )
            values (
                :hotelId, :rating, :reviewsTotal,
                :cleanliness, :service, :priceQuality, :room, :location,
                :createdAt, :updatedAt
            )
            on conflict (hotel_id) do update set
                rating = excluded.rating,
                reviews_total = excluded.reviews_total,
                cleanliness = excluded.cleanliness,
                service = excluded.service,
                price_quality = excluded.price_quality,
                room = excluded.room,
                location = excluded.location,
                updated_at = excluded.updated_at
            returning %s
            """.formatted(SUMMARY_COLUMNS);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<HotelReviewsSummary> findByHotelId(UUID hotelId) {
        return jdbcTemplate.query(
                FIND_BY_HOTEL_ID_QUERY,
                new SqlParams().addValue("hotelId", hotelId),
                this::mapReviewsSummary
        ).stream().findFirst();
    }

    @Override
    public Map<UUID, HotelReviewsSummary> findByHotelIds(List<UUID> hotelIds) {
        if (hotelIds == null || hotelIds.isEmpty()) {
            return Map.of();
        }

        Map<UUID, HotelReviewsSummary> result = new HashMap<>();
        jdbcTemplate.query(
                FIND_BY_HOTEL_IDS_QUERY,
                new SqlParams().addValue("hotelIds", hotelIds),
                (rs, rowNum) -> {
                    HotelReviewsSummary summary = mapReviewsSummary(rs, rowNum);
                    result.put(summary.hotelId(), summary);
                    return summary;
                }
        );

        return result;
    }

    @Override
    public HotelReviewsSummary upsert(HotelReviewsSummary reviewsSummary) {
        return jdbcTemplate.query(
                UPSERT_QUERY,
                new SqlParams()
                        .addValue("hotelId", reviewsSummary.hotelId())
                        .addValue("rating", reviewsSummary.rating())
                        .addValue("reviewsTotal", reviewsSummary.reviewsTotal())
                        .addValue("cleanliness", reviewsSummary.cleanliness())
                        .addValue("service", reviewsSummary.service())
                        .addValue("priceQuality", reviewsSummary.priceQuality())
                        .addValue("room", reviewsSummary.room())
                        .addValue("location", reviewsSummary.location())
                        .addTimestamp("createdAt", reviewsSummary.createdAt())
                        .addTimestamp("updatedAt", reviewsSummary.updatedAt()),
                this::mapReviewsSummary
        ).getFirst();
    }

    private HotelReviewsSummary mapReviewsSummary(ResultSet rs, int rowNum) throws SQLException {
        return new HotelReviewsSummary(
                rs.getObject("hotel_id", UUID.class),
                rs.getBigDecimal("rating"),
                rs.getInt("reviews_total"),
                rs.getBigDecimal("cleanliness"),
                rs.getBigDecimal("service"),
                rs.getBigDecimal("price_quality"),
                rs.getBigDecimal("room"),
                rs.getBigDecimal("location"),
                ResultSetUtils.getTimestamp("created_at", rs),
                ResultSetUtils.getTimestamp("updated_at", rs)
        );
    }
}
