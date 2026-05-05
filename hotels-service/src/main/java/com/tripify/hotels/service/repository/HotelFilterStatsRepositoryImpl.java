package com.tripify.hotels.service.repository;

import java.util.HashMap;
import java.util.Map;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.filter.HotelAttributeRule;
import com.tripify.hotels.service.model.filter.HotelFilterCatalog;
import com.tripify.hotels.service.model.filter.HotelFilterDefinition;
import com.tripify.hotels.service.model.filter.HotelFilterType;
import com.tripify.hotels.service.repository.contract.HotelFilterStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HotelFilterStatsRepositoryImpl implements HotelFilterStatsRepository {

    private static final String COUNT_FACETS_QUERY =
            """
            select sf.facet as filter_key, count(distinct h.id) as hotel_count
            from hotels h
            join hotel_search_facets sf on sf.hotel_id = h.id
            where lower(h.country) = lower(:country)
              and lower(h.city) = lower(:city)
            group by sf.facet
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Integer> countFacetUsage(String country, String city) {
        Map<String, Integer> result = new HashMap<>();
        jdbcTemplate.query(
                COUNT_FACETS_QUERY,
                baseParams(country, city),
                (rs, rowNum) -> {
                    result.put(rs.getString("filter_key"), rs.getInt("hotel_count"));
                    return null;
                }
        );
        return result;
    }

    @Override
    public int countFreeCancellation(String country, String city) {
        return countFacetUsage(country, city)
                .getOrDefault(HotelFilterCatalog.FREE_CANCELLATION, 0);
    }

    @Override
    public int countAttributeFilter(String country, String city, HotelFilterDefinition definition) {
        if (definition.type() != HotelFilterType.ATTRIBUTE || definition.attributeRule() == null) {
            return 0;
        }

        HotelAttributeRule rule = definition.attributeRule();
        StringBuilder sql = new StringBuilder("""
                select count(*)
                from hotels h
                where lower(h.country) = lower(:country)
                  and lower(h.city) = lower(:city)
                """);

        SqlParams params = baseParams(country, city);

        if (rule.minHotelClass() != null) {
            sql.append(" and h.hotel_class >= :minHotelClass");
            params.addValue("minHotelClass", rule.minHotelClass());
        }

        if (rule.maxHotelClass() != null) {
            sql.append(" and h.hotel_class <= :maxHotelClass");
            params.addValue("maxHotelClass", rule.maxHotelClass());
        }

        if (rule.minReviewsRating() != null) {
            sql.append(" and h.reviews_rating >= :minReviewsRating");
            params.addValue("minReviewsRating", rule.minReviewsRating());
        }

        Integer count = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);
        return count != null ? count : 0;
    }

    private static SqlParams baseParams(String country, String city) {
        return new SqlParams()
                .addValue("country", country)
                .addValue("city", city);
    }
}
