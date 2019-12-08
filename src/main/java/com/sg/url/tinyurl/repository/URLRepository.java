package com.sg.url.tinyurl.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class URLRepository {
    private HashOperations<String, String, String> hashOperations;
    private ValueOperations<String, String> valueOperations;
    private final String primaryKeySequence = "pKeySequence";

    private RedisTemplate<String, String> redisTemplate;

    public URLRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
        this.valueOperations = this.redisTemplate.opsForValue();
    }

    public void saveUrl(String hash, String url) {
        hashOperations.put("1", hash, url);
    }

    public String getUrl(String hash) {
        return hashOperations.get("1", hash);
    }

    public Long getNextID() {
        return valueOperations.increment(primaryKeySequence);
    }
}