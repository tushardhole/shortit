package com.sg.url.tinyurl.core;

import java.util.HashMap;
import java.util.stream.IntStream;

public class HashCharSet {
    public static final char[] HASH_CHAR_SET = new char[62];
    public static final HashMap<Character, Integer> HASH_CHAR_SET_INDEXES = new HashMap<>();

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
        if (isAtoZRange(index)) {
            return (char) ('A' + index - 26);
        } else if (isNumericRange(index)) {
            return (char) ('0' + index - 52);
        } else {
            return (char) ('a' + index);
        }
    }

    private static boolean isNumericRange(int index) {
        return index >= 52;
    }

    private static boolean isAtoZRange(int index) {
        return index > 25 && index < 52;
    }
}
