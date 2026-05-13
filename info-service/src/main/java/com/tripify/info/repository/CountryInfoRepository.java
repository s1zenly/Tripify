package com.tripify.info.repository;

import com.tripify.info.model.CountryInfoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountryInfoRepository extends MongoRepository<CountryInfoDocument, String> {
}
