package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

class Test12_GroupAnagramsTest {

    @Test
    void shouldGroupAnagrams() {
        String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
        List<List<String>> result = GroupAnagrams.groupAnagrams(strs);
        // Sort the result for comparison
        for (List<String> group : result) {
            group.sort(String::compareTo);
        }
        result.sort((a, b) -> a.get(0).compareTo(b.get(0)));
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("bat"),
                Arrays.asList("nat", "tan"),
                Arrays.asList("ate", "eat", "tea"));
        for (List<String> group : expected) {
            group.sort(String::compareTo);
        }
        expected.sort((a, b) -> a.get(0).compareTo(b.get(0)));
        assertEquals(expected, result);
    }
}
