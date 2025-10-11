package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test40_GraphValidTreeTest {

    @Test
    void shouldReturnTrueForValidTree() {
        int n = 5;
        int[][] edges = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 4 } };
        assertTrue(GraphValidTree.validTree(n, edges));
    }

    @Test
    void shouldReturnFalseForInvalidTree() {
        int n = 5;
        int[][] edges = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 1, 3 }, { 1, 4 } };
        assertFalse(GraphValidTree.validTree(n, edges));
    }
}
