package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test20_ValidAnagramTest {

    @Test
    void shouldReturnTrueWhenSIsAnagramAndTIsNagaram() {
        String s = "anagram";
        String t = "nagaram";
        assertTrue(ValidAnagram.isAnagram(s, t));
    }
}
