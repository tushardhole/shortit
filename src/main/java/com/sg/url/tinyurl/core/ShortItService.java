package com.sg.url.tinyurl.core;

import com.sg.url.tinyurl.repository.ShortURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShortItService {
    private final ShortURLRepository shortUrlRepository;
    private final HashAdapter hashAdapter;

    @Autowired
    public ShortItService(ShortURLRepository shortUrlRepository, HashAdapter hashAdapter) {
        this.shortUrlRepository = shortUrlRepository;
        this.hashAdapter = hashAdapter;
    }

    public String processShortUrl(String fullURL, long expiryInSeconds) {
        Long nextID = shortUrlRepository.getNextID();
        String hash = hashAdapter.toHash(nextID);
        shortUrlRepository.saveUrl(hash, fullURL, expiryInSeconds);
        return hash;
    }

    public Optional<String> getUrl(String hash) {
        return shortUrlRepository.getUrl(hash);
    }
}
