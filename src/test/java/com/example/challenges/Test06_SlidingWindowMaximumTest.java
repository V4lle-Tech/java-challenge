package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class Test06_SlidingWindowMaximumTest {

    @Test
    void shouldReturnMaxInEachWindowOfSizeK() {
        int[] nums = { 1, 3, -1, -3, 5, 3, 6, 7 };
        int k = 3;
        int[] expected = { 3, 3, 5, 5, 6, 7 };
        assertArrayEquals(expected, SlidingWindowMaximum.maxSlidingWindow(nums, k));
    }
}
