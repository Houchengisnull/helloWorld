package org.hc.learning.datastruct.tree.二叉树;

import org.junit.Test;

/**
 *
 */
public class BinarySearchTreeNodeTest {

    @Test
    public void testInsert(){
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(5);
        tree.levelOrder();
        tree.insert(4);
        tree.levelOrder();
        tree.insert(3);
        tree.levelOrder();
        tree.insert(2);
        tree.levelOrder();
        tree.insert(1);
        tree.levelOrder();
    }

}
