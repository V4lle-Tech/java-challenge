package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test08_MaximumProductSubarrayTest {

    @Test
    void shouldReturnMaximumProductSubarray() {
        int[] nums = { 2, 3, -2, 4 };
        assertEquals(6, MaximumProductSubarray.maxProduct(nums));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] nums = { -2, 3, -4 };
        assertEquals(24, MaximumProductSubarray.maxProduct(nums));
    }
}
