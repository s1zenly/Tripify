package com.tripify.pack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.tripify.pack.persistence.mongo.repository")
public class MongoConfig {
}
