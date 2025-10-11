package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class Test27_NonOverlappingIntervalsTest {

    @Test
    void shouldReturnMinimumRemovalsForNonOverlapping() {
        int[][] intervals = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 3 } };
        assertEquals(1, NonOverlappingIntervals.eraseOverlapIntervals(intervals));
    }
}
