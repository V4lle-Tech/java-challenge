package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test46_DecodeWaysTest {

    @Test
    void shouldReturn2ForS12() {
        String s = "12";
        assertEquals(2, DecodeWays.numDecodings(s));
    }

    @Test
    void shouldReturn3ForS226() {
        String s = "226";
        assertEquals(3, DecodeWays.numDecodings(s));
    }
}
