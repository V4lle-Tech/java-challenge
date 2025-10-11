package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

class Test29_FourSumTest {

    @Test
    void shouldReturnQuadrupletsThatSumToTarget() {
        int[] nums = { 1, 0, -1, 0, -2, 2 };
        int target = 0;
        List<List<Integer>> result = FourSum.fourSum(nums, target);
        // Sort the result for comparison
        for (List<Integer> quad : result) {
            quad.sort(Integer::compareTo);
        }
        result.sort((a, b) -> {
            int cmp = a.get(0).compareTo(b.get(0));
            if (cmp == 0)
                cmp = a.get(1).compareTo(b.get(1));
            if (cmp == 0)
                cmp = a.get(2).compareTo(b.get(2));
            if (cmp == 0)
                cmp = a.get(3).compareTo(b.get(3));
            return cmp;
        });
        List<List<Integer>> expected = Arrays.asList(
                Arrays.asList(-2, -1, 1, 2),
                Arrays.asList(-2, 0, 0, 2),
                Arrays.asList(-1, 0, 0, 1));
        for (List<Integer> quad : expected) {
            quad.sort(Integer::compareTo);
        }
        expected.sort((a, b) -> {
            int cmp = a.get(0).compareTo(b.get(0));
            if (cmp == 0)
                cmp = a.get(1).compareTo(b.get(1));
            if (cmp == 0)
                cmp = a.get(2).compareTo(b.get(2));
            if (cmp == 0)
                cmp = a.get(3).compareTo(b.get(3));
            return cmp;
        });
        assertEquals(expected, result);
    }
}
