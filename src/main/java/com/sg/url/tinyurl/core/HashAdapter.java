package com.sg.url.tinyurl.core;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.sg.url.tinyurl.core.HashCharSet.HASH_CHAR_SET;
import static com.sg.url.tinyurl.core.HashCharSet.HASH_CHAR_SET_INDEXES;
import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.toList;

@Component
public class HashAdapter {
    public String toHash(long id) {
        List<Long> hashDigits = LongStream.iterate(id, i -> i > 0, i -> i / 62L)
                .map(it -> it % 62)
                .boxed()
                .collect(toList());

        Collections.reverse(hashDigits);

        return hashDigits
                .stream()
                .map(it -> String.valueOf(HASH_CHAR_SET[toIntExact(it)]))
                .collect(Collectors.joining());
    }

    public long toID(String hash) {
        char[] hashChars = hash.toCharArray();
        return LongStream.iterate(0, i -> i < hashChars.length, i -> ++i)
                .map(it -> (long) (HASH_CHAR_SET_INDEXES.get(hash.toCharArray()[(int) it]) * Math.pow(62.0, hashChars.length - it - 1)))
                .reduce(Long::sum)
                .orElse(0L);
    }
}
