package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test10_LongestConsecutiveSequenceTest {

    @Test
    void shouldFindLongestConsecutiveSequence() {
        int[] nums = { 100, 4, 200, 1, 3, 2 };
        assertEquals(4, LongestConsecutiveSequence.longestConsecutive(nums));
    }

    @Test
    void shouldHandleLargerSequence() {
        int[] nums = { 0, 3, 7, 2, 5, 8, 4, 6, 0, 1 };
        assertEquals(9, LongestConsecutiveSequence.longestConsecutive(nums));
    }
}
