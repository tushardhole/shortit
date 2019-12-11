package com.sg.url.tinyurl.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
public class ShortURLRepository {
    private ValueOperations<String, String> valueOperations;
    private final String primaryKeySequence = "pKeySequence";

    private RedisTemplate<String, String> redisTemplate;

    public ShortURLRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = this.redisTemplate.opsForValue();
    }

    public void saveUrl(String hash, String url, long expiryInSeconds) {
        if (expiryInSeconds > 0L) {
            valueOperations.set(hash, url, Duration.ofSeconds(expiryInSeconds));
        } else {
            valueOperations.set(hash, url);
        }
    }

    public Optional<String> getUrl(String hash) {
        return Optional.ofNullable(valueOperations.get(hash));
    }

    public Long getNextID() {
        return valueOperations.increment(primaryKeySequence);
    }
}