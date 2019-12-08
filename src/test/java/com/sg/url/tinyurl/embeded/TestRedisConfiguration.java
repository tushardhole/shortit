package com.sg.url.tinyurl.embeded;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisShardInfo;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class TestRedisConfiguration {


    private final RedisServer redisServer;

    public TestRedisConfiguration(@Value("${spring.redis.port}") int redisPort) {
        this.redisServer = new RedisServer(redisPort);

        JedisShardInfo shardInfo = new JedisShardInfo("localhost", 6379);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setShardInfo(shardInfo);

        RedisTemplate template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}