package com.example.challenges;

import java.util.HashMap;

public class CountSubarraysWithSumK {

    public static void main(String[] args) {
        int[] numsA = { 1, 2, 3 }; // 2
        int k = 3;
        System.out.println(subarraySumPrefixSum(numsA, k));
        int[] numsB = { 1, 0, 1, 1, 2, 3 }; // 3
        k = 3;
        System.out.println(subarraySumPrefixSum(numsB, k));
    }

    public static int subarraySumPrefixSum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int count = 0;
        int sum = 0;
        HashMap<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1); // Para subarrays que empiezan desde el inicio

        for (int num : nums) {
            sum += num;
            // Verificar si existe un prefix sum anterior que sea sum - k
            if (prefixSumCount.containsKey(sum - k)) {
                count += prefixSumCount.get(sum - k);
            }
            // Actualizar la frecuencia del prefix sum actual
            prefixSumCount.put(sum, prefixSumCount.getOrDefault(sum, 0) + 1);
            System.out.println(prefixSumCount.toString());
        }

        return count;
    }
}
