package com.sg.url.tinyurl.core;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashAdapterTest {
    private final HashAdapter hash = new HashAdapter();

    @Test
    public void shouldGenerateHashAndReverseHash() {
        Map<Long, String> expectedHash = new HashMap<>();
        expectedHash.put(1L, "b");
        expectedHash.put(2L, "c");
        expectedHash.put((Long.MAX_VALUE - 1), "k9viXaIfiWg");
        expectedHash.put(Long.MAX_VALUE, "k9viXaIfiWh");

        expectedHash.forEach((k, v) -> {
            String generatedHash = hash.toHash(k);
            long reversedHash = hash.toID(generatedHash);

            assertEquals(k, reversedHash);
            assertEquals(v, generatedHash);
        });
    }
}