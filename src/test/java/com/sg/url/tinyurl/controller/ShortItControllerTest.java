package com.sg.url.tinyurl.controller;

import com.sg.url.tinyurl.core.ShortItService;
import com.sg.url.tinyurl.model.URLDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.HOURS;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.SEE_OTHER;

@RunWith(MockitoJUnitRunner.class)
public class ShortItControllerTest {

    @Mock
    HttpServletRequest request;

    @Mock
    ShortItService shortITService;

    @InjectMocks
    ShortItController controller;

    @Test
    public void shouldGetShortURL() {
        URLDetails urlDetails = new URLDetails("http://abc.com", -1L);
        when(request.getRequestURL()).thenReturn(new StringBuffer("localhost.com/"));
        when(shortITService.processShortUrl("http://abc.com", -1)).thenReturn("a");

        ResponseEntity<String> shortUrl = controller.getShortUrl(request, urlDetails);

        assertEquals(OK, shortUrl.getStatusCode());
        assertEquals("localhost.com/a", shortUrl.getBody());
        verify(shortITService).processShortUrl("http://abc.com", -1);
    }

    @Test
    public void shouldCreateShortURLWithDefaultExpiryOf24Hours() {
        URLDetails urlDetails = new URLDetails("http://abc.com", null);
        when(request.getRequestURL()).thenReturn(new StringBuffer("localhost.com/"));
        when(shortITService.processShortUrl("http://abc.com", HOURS.toSeconds(24))).thenReturn("a");

        ResponseEntity<String> shortUrl = controller.getShortUrl(request, urlDetails);

        assertEquals(OK, shortUrl.getStatusCode());
        assertEquals("localhost.com/a", shortUrl.getBody());
        verify(shortITService).processShortUrl("http://abc.com", 86400L);
    }

    @Test
    public void shouldGetFullUrlFromRepository() {
        String urlHash = "xyz";
        Mockito.when(shortITService.getUrl(urlHash)).thenReturn(Optional.of("abc.com"));

        ResponseEntity<Object> fullUrl = controller.getFullUrl(urlHash);

        assertEquals(SEE_OTHER, fullUrl.getStatusCode());
        assertEquals("abc.com", fullUrl.getHeaders().get("location").get(0));

        verify(shortITService).getUrl(urlHash);
    }
}