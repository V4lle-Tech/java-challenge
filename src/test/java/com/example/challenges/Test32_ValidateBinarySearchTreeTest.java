package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test32_ValidateBinarySearchTreeTest {

    @Test
    void shouldReturnTrueForValidBST() {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        assertTrue(ValidateBinarySearchTree.isValidBST(root));
    }

    @Test
    void shouldReturnFalseForInvalidBST() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(6);
        assertFalse(ValidateBinarySearchTree.isValidBST(root));
    }
}
