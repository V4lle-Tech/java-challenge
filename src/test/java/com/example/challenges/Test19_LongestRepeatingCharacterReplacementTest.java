package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test19_LongestRepeatingCharacterReplacementTest {

    @Test
    void shouldReturn4WhenSIsAABABBAAndKIs1() {
        String s = "AABABBA";
        int k = 1;
        assertEquals(4, LongestRepeatingCharacterReplacement.characterReplacement(s, k));
    }
}
