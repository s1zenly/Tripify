package com.tripify.auth.service.clients;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;
import java.util.Optional;

@Component
public class RedisClient {

    private final JedisPool jedisPool;

    public RedisClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void set(
            String key,
            String value,
            Duration ttl
    ) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, ttl.toSeconds(), value);
        }
    }

    public Optional<String> get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return Optional.ofNullable(jedis.get(key));
        }
    }

    public void delete(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }
}
