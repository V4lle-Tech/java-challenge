package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test42_HouseRobberTest {

    @Test
    void shouldReturn4ForNums1_2_3_1() {
        int[] nums = { 1, 2, 3, 1 };
        assertEquals(4, HouseRobber.rob(nums));
    }

    @Test
    void shouldReturn12ForNums2_7_9_3_1() {
        int[] nums = { 2, 7, 9, 3, 1 };
        assertEquals(12, HouseRobber.rob(nums));
    }
}
