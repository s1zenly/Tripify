package com.tripify.hotels.service.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.tripify.hotels.service.infra.SqlParams;
import com.tripify.hotels.service.model.HotelSearchFacet;
import com.tripify.hotels.service.repository.contract.HotelSearchFacetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class HotelSearchFacetRepositoryImpl implements HotelSearchFacetRepository {

    private static final String DELETE_BY_HOTEL_ID_QUERY =
            "delete from hotel_search_facets where hotel_id = :hotelId";

    private static final String INSERT_QUERY =
            """
            insert into hotel_search_facets (hotel_id, facet, created_at)
            values (:hotelId, :facet, :createdAt)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void replaceAll(UUID hotelId, List<HotelSearchFacet> facets) {
        jdbcTemplate.update(DELETE_BY_HOTEL_ID_QUERY, new SqlParams().addValue("hotelId", hotelId));

        if (facets == null || facets.isEmpty()) {
            return;
        }

        Instant now = Instant.now();
        SqlParams[] batch = facets.stream()
                .map(facet -> new SqlParams()
                        .addValue("hotelId", hotelId)
                        .addValue("facet", facet.facet())
                        .addTimestamp("createdAt", facet.createdAt() != null ? facet.createdAt() : now))
                .toArray(SqlParams[]::new);

        jdbcTemplate.batchUpdate(INSERT_QUERY, batch);
    }
}
