package com.tripify.hotels.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.HotelRefundCondition;
import com.tripify.hotels.service.repository.contract.HotelRefundConditionRepository;
import com.tripify.hotels.service.utils.ResultSetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotelRefundConditionRepositoryImpl implements HotelRefundConditionRepository {

    private static final String FIND_BY_HOTEL_ID_QUERY =
            """
            select id, hotel_id, quantity_percent, condition_description, created_at
            from hotel_refund_conditions
            where hotel_id = :hotelId
            order by quantity_percent desc
            """;

    private static final String DELETE_BY_HOTEL_ID_QUERY =
            "delete from hotel_refund_conditions where hotel_id = :hotelId";

    private static final String INSERT_QUERY =
            """
            insert into hotel_refund_conditions (
                id, hotel_id, quantity_percent, condition_description, created_at
            )
            values (
                :id, :hotelId, :quantityPercent, :conditionDescription, :createdAt
            )
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<HotelRefundCondition> findByHotelId(UUID hotelId) {
        return jdbcTemplate.query(
                FIND_BY_HOTEL_ID_QUERY,
                new SqlParams().addValue("hotelId", hotelId),
                this::mapRefundCondition
        );
    }

    @Override
    @Transactional
    public void replaceAll(UUID hotelId, List<HotelRefundCondition> refundConditions) {
        jdbcTemplate.update(DELETE_BY_HOTEL_ID_QUERY, new SqlParams().addValue("hotelId", hotelId));

        if (refundConditions == null || refundConditions.isEmpty()) {
            return;
        }

        Instant now = Instant.now();
        SqlParams[] batch = refundConditions.stream()
                .map(condition -> new SqlParams()
                        .addValue("id", condition.id() != null ? condition.id() : UUID.randomUUID())
                        .addValue("hotelId", hotelId)
                        .addValue("quantityPercent", condition.quantityPercent())
                        .addValue("conditionDescription", condition.conditionDescription())
                        .addTimestamp("createdAt", condition.createdAt() != null ? condition.createdAt() : now))
                .toArray(SqlParams[]::new);

        jdbcTemplate.batchUpdate(INSERT_QUERY, batch);
    }

    private HotelRefundCondition mapRefundCondition(ResultSet rs, int rowNum) throws SQLException {
        return new HotelRefundCondition(
                rs.getObject("id", UUID.class),
                rs.getObject("hotel_id", UUID.class),
                rs.getInt("quantity_percent"),
                rs.getString("condition_description"),
                ResultSetUtils.getTimestamp("created_at", rs)
        );
    }
}
