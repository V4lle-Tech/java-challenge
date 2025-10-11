package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test33_LowestCommonAncestorOfBSTTest {

    @Test
    void shouldFindLowestCommonAncestor() {
        TreeNode root = new TreeNode(6);
        root.left = new TreeNode(2);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(9);
        root.left.right.left = new TreeNode(3);
        root.left.right.right = new TreeNode(5);
        TreeNode p = root.left;
        TreeNode q = root.right;
        TreeNode result = LowestCommonAncestorOfBST.lowestCommonAncestor(root, p, q);
        assertEquals(6, result.val);
    }
}
