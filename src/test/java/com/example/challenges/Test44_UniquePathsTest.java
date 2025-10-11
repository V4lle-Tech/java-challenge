package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test44_UniquePathsTest {

    @Test
    void shouldReturn28ForM3N7() {
        int m = 3, n = 7;
        assertEquals(28, UniquePaths.uniquePaths(m, n));
    }

    @Test
    void shouldReturn3ForM3N2() {
        int m = 3, n = 2;
        assertEquals(3, UniquePaths.uniquePaths(m, n));
    }
}
