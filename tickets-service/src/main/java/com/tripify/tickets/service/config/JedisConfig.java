package com.tripify.tickets.service.config;

import com.tripify.tickets.service.config.property.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class JedisConfig {

    @Bean
    public JedisPool jedisPool(RedisProperties properties) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(properties.maxTotal());
        poolConfig.setMaxIdle(properties.maxIdle());
        poolConfig.setMinIdle(properties.minIdle());

        var clientConfigBuilder = DefaultJedisClientConfig.builder()
                .timeoutMillis(properties.timeoutMs());

        if (properties.password() != null && !properties.password().isBlank()) {
            clientConfigBuilder.password(properties.password());
        }

        return new JedisPool(
                poolConfig,
                new HostAndPort(properties.host(), properties.port()),
                clientConfigBuilder.build()
        );
    }
}
