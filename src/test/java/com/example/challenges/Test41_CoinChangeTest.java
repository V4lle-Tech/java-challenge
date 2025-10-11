package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test41_CoinChangeTest {

    @Test
    void shouldReturn3ForCoins1_2_5AndAmount11() {
        int[] coins = { 1, 2, 5 };
        int amount = 11;
        assertEquals(3, CoinChange.coinChange(coins, amount));
    }

    @Test
    void shouldReturnMinus1WhenImpossible() {
        int[] coins = { 2 };
        int amount = 3;
        assertEquals(-1, CoinChange.coinChange(coins, amount));
    }
}
