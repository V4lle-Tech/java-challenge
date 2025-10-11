package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class Test24_MergeIntervalsTest {

    @Test
    void shouldMergeOverlappingIntervals() {
        int[][] intervals = { { 1, 3 }, { 2, 6 }, { 8, 10 }, { 15, 18 } };
        int[][] result = MergeIntervals.merge(intervals);
        int[][] expected = { { 1, 6 }, { 8, 10 }, { 15, 18 } };
        assertArrayEquals(expected, result);
    }
}
