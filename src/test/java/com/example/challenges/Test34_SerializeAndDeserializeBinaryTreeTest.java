package com.example.challenges;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Test34_SerializeAndDeserializeBinaryTreeTest {

    @Test
    void shouldSerializeAndDeserializeTree() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);
        SerializeAndDeserializeBinaryTree codec = new SerializeAndDeserializeBinaryTree();
        String serialized = codec.serialize(root);
        TreeNode deserialized = codec.deserialize(serialized);
        // Assuming equals method or check structure
        assertNotNull(deserialized);
        assertEquals(1, deserialized.val);
        assertEquals(2, deserialized.left.val);
        assertEquals(3, deserialized.right.val);
        assertEquals(4, deserialized.right.left.val);
        assertEquals(5, deserialized.right.right.val);
    }
}
