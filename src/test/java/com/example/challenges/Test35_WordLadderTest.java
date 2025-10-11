package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class Test35_WordLadderTest {

    @Test
    void shouldReturnLengthOfShortestTransformation() {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
        assertEquals(5, WordLadder.ladderLength(beginWord, endWord, wordList));
    }
}
