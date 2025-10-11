package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test14_ValidParenthesesTest {

    @Test
    void shouldReturnTrueForValidParentheses() {
        String s = "()[]{}";
        assertTrue(ValidParentheses.isValid(s));
    }

    @Test
    void shouldReturnFalseForInvalidParentheses() {
        String s = "([)]";
        assertFalse(ValidParentheses.isValid(s));
    }
}
