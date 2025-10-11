package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test43_LongestIncreasingSubsequenceTest {

    @Test
    void shouldReturn4ForNums10_9_2_5_3_7_101_18() {
        int[] nums = { 10, 9, 2, 5, 3, 7, 101, 18 };
        assertEquals(4, LongestIncreasingSubsequence.lengthOfLIS(nums));
    }

    @Test
    void shouldReturn4ForNums0_1_0_3_2_3() {
        int[] nums = { 0, 1, 0, 3, 2, 3 };
        assertEquals(4, LongestIncreasingSubsequence.lengthOfLIS(nums));
    }
}
