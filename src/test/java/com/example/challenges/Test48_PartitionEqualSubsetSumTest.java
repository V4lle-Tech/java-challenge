package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test48_PartitionEqualSubsetSumTest {

    @Test
    void shouldReturnTrueWhenNums1_5_11_5() {
        int[] nums = { 1, 5, 11, 5 };
        assertTrue(PartitionEqualSubsetSum.canPartition(nums));
    }

    @Test
    void shouldReturnFalseWhenNums1_2_3_5() {
        int[] nums = { 1, 2, 3, 5 };
        assertFalse(PartitionEqualSubsetSum.canPartition(nums));
    }
}
