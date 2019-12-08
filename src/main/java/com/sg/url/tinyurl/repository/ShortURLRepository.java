package com.sg.url.tinyurl.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ShortURLRepository {
    private HashOperations<String, String, String> hashOperations;
    private ValueOperations<String, String> valueOperations;
    private final String primaryKeySequence = "pKeySequence";

    private RedisTemplate<String, String> redisTemplate;

    public ShortURLRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
        this.valueOperations = this.redisTemplate.opsForValue();
    }

    public void saveUrl(String hash, String url) {
        hashOperations.put("1", hash, url);
    }

    public Optional<String> getUrl(String hash) {
        return Optional.ofNullable(hashOperations.get("1", hash));
    }

    public Long getNextID() {
        return valueOperations.increment(primaryKeySequence);
    }
}