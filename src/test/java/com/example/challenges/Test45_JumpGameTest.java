package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test45_JumpGameTest {

    @Test
    void shouldReturnTrueWhenCanReachEnd() {
        int[] nums = { 2, 3, 1, 1, 4 };
        assertTrue(JumpGame.canJump(nums));
    }

    @Test
    void shouldReturnFalseWhenCannotReachEnd() {
        int[] nums = { 3, 2, 1, 0, 4 };
        assertFalse(JumpGame.canJump(nums));
    }
}
