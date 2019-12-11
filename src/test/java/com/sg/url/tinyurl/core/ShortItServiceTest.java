package com.sg.url.tinyurl.core;

import com.sg.url.tinyurl.repository.ShortURLRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShortItServiceTest {

    @Mock
    HashAdapter hashAdapter;

    @Mock
    ShortURLRepository shortURLRepository;

    @InjectMocks
    ShortItService shortItService;

    @Test
    public void shouldProcessFullURL() {
        when(shortURLRepository.getNextID()).thenReturn(1L);
        when(hashAdapter.toHash(1L)).thenReturn("a");

        String shortUrl = shortItService.processShortUrl("http://www.google.com", 10);
        assertEquals("a", shortUrl);
        verify(shortURLRepository).saveUrl("a", "http://www.google.com", 10);
    }

    @Test
    public void shouldGetFullURL() {
        when(shortURLRepository.getUrl("a")).thenReturn(Optional.of("abc.com"));

        Optional<String> fullUrl = shortItService.getUrl("a");
        assertEquals("abc.com", fullUrl.get());
    }
}