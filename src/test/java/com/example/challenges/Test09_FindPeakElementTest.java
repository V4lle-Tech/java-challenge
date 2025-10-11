package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test09_FindPeakElementTest {

    @Test
    void shouldFindPeakElement() {
        int[] nums = { 1, 2, 3, 1 };
        int result = FindPeakElement.findPeakElement(nums);
        assertTrue(result == 2);
    }

    @Test
    void shouldFindAnyPeakInMultiplePeaks() {
        int[] nums = { 1, 2, 1, 3, 5, 6, 4 };
        int result = FindPeakElement.findPeakElement(nums);
        assertTrue(result == 1 || result == 5);
    }
}
