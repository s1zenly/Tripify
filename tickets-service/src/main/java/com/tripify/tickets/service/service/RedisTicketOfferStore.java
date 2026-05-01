package com.tripify.tickets.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripify.tickets.service.clients.RedisClient;
import com.tripify.tickets.service.config.property.RedisOfferStoreProperties;
import com.tripify.tickets.service.exception.TicketOfferStoreException;
import com.tripify.tickets.service.model.unified.UnifiedOffer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisTicketOfferStore implements TicketOfferStore {

    private static final Logger logger = LoggerFactory.getLogger(RedisTicketOfferStore.class);

    private final RedisClient redisClient;
    private final RedisOfferStoreProperties properties;
    private final ObjectMapper objectMapper;

    @Override
    public void saveAll(Collection<UnifiedOffer> offers) {
        offers.forEach(this::save);
    }

    @Override
    public Optional<UnifiedOffer> findById(String tid) {
        return redisClient.get(offerKey(tid)).map(this::deserialize);
    }

    private void save(UnifiedOffer offer) {
        if (offer == null || offer.unifiedOfferId() == null) {
            return;
        }

        try {
            redisClient.set(
                    offerKey(offer.unifiedOfferId()),
                    objectMapper.writeValueAsString(offer),
                    resolveTtl(offer)
            );
        } catch (JsonProcessingException exception) {
            throw new TicketOfferStoreException(
                    "Failed to serialize ticket offer: tid=" + offer.unifiedOfferId(),
                    exception
            );
        }
    }

    private UnifiedOffer deserialize(String payload) {
        try {
            return objectMapper.readValue(payload, UnifiedOffer.class);
        } catch (JsonProcessingException exception) {
            throw new TicketOfferStoreException("Failed to deserialize ticket offer from Redis", exception);
        }
    }

    private Duration resolveTtl(UnifiedOffer offer) {
        if (offer.availability() != null && offer.availability().expiresAt() != null) {
            Duration ttl = Duration.between(Instant.now(), offer.availability().expiresAt());
            if (!ttl.isNegative() && !ttl.isZero()) {
                return ttl;
            }
            logger.debug("Offer already expired, applying default TTL: tid={}", offer.unifiedOfferId());
        }
        return properties.defaultTtl();
    }

    private String offerKey(String tid) {
        return properties.keyPrefix() + tid;
    }
}
