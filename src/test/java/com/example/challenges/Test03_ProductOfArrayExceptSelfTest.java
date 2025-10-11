package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test03_ProductOfArrayExceptSelfTest {

    @Test
    void shouldReturn24_12_8_6WhenNumsIs1_2_3_4() {
        int[] nums = { 1, 2, 3, 4 };
        int[] expected = { 24, 12, 8, 6 };
        assertArrayEquals(expected, ProductOfArrayExceptSelf.productExceptSelf(nums));
    }

    @Test
    void shouldReturn0_0_9_0_0WhenNumsIsMinus1_1_0_Minus3_3() {
        int[] nums = { -1, 1, 0, -3, 3 };
        int[] expected = { 0, 0, 9, 0, 0 };
        assertArrayEquals(expected, ProductOfArrayExceptSelf.productExceptSelf(nums));
    }
}
