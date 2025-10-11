package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test49_PalindromicSubstringsTest {

    @Test
    void shouldReturn3ForSAbc() {
        String s = "abc";
        assertEquals(3, PalindromicSubstrings.countSubstrings(s));
    }

    @Test
    void shouldReturn6ForSAaa() {
        String s = "aaa";
        assertEquals(6, PalindromicSubstrings.countSubstrings(s));
    }
}
