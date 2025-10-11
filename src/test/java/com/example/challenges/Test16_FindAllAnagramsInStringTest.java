package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class Test16_FindAllAnagramsInStringTest {

    @Test
    void shouldReturn0And6WhenSIsCbaebabacdAndPIsAbc() {
        String s = "cbaebabacd";
        String p = "abc";
        List<Integer> expected = Arrays.asList(0, 6);
        assertEquals(expected, FindAllAnagramsInString.findAnagrams(s, p));
    }
}
