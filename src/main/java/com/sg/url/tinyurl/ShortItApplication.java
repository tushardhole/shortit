package com.sg.url.tinyurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ShortItApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortItApplication.class, args);
    }

}
