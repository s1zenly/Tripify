package com.tripify.auth.service.config;

import com.tripify.auth.service.config.property.RedisProperties;
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

        var clientConfig = DefaultJedisClientConfig.builder()
                .password(properties.password())
                .timeoutMillis(properties.timeoutMs())
                .build();

        return new JedisPool(
                poolConfig,
                new HostAndPort(properties.host(), properties.port()),
                clientConfig
        );
    }
}
