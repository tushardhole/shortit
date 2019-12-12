package com.sg.url.tinyurl.controller;

import com.sg.url.tinyurl.core.ShortItService;
import com.sg.url.tinyurl.model.URLDetails;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@Controller
public class ShortItController {
    private final ShortItService shortITService;

    @Autowired
    public ShortItController(ShortItService shortITService) {
        this.shortITService = shortITService;
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getShortUrl(HttpServletRequest request, @RequestBody @Valid URLDetails urlDetails) {
        String shortUrl = request.getRequestURL() + shortITService.processShortUrl(urlDetails.url, urlDetails.expiryInSeconds);
        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }

    @GetMapping(value = "/{hash}", produces = TEXT_PLAIN_VALUE)
    public ResponseEntity<Object> getFullUrl(@PathVariable("hash") @Valid @NotEmpty String hash) {
        return shortITService.getUrl(hash)
                .map(it -> ResponseEntity.status(HttpStatus.SEE_OTHER).header(HttpHeaders.LOCATION, it).build())
                .orElse(new ResponseEntity<>(Strings.EMPTY, HttpStatus.NOT_FOUND));
    }
}
