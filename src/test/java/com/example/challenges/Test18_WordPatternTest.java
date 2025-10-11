package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test18_WordPatternTest {

    @Test
    void shouldReturnTrueWhenPatternAbBaAndSDogCatCatDog() {
        String pattern = "abba";
        String s = "dog cat cat dog";
        assertTrue(WordPattern.wordPattern(pattern, s));
    }

    @Test
    void shouldReturnFalseWhenPatternAbBaAndSDogCatCatFish() {
        String pattern = "abba";
        String s = "dog cat cat fish";
        assertFalse(WordPattern.wordPattern(pattern, s));
    }
}
