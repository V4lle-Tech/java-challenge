package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test37_NumberOfIslandsTest {

    @Test
    void shouldCountNumberOfIslands() {
        char[][] grid = {
                { '1', '1', '0', '0', '0' },
                { '1', '1', '0', '0', '0' },
                { '0', '0', '1', '0', '0' },
                { '0', '0', '0', '1', '1' }
        };
        assertEquals(3, NumberOfIslands.numIslands(grid));
    }
}
