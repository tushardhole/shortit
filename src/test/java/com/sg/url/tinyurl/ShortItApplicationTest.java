package com.sg.url.tinyurl;

import com.sg.url.tinyurl.embeded.TestRedisConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc
public class ShortItApplicationTest {
    @Autowired
    MockMvc mvc;

    @Test
    public void urlToShortUrlAndGetBackFullUrl() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\" : \"http://abcd123.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(is("http://localhost/b")));

        mvc.perform(get("http://localhost/b"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://abcd123.com"));
    }

    @Test
    public void shouldNotProcessInvalidURL() throws Exception {
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\" : \"abcd123com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotProcessInvalidShortURL() throws Exception {
        mvc.perform(get("http://localhost/invalid")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
