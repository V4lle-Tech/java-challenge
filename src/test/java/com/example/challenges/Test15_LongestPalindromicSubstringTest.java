package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test15_LongestPalindromicSubstringTest {

    @Test
    void shouldReturnBabOrAbaWhenSIsBabad() {
        String s = "babad";
        String result = LongestPalindromicSubstring.longestPalindrome(s);
        assertTrue("bab".equals(result) || "aba".equals(result));
    }
}
