package com.sg.url.tinyurl.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShortURLRepositoryTest {

    @Mock
    RedisTemplate redisTemplate;

    @Mock
    ValueOperations valueOperations;

    ShortURLRepository shortUrlRepository;

    @Before
    public void setUp() {
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);
        shortUrlRepository = new ShortURLRepository(redisTemplate);
    }

    @Test
    public void shouldSaveUrlAndNeverExpire() {
        shortUrlRepository.saveUrl("xyz", "abcd.com", -1);
        verify(valueOperations).set("xyz", "abcd.com");
    }

    @Test
    public void shouldSaveUrlAndExpireIn1Second() {
        shortUrlRepository.saveUrl("xyz", "abcd.com", 1);
        Duration oneSecond = Duration.ofSeconds(1);
        verify(valueOperations).set("xyz", "abcd.com", oneSecond);
    }

    @Test
    public void shouldSaveUrlAndExpireIn100Second() {
        shortUrlRepository.saveUrl("xyz", "abcd.com", 100);
        Duration hundredSecond = Duration.ofSeconds(100);
        verify(valueOperations).set("xyz", "abcd.com", hundredSecond);
    }

    @Test
    public void shouldGetUrl() {
        when(valueOperations.get("xyz")).thenReturn("abcd.com");
        String url = shortUrlRepository.getUrl("xyz").get();
        assertEquals("abcd.com", url);
        verify(valueOperations).get("xyz");
    }

    @Test
    public void shouldGetNextID() {
        when(valueOperations.increment("pKeySequence")).thenReturn(1L);

        long id = shortUrlRepository.getNextID();
        assertEquals(1L, id);
    }
}