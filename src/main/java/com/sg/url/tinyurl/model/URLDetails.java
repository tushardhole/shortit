package com.sg.url.tinyurl.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class URLDetails {
    @JsonProperty
    public final String url;

    @JsonCreator
    public URLDetails(String url) {
        this.url = url;
    }
}
