package com.sg.url.tinyurl.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.URL;

public class URLDetails {
    @JsonProperty
    @URL(message = "Invalid URL format")
    public final String url;

    @JsonCreator
    public URLDetails(String url) {
        this.url = url;
    }
}
