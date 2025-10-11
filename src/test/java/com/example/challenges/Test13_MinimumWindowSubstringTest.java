package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test13_MinimumWindowSubstringTest {

    @Test
    void shouldFindMinimumWindowSubstring() {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        assertEquals("BANC", MinimumWindowSubstring.minWindow(s, t));
    }
}
