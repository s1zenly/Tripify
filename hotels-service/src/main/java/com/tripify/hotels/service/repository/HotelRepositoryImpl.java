package com.tripify.hotels.service.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.repository.contract.HotelRepository;
import com.tripify.hotels.service.utils.ResultSetUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HotelRepositoryImpl implements HotelRepository {

    private static final String HOTEL_COLUMNS = """
            id, external_hotel_id, provider_name, title, external_link, description,
            address, city, country, currency, price, hotel_class, reviews_mongo_id,
            latitude, longitude, reviews_total, reviews_rating,
            parsed_at, provided_at, created_at, updated_at
            """;

    private static final String FIND_BY_ID_QUERY =
            "select " + HOTEL_COLUMNS + " from hotels where id = :id";

    private static final String FIND_BY_PROVIDER_AND_EXTERNAL_ID_QUERY =
            """
            select %s
            from hotels
            where provider_name = :providerName
              and external_hotel_id = :externalHotelId
            """.formatted(HOTEL_COLUMNS);

    private static final String UPSERT_QUERY =
            """
            insert into hotels (
                id, external_hotel_id, provider_name, title, external_link, description,
                address, city, country, currency, price, hotel_class, reviews_mongo_id,
                latitude, longitude, reviews_total, reviews_rating,
                parsed_at, provided_at, created_at, updated_at
            )
            values (
                :id, :externalHotelId, :providerName, :title, :externalLink, :description,
                :address, :city, :country, :currency, :price, :hotelClass, :reviewsMongoId,
                :latitude, :longitude, :reviewsTotal, :reviewsRating,
                :parsedAt, :providedAt, :createdAt, :updatedAt
            )
            on conflict (id) do update set
                title = excluded.title,
                external_link = excluded.external_link,
                description = excluded.description,
                address = excluded.address,
                city = excluded.city,
                country = excluded.country,
                currency = excluded.currency,
                price = excluded.price,
                hotel_class = excluded.hotel_class,
                reviews_mongo_id = excluded.reviews_mongo_id,
                latitude = excluded.latitude,
                longitude = excluded.longitude,
                reviews_total = excluded.reviews_total,
                reviews_rating = excluded.reviews_rating,
                parsed_at = excluded.parsed_at,
                provided_at = excluded.provided_at,
                updated_at = excluded.updated_at
            returning %s
            """.formatted(HOTEL_COLUMNS);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Hotel> findById(UUID id) {
        return jdbcTemplate.query(
                FIND_BY_ID_QUERY,
                new SqlParams().addValue("id", id),
                this::mapHotel
        ).stream().findFirst();
    }

    @Override
    public Optional<Hotel> findByProviderAndExternalId(String providerName, Long externalHotelId) {
        return jdbcTemplate.query(
                FIND_BY_PROVIDER_AND_EXTERNAL_ID_QUERY,
                new SqlParams()
                        .addValue("providerName", providerName)
                        .addValue("externalHotelId", externalHotelId),
                this::mapHotel
        ).stream().findFirst();
    }

    @Override
    public Hotel upsert(Hotel hotel) {
        return jdbcTemplate.query(
                UPSERT_QUERY,
                new SqlParams()
                        .addValue("id", hotel.id())
                        .addValue("externalHotelId", hotel.externalHotelId())
                        .addValue("providerName", hotel.providerName())
                        .addValue("title", hotel.title())
                        .addValue("externalLink", hotel.externalLink())
                        .addValue("description", hotel.description())
                        .addValue("address", hotel.address())
                        .addValue("city", hotel.city())
                        .addValue("country", hotel.country())
                        .addValue("currency", hotel.currency())
                        .addValue("price", hotel.price())
                        .addValue("hotelClass", hotel.hotelClass())
                        .addValue("reviewsMongoId", hotel.reviewsMongoId())
                        .addValue("latitude", hotel.latitude())
                        .addValue("longitude", hotel.longitude())
                        .addValue("reviewsTotal", hotel.reviewsTotal())
                        .addValue("reviewsRating", hotel.reviewsRating())
                        .addTimestamp("parsedAt", hotel.parsedAt())
                        .addTimestamp("providedAt", hotel.providedAt())
                        .addTimestamp("createdAt", hotel.createdAt())
                        .addTimestamp("updatedAt", hotel.updatedAt()),
                this::mapHotel
        ).getFirst();
    }

    private Hotel mapHotel(ResultSet rs, int rowNum) throws SQLException {
        return new Hotel(
                rs.getObject("id", UUID.class),
                rs.getLong("external_hotel_id"),
                rs.getString("provider_name"),
                rs.getString("title"),
                rs.getString("external_link"),
                rs.getString("description"),
                rs.getString("address"),
                rs.getString("city"),
                rs.getString("country"),
                rs.getString("currency"),
                rs.getBigDecimal("price"),
                rs.getInt("hotel_class"),
                rs.getString("reviews_mongo_id"),
                rs.getBigDecimal("latitude"),
                rs.getBigDecimal("longitude"),
                rs.getInt("reviews_total"),
                rs.getBigDecimal("reviews_rating"),
                ResultSetUtils.getTimestamp("parsed_at", rs),
                ResultSetUtils.getTimestamp("provided_at", rs),
                ResultSetUtils.getTimestamp("created_at", rs),
                ResultSetUtils.getTimestamp("updated_at", rs)
        );
    }
}
