package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class Test17_EncodeAndDecodeStringsTest {

    @Test
    void shouldEncodeAndDecodeHelloWorld() {
        List<String> input = Arrays.asList("Hello", "World");
        String encoded = EncodeAndDecodeStrings.encode(input);
        List<String> decoded = EncodeAndDecodeStrings.decode(encoded);
        assertEquals(input, decoded);
    }
}
