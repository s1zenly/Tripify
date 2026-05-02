package com.tripify.hotels.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.HotelPaymentMethods;
import com.tripify.hotels.service.repository.contract.HotelPaymentMethodsRepository;
import com.tripify.hotels.service.utils.JdbcArrayUtils;
import com.tripify.hotels.service.utils.ResultSetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HotelPaymentMethodsRepositoryImpl implements HotelPaymentMethodsRepository {

    private static final String PAYMENT_COLUMNS = """
            hotel_id, is_cash, cash_currencies, is_card, card_types, created_at, updated_at
            """;

    private static final String FIND_BY_HOTEL_ID_QUERY =
            "select " + PAYMENT_COLUMNS + " from hotel_payment_methods where hotel_id = :hotelId";

    private static final String UPSERT_QUERY =
            """
            insert into hotel_payment_methods (
                hotel_id, is_cash, cash_currencies, is_card, card_types, created_at, updated_at
            )
            values (
                :hotelId, :isCash, :cashCurrencies, :isCard, :cardTypes, :createdAt, :updatedAt
            )
            on conflict (hotel_id) do update set
                is_cash = excluded.is_cash,
                cash_currencies = excluded.cash_currencies,
                is_card = excluded.is_card,
                card_types = excluded.card_types,
                updated_at = excluded.updated_at
            returning %s
            """.formatted(PAYMENT_COLUMNS);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<HotelPaymentMethods> findByHotelId(UUID hotelId) {
        return jdbcTemplate.query(
                FIND_BY_HOTEL_ID_QUERY,
                new SqlParams().addValue("hotelId", hotelId),
                this::mapPaymentMethods
        ).stream().findFirst();
    }

    @Override
    public HotelPaymentMethods upsert(HotelPaymentMethods paymentMethods) {
        return jdbcTemplate.query(
                UPSERT_QUERY,
                new SqlParams()
                        .addValue("hotelId", paymentMethods.hotelId())
                        .addValue("isCash", paymentMethods.isCash())
                        .addValue(
                                "cashCurrencies",
                                toSqlArray(paymentMethods.cashCurrencies()),
                                Types.ARRAY
                        )
                        .addValue("isCard", paymentMethods.isCard())
                        .addValue(
                                "cardTypes",
                                toSqlArray(paymentMethods.cardTypes()),
                                Types.ARRAY
                        )
                        .addTimestamp("createdAt", paymentMethods.createdAt())
                        .addTimestamp("updatedAt", paymentMethods.updatedAt()),
                this::mapPaymentMethods
        ).getFirst();
    }

    private HotelPaymentMethods mapPaymentMethods(ResultSet rs, int rowNum) throws SQLException {
        return new HotelPaymentMethods(
                rs.getObject("hotel_id", UUID.class),
                rs.getBoolean("is_cash"),
                JdbcArrayUtils.getStringList("cash_currencies", rs),
                rs.getBoolean("is_card"),
                JdbcArrayUtils.getStringList("card_types", rs),
                ResultSetUtils.getTimestamp("created_at", rs),
                ResultSetUtils.getTimestamp("updated_at", rs)
        );
    }

    private static String[] toSqlArray(List<String> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }

        return values.toArray(String[]::new);
    }
}
