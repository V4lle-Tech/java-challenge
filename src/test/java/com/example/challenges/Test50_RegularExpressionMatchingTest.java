package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test50_RegularExpressionMatchingTest {

    @Test
    void shouldReturnFalseForSAaAndPA() {
        String s = "aa";
        String p = "a";
        assertFalse(RegularExpressionMatching.isMatch(s, p));
    }

    @Test
    void shouldReturnTrueForSAaAndPAStar() {
        String s = "aa";
        String p = "a*";
        assertTrue(RegularExpressionMatching.isMatch(s, p));
    }

    @Test
    void shouldReturnTrueForSAbAndPDotStar() {
        String s = "ab";
        String p = ".*";
        assertTrue(RegularExpressionMatching.isMatch(s, p));
    }
}
