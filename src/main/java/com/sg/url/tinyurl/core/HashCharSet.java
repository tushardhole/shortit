package com.sg.url.tinyurl.core;

import java.util.HashMap;
import java.util.stream.IntStream;

class HashCharSet {
    static final char[] HASH_CHAR_SET = new char[62];
    static final HashMap<Character, Integer> HASH_CHAR_SET_INDEXES = new HashMap<>();

    static {
        initializeHashCharSet();
    }

    private static void initializeHashCharSet() {
        IntStream.range(0, 62)
                .forEach(i -> {
                    HASH_CHAR_SET[i] = getCharForIndex(i);
                    HASH_CHAR_SET_INDEXES.put(HASH_CHAR_SET[i], i);
                });
    }

    private static char getCharForIndex(int index) {
        var capitalAlphaFilter = IntStream.range(0, 25)
                .filter(it -> it == index)
                .map(it -> 'a' + it);

        var bigAlphaFilter = IntStream.range(26, 52)
                .filter(it -> it == index)
                .map(it -> 'A' + it - 26);

        return (char) bigAlphaFilter
                .findFirst()
                .orElse(capitalAlphaFilter.findFirst().orElse('0' + index - 52));
    }
}
