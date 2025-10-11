package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class Test47_WordBreakTest {

    @Test
    void shouldReturnTrueWhenSLeetcodeAndWordDictLeetCode() {
        String s = "leetcode";
        List<String> wordDict = Arrays.asList("leet", "code");
        assertTrue(WordBreak.wordBreak(s, wordDict));
    }

    @Test
    void shouldReturnFalseWhenSCatsandogAndWordDictGiven() {
        String s = "catsandog";
        List<String> wordDict = Arrays.asList("cats", "dog", "sand", "and", "cat");
        assertFalse(WordBreak.wordBreak(s, wordDict));
    }
}
