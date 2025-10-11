package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test22_SortColorsTest {

    @Test
    void shouldSortColorsInPlace() {
        int[] nums = { 2, 0, 2, 1, 1, 0 };
        SortColors.sortColors(nums);
        int[] expected = { 0, 0, 1, 1, 2, 2 };
        assertArrayEquals(expected, nums);
    }
}
