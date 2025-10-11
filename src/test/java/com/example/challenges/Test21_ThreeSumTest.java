package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

class Test21_ThreeSumTest {

    @Test
    void shouldReturnTripletsThatSumToZero() {
        int[] nums = { -1, 0, 1, 2, -1, -4 };
        List<List<Integer>> result = ThreeSum.threeSum(nums);
        // Sort the result for comparison
        for (List<Integer> triplet : result) {
            triplet.sort(Integer::compareTo);
        }
        result.sort((a, b) -> {
            int cmp = a.get(0).compareTo(b.get(0));
            if (cmp == 0)
                cmp = a.get(1).compareTo(b.get(1));
            if (cmp == 0)
                cmp = a.get(2).compareTo(b.get(2));
            return cmp;
        });
        List<List<Integer>> expected = Arrays.asList(
                Arrays.asList(-1, -1, 2),
                Arrays.asList(-1, 0, 1));
        for (List<Integer> triplet : expected) {
            triplet.sort(Integer::compareTo);
        }
        expected.sort((a, b) -> {
            int cmp = a.get(0).compareTo(b.get(0));
            if (cmp == 0)
                cmp = a.get(1).compareTo(b.get(1));
            if (cmp == 0)
                cmp = a.get(2).compareTo(b.get(2));
            return cmp;
        });
        assertEquals(expected, result);
    }
}
