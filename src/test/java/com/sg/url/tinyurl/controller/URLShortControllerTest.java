package com.sg.url.tinyurl.controller;

import com.sg.url.tinyurl.core.HashAdapter;
import com.sg.url.tinyurl.model.URLDetails;
import com.sg.url.tinyurl.repository.URLRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class URLShortControllerTest {

    @Mock
    HttpServletRequest request;

    @Mock
    URLRepository urlRepository;

    @Mock
    HashAdapter hashAdapter;

    @InjectMocks
    URLShortController controller;

    @Test
    public void shouldSaveShortUrlToRepository() {
        URLDetails urlDetails = new URLDetails("http://abc.com");
        Mockito.when(urlRepository.getNextID()).thenReturn(1L);
        Mockito.when(hashAdapter.toHash(1L)).thenReturn("xyz");
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("localhost.com/"));

        controller.getShortUrl(request, urlDetails);

        verify(urlRepository).saveUrl("xyz", "http://abc.com");
    }

    @Test
    public void shouldGetFullUrlFromRepository() {
        String urlHash = "xyz";
        Mockito.when(urlRepository.getUrl(urlHash)).thenReturn("abc.com");

        controller.getFullUrl(urlHash);

        verify(urlRepository).getUrl(urlHash);
    }

    @Test
    public void shouldNotSaveInvalidUrlToRepository() {
        URLDetails urlDetails = new URLDetails("invalid.com");

        controller.getShortUrl(request, urlDetails);

        verify(urlRepository, never()).saveUrl(anyString(), anyString());
        verify(urlRepository, never()).getNextID();
        verify(hashAdapter, never()).toHash(anyLong());
        verify(request, never()).getRequestURL();
    }
}