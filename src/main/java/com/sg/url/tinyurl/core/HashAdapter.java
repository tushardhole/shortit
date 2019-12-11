package com.sg.url.tinyurl.core;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.sg.url.tinyurl.core.HashCharSet.HASH_CHAR_SET;
import static com.sg.url.tinyurl.core.HashCharSet.HASH_CHAR_SET_INDEXES;
import static java.lang.String.valueOf;

@Component
public class HashAdapter {
    public String toHash(long id) {
        return LongStream.iterate(id, i -> i > 0, i -> i / 62L)
                .map(digit -> digit % 62)
                .mapToInt(Math::toIntExact)
                .mapToObj(digitRem -> valueOf(HASH_CHAR_SET[digitRem]))
                .collect(Collectors.joining());
    }

    public long toID(String hash) {
        final char[] hashChars = hash.toCharArray();
        return LongStream.iterate(hashChars.length - 1, i -> i >= 0, i -> --i)
                .map(it -> (long) (HASH_CHAR_SET_INDEXES.get(hashChars[(int) it]) * Math.pow(62.0, it)))
                .reduce(Long::sum)
                .orElse(0L);
    }
}
