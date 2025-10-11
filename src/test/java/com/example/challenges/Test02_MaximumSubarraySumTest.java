package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test02_MaximumSubarraySumTest {

    @Test
    void shouldReturn6WhenKadaneOnNegativePositiveArray() {
        int[] nums = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
        assertEquals(6, MaximumSubarraySum.maxSubarraySum(nums));
    }

    @Test
    void shouldReturn23WhenKadaneOnPositiveArray() {
        int[] nums = { 5, 4, -1, 7, 8 };
        assertEquals(23, MaximumSubarraySum.maxSubarraySum(nums));
    }
}
