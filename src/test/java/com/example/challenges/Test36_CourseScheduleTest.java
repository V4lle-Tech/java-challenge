package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test36_CourseScheduleTest {

    @Test
    void shouldReturnTrueWhenNoCycle() {
        int numCourses = 2;
        int[][] prerequisites = { { 1, 0 } };
        assertTrue(CourseSchedule.canFinish(numCourses, prerequisites));
    }

    @Test
    void shouldReturnFalseWhenCycleExists() {
        int numCourses = 2;
        int[][] prerequisites = { { 1, 0 }, { 0, 1 } };
        assertFalse(CourseSchedule.canFinish(numCourses, prerequisites));
    }
}
