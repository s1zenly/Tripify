package com.tripify.hotels.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.HotelTermsPlacement;
import com.tripify.hotels.service.repository.contract.HotelTermsPlacementRepository;
import com.tripify.hotels.service.utils.ResultSetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HotelTermsPlacementRepositoryImpl implements HotelTermsPlacementRepository {

    private static final String TERMS_COLUMNS = """
            hotel_id, check_in_after_time, check_in_before_time,
            check_out_after_time, check_out_before_time, timezone,
            cancellation, refund_prepayment, smoking, pet_friendly, party_friendly,
            age_restriction, additional_info, created_at, updated_at
            """;

    private static final String FIND_BY_HOTEL_ID_QUERY =
            "select " + TERMS_COLUMNS + " from hotel_terms_placement where hotel_id = :hotelId";

    private static final String UPSERT_QUERY =
            """
            insert into hotel_terms_placement (
                hotel_id, check_in_after_time, check_in_before_time,
                check_out_after_time, check_out_before_time, timezone,
                cancellation, refund_prepayment, smoking, pet_friendly, party_friendly,
                age_restriction, additional_info, created_at, updated_at
            )
            values (
                :hotelId, :checkInAfterTime, :checkInBeforeTime,
                :checkOutAfterTime, :checkOutBeforeTime, :timezone,
                :cancellation, :refundPrepayment, :smoking, :petFriendly, :partyFriendly,
                :ageRestriction, :additionalInfo, :createdAt, :updatedAt
            )
            on conflict (hotel_id) do update set
                check_in_after_time = excluded.check_in_after_time,
                check_in_before_time = excluded.check_in_before_time,
                check_out_after_time = excluded.check_out_after_time,
                check_out_before_time = excluded.check_out_before_time,
                timezone = excluded.timezone,
                cancellation = excluded.cancellation,
                refund_prepayment = excluded.refund_prepayment,
                smoking = excluded.smoking,
                pet_friendly = excluded.pet_friendly,
                party_friendly = excluded.party_friendly,
                age_restriction = excluded.age_restriction,
                additional_info = excluded.additional_info,
                updated_at = excluded.updated_at
            returning %s
            """.formatted(TERMS_COLUMNS);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<HotelTermsPlacement> findByHotelId(UUID hotelId) {
        return jdbcTemplate.query(
                FIND_BY_HOTEL_ID_QUERY,
                new SqlParams().addValue("hotelId", hotelId),
                this::mapTermsPlacement
        ).stream().findFirst();
    }

    @Override
    public HotelTermsPlacement upsert(HotelTermsPlacement termsPlacement) {
        return jdbcTemplate.query(
                UPSERT_QUERY,
                new SqlParams()
                        .addValue("hotelId", termsPlacement.hotelId())
                        .addValue("checkInAfterTime", termsPlacement.checkInAfterTime())
                        .addValue("checkInBeforeTime", termsPlacement.checkInBeforeTime())
                        .addValue("checkOutAfterTime", termsPlacement.checkOutAfterTime())
                        .addValue("checkOutBeforeTime", termsPlacement.checkOutBeforeTime())
                        .addValue("timezone", termsPlacement.timezone())
                        .addValue("cancellation", termsPlacement.cancellation())
                        .addValue("refundPrepayment", termsPlacement.refundPrepayment())
                        .addValue("smoking", termsPlacement.smoking())
                        .addValue("petFriendly", termsPlacement.petFriendly())
                        .addValue("partyFriendly", termsPlacement.partyFriendly())
                        .addValue("ageRestriction", termsPlacement.ageRestriction())
                        .addValue("additionalInfo", termsPlacement.additionalInfo())
                        .addTimestamp("createdAt", termsPlacement.createdAt())
                        .addTimestamp("updatedAt", termsPlacement.updatedAt()),
                this::mapTermsPlacement
        ).getFirst();
    }

    private HotelTermsPlacement mapTermsPlacement(ResultSet rs, int rowNum) throws SQLException {
        return new HotelTermsPlacement(
                rs.getObject("hotel_id", UUID.class),
                rs.getString("check_in_after_time"),
                rs.getString("check_in_before_time"),
                rs.getString("check_out_after_time"),
                rs.getString("check_out_before_time"),
                rs.getString("timezone"),
                rs.getBoolean("cancellation"),
                (Boolean) rs.getObject("refund_prepayment"),
                rs.getBoolean("smoking"),
                rs.getBoolean("pet_friendly"),
                rs.getBoolean("party_friendly"),
                (Integer) rs.getObject("age_restriction"),
                rs.getString("additional_info"),
                ResultSetUtils.getTimestamp("created_at", rs),
                ResultSetUtils.getTimestamp("updated_at", rs)
        );
    }
}
