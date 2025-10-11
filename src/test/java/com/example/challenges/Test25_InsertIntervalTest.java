package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class Test25_InsertIntervalTest {

    @Test
    void shouldInsertAndMergeInterval() {
        int[][] intervals = { { 1, 3 }, { 6, 9 } };
        int[] newInterval = { 2, 5 };
        int[][] result = InsertInterval.insert(intervals, newInterval);
        int[][] expected = { { 1, 5 }, { 6, 9 } };
        assertArrayEquals(expected, result);
    }
}
