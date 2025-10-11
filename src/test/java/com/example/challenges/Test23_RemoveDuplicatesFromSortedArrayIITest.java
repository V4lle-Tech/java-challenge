package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test23_RemoveDuplicatesFromSortedArrayIITest {

    @Test
    void shouldRemoveDuplicatesAllowingAtMostTwo() {
        int[] nums = { 1, 1, 1, 2, 2, 3 };
        int result = RemoveDuplicatesFromSortedArrayII.removeDuplicates(nums);
        assertEquals(5, result);
        int[] expected = { 1, 1, 2, 2, 3 };
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], nums[i]);
        }
    }
}
