package com.sg.url.tinyurl.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShortURLRepositoryTest {

    @Mock
    RedisTemplate redisTemplate;

    @Mock
    ValueOperations valueOperations;

    @Mock
    HashOperations<String, String, String> hashOperations;

    ShortURLRepository shortUrlRepository;

    @Before
    public void setUp() throws Exception {
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);
        when(redisTemplate.opsForHash())
                .thenReturn(hashOperations);
        shortUrlRepository = new ShortURLRepository(redisTemplate);
    }

    @Test
    public void shouldSaveUrl() {
        shortUrlRepository.saveUrl("xyz", "abcd.com");
        verify(hashOperations).put("1", "xyz", "abcd.com");
    }

    @Test
    public void shouldGetUrl() {
        when(hashOperations.get("1", "xyz")).thenReturn("abcd.com");
        String url = shortUrlRepository.getUrl("xyz").get();
        assertEquals("abcd.com", url);
        verify(hashOperations).get("1", "xyz");
    }

    @Test
    public void shouldGetNextID() {
        when(valueOperations.increment("pKeySequence")).thenReturn(1L);

        long id = shortUrlRepository.getNextID();
        assertEquals(1L, id);
    }
}