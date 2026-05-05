package com.tripify.hotels.service.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.Hotel;
import com.tripify.hotels.service.model.HotelSearchFilter;
import com.tripify.hotels.service.model.filter.HotelAttributeRule;
import com.tripify.hotels.service.model.filter.HotelFilterCatalog;
import com.tripify.hotels.service.model.filter.HotelFilterDefinition;
import com.tripify.hotels.service.model.HotelSearchPage;
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
            address, city, country, currency, price, max_guests, hotel_class,
            latitude, longitude, reviews_total, reviews_rating,
            parsed_at, provided_at, created_at, updated_at
            """;

    private static final String FIND_BY_ID_QUERY =
            "select " + HOTEL_COLUMNS + " from hotels where id = :id";

    private static final String SEARCH_FROM_WHERE =
            """
            from hotels h
            where lower(h.country) = lower(:country)
              and lower(h.city) = lower(:city)
            """;

    private static final String UPSERT_QUERY =
            """
            insert into hotels (
                id, external_hotel_id, provider_name, title, external_link, description,
                address, city, country, currency, price, max_guests, hotel_class,
                latitude, longitude, reviews_total, reviews_rating,
                parsed_at, provided_at, created_at, updated_at
            )
            values (
                :id, :externalHotelId, :providerName, :title, :externalLink, :description,
                :address, :city, :country, :currency, :price, :maxGuests, :hotelClass,
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
                max_guests = excluded.max_guests,
                hotel_class = excluded.hotel_class,
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
    public HotelSearchPage search(HotelSearchFilter filter) {
        int fetchLimit = filter.limit() + 1;
        SearchQuery searchQuery = buildSearchQuery(filter, fetchLimit);
        List<Hotel> hotels = jdbcTemplate.query(
                searchQuery.sql(),
                searchQuery.params(),
                this::mapHotel
        );

        if (hotels.size() <= filter.limit()) {
            return new HotelSearchPage(hotels, null);
        }

        List<Hotel> page = hotels.subList(0, filter.limit());
        return new HotelSearchPage(page, page.getLast().id());
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
                        .addValue("maxGuests", hotel.maxGuests())
                        .addValue("hotelClass", hotel.hotelClass())
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

    private SearchQuery buildSearchQuery(HotelSearchFilter filter, int fetchLimit) {
        StringBuilder sql = new StringBuilder("select ")
                .append(HOTEL_COLUMNS)
                .append(SEARCH_FROM_WHERE);

        SqlParams params = new SqlParams()
                .addValue("country", filter.country())
                .addValue("city", filter.city());

        if (filter.minGuests() != null) {
            sql.append(" and h.max_guests >= :minGuests");
            params.addValue("minGuests", filter.minGuests());
        }

        if (filter.maxPricePerNightUsd() != null) {
            sql.append(" and h.price <= :maxPricePerNightUsd");
            params.addValue("maxPricePerNightUsd", filter.maxPricePerNightUsd());
        }

        if (filter.resolvedFilters() != null) {
            if (filter.resolvedFilters().hasFacets()) {
                sql.append("""
                         and (
                             select count(distinct sf.facet)
                             from hotel_search_facets sf
                             where sf.hotel_id = h.id
                               and sf.facet in (:facets)
                         ) = :facetsCount
                        """);
                params.addValue("facets", filter.resolvedFilters().facets());
                params.addValue("facetsCount", filter.resolvedFilters().facets().size());
            }

            appendTermsFilters(sql, filter.resolvedFilters().termsFilterIds());
            appendAttributeFilters(sql, params, filter.resolvedFilters().attributeFilterIds());
        }

        if (filter.lastId() != null) {
            sql.append(" and h.id > :lastId");
            params.addValue("lastId", filter.lastId());
        }
        sql.append(" order by h.id asc limit :limit");

        params.addValue("limit", fetchLimit);

        return new SearchQuery(sql.toString(), params);
    }

    private static void appendTermsFilters(StringBuilder sql, List<String> termsFilterIds) {
        for (String termsFilterId : termsFilterIds) {
            throw new IllegalArgumentException("Unsupported terms filter: " + termsFilterId);
        }
    }

    private static void appendAttributeFilters(StringBuilder sql, SqlParams params, List<String> attributeFilterIds) {
        for (String attributeFilterId : attributeFilterIds) {
            HotelFilterDefinition definition = HotelFilterCatalog.find(attributeFilterId)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown attribute filter: " + attributeFilterId));

            if (definition.attributeRule() == null) {
                throw new IllegalArgumentException("Attribute rule is missing for filter: " + attributeFilterId);
            }

            HotelAttributeRule rule = definition.attributeRule();

            if (rule.minHotelClass() != null) {
                sql.append(" and h.hotel_class >= :").append(attributeFilterId).append("_minClass");
                params.addValue(attributeFilterId + "_minClass", rule.minHotelClass());
            }

            if (rule.maxHotelClass() != null) {
                sql.append(" and h.hotel_class <= :").append(attributeFilterId).append("_maxClass");
                params.addValue(attributeFilterId + "_maxClass", rule.maxHotelClass());
            }

            if (rule.minReviewsRating() != null) {
                sql.append(" and h.reviews_rating >= :").append(attributeFilterId).append("_minRating");
                params.addValue(attributeFilterId + "_minRating", rule.minReviewsRating());
            }
        }
    }

    private record SearchQuery(String sql, SqlParams params) {
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
                rs.getInt("max_guests"),
                rs.getInt("hotel_class"),
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
