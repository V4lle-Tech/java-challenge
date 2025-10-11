package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class Test05_FindAllDuplicatesInArrayTest {

    @Test
    void shouldReturnDuplicatesWhenArrayHasDuplicates() {
        int[] nums = { 4, 3, 2, 7, 8, 2, 3, 1 };
        List<Integer> expected = Arrays.asList(2, 3);
        assertEquals(expected, FindAllDuplicatesInArray.findDuplicates(nums));
    }
}
