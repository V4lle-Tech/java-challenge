package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test11_LongestSubstringWithoutRepeatingCharactersTest {

    @Test
    void shouldReturnLengthOfLongestSubstring() {
        String s = "abcabcbb";
        assertEquals(3, LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring(s));
    }

    @Test
    void shouldHandleAnotherExample() {
        String s = "pwwkew";
        assertEquals(3, LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring(s));
    }
}
