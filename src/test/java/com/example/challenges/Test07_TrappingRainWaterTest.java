package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test07_TrappingRainWaterTest {

    @Test
    void shouldCalculateTrappedRainWater() {
        int[] height = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
        assertEquals(6, TrappingRainWater.trap(height));
    }
}
