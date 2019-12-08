package com.sg.url.tinyurl.controller;

import com.sg.url.tinyurl.core.HashAdapter;
import com.sg.url.tinyurl.model.URLDetails;
import com.sg.url.tinyurl.repository.URLRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@Controller
public class URLShortController {
    private static final String INVALID_URL_MSG = "Invalid url";
    private final HashAdapter hashAdapter;
    private final URLRepository urlRepository;

    @Autowired
    public URLShortController(HashAdapter hashAdapter, URLRepository urlRepository) {
        this.hashAdapter = hashAdapter;
        this.urlRepository = urlRepository;
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getShortUrl(HttpServletRequest request, @RequestBody URLDetails urlDetails) {
        if (isValidURL(urlDetails.url)) {
            return processShortUrl(request, urlDetails);
        }
        return new ResponseEntity<>(INVALID_URL_MSG, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{hash}", produces = TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getFullUrl(@PathVariable("hash") String hash) {
        String fullURL = urlRepository.getUrl(hash);
        if (StringUtils.isEmpty(fullURL)) {
            return new ResponseEntity<>(Strings.EMPTY, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, fullURL).build();
    }

    private ResponseEntity<String> processShortUrl(HttpServletRequest request, @ModelAttribute URLDetails urlDetails) {
        Long nextID = urlRepository.getNextID();
        String hash = hashAdapter.toHash(nextID);
        urlRepository.saveUrl(hash, urlDetails.url);
        String shortUrl = request.getRequestURL() + hash;
        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }

    private boolean isValidURL(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }
}
