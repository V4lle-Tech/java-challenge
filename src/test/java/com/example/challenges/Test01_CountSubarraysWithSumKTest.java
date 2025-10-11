package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test01_CountSubarraysWithSumKTest {

    @Test
    void shouldReturn2WhenNumsIs111AndKIs2() {
        int[] nums = { 1, 1, 1 };
        int k = 2;
        assertEquals(2, CountSubarraysWithSumK.subarraySumPrefixSum(nums, k));
    }

    @Test
    void shouldReturn2WhenNumsIs123AndKIs3() {
        int[] nums = { 1, 2, 3 };
        int k = 3;
        assertEquals(2, CountSubarraysWithSumK.subarraySumPrefixSum(nums, k));
    }
}
