package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test04_ContainerWithMostWaterTest {

    @Test
    void shouldReturn49WhenHeightIs1_8_6_2_5_4_8_3_7() {
        int[] height = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
        assertEquals(49, ContainerWithMostWater.maxArea(height));
    }
}
