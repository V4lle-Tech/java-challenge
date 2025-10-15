package com.example.challenges;

public class CountSubarraysWithSumK {

    public static void main(String[] args) {
        int[] numsA = { 1, 1, 1 };
        int k = 1;
        System.out.println(subarraySumPrefixSum(numsA, k));
        int[] numsB = { 1, 2, 3 };
        k = 3;
        System.out.println(subarraySumPrefixSum(numsB, k));
    }

    public static int subarraySumPrefixSum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == k) {
                count++;
            }
            if (i > 0) {
                if (k == (nums[i - 1] + nums[i])) {
                    count++;
                }
            }
        }

        return count;
    }
}
