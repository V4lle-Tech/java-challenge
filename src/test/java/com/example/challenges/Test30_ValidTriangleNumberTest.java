package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test30_ValidTriangleNumberTest {

    @Test
    void shouldCountValidTriangles() {
        int[] nums = { 2, 2, 3, 4 };
        assertEquals(3, ValidTriangleNumber.triangleNumber(nums));
    }
}
