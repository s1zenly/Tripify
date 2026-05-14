package com.tripify.pack.persistence.mongo.repository;

import com.tripify.pack.persistence.mongo.document.PackSnapshotDocument;
import com.tripify.pack.service.model.PackSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
@RequiredArgsConstructor
public class PackSnapshotQueryRepository {

    private final MongoTemplate mongoTemplate;

    public List<PackSnapshotDocument> findBySearchCriteria(PackSearchCriteria criteria) {
        Query query = new Query();
        query.addCriteria(Criteria.where("searchContext.originCountry")
                .is(normalizeCountry(criteria.originCountry())));
        query.addCriteria(Criteria.where("searchContext.originCity")
                .is(normalizeCity(criteria.originCity())));
        query.addCriteria(Criteria.where("searchContext.destinationCountry")
                .is(normalizeCountry(criteria.destinationCountry())));
        query.addCriteria(Criteria.where("searchContext.destinationCity")
                .is(normalizeCity(criteria.destinationCity())));
        query.addCriteria(Criteria.where("searchContext.dateFrom").is(criteria.dateFrom()));
        query.addCriteria(Criteria.where("searchContext.dateTo").is(criteria.dateTo()));
        query.addCriteria(Criteria.where("searchContext.adults").is(criteria.adults()));

        if (criteria.children() != null) {
            query.addCriteria(Criteria.where("searchContext.children").is(criteria.children()));
        }
        if (criteria.budget() != null) {
            query.addCriteria(Criteria.where("searchContext.budget").is(criteria.budget()));
        }

        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        return mongoTemplate.find(query, PackSnapshotDocument.class);
    }

    private static String normalizeCountry(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private static String normalizeCity(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }
}
