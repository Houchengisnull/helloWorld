package org.hc.learning.datastruct.tree.二叉树;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

/**
 * 平衡二叉排序树 - AVL树
 */
@Getter
@Setter
public class AVLTree<K extends Comparable<K>> {
    private AVLTreeNode<K> root;

    private int height(AVLTreeNode<K> tree) {
        return tree == null ? 0 : tree.height;
    }

    @Getter
    @Setter
    class AVLTreeNode<K extends Comparable<K>> {
        private K key;
        private int height; // 结点到最深结点的路径长度
        private AVLTreeNode<K> left;
        private AVLTreeNode<K> right;

        public AVLTreeNode(K key) {
            this.key = key;
            this.height = 0;
        }
    }

    private AVLTreeNode insert(AVLTreeNode<K> node, K key) {
        if (node == null) {
            node = new AVLTreeNode<>(key);
        } else {
            int c = key.compareTo(node.getKey());
            if (c < 0) { // 将key插入到左子树
                node.left = insert(node.left, key);
                if (height(node.left) - height(node.right) > 1) {
                    if (key.compareTo(node.left.key) < 0) {
                        node = leftLeftRotation(node);
                    } else {
                        node = leftRightRotation(node);
                    }
                }
            } else {
                node.right = insert(node.right, key);
                if (height(node.right) - height(node.left) > 1) {
                    if (key.compareTo(node.right.key) < 0) {
                        node = rightRightRotation(node);
                    } else {
                        node = rightLeftRotation(node);
                    }
                }
            }
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }

    public void insert(K key) {
        root = insert(root, key);
    }

    /**
     * 左左旋转
     * @param node
     * @return
     */
    private AVLTreeNode<K> leftLeftRotation(AVLTreeNode<K> node) {
        AVLTreeNode<K> left = node.left;
        node.left = left.right;
        left.right = node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        left.height = Math.max(height(left.left), height(left.right)) + 1;
        return left;
    }

    private AVLTreeNode<K> rightRightRotation(AVLTreeNode<K> node) {
        AVLTreeNode<K> right = node.right;
        node.right = right.left;
        right.left = node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        right.height = Math.max(height(right.left), height(right.right)) + 1;
        return right;
    }

    private AVLTreeNode<K> leftRightRotation(AVLTreeNode<K> node) {
        node.left = rightRightRotation(node.left);
        return leftLeftRotation(node);
    }

    private AVLTreeNode<K> rightLeftRotation(AVLTreeNode<K> node) {
        node.right = leftLeftRotation(node);
        return rightRightRotation(node);
    }

    public void levelOrder() {
        AVLTreeNode temp = null;
        LinkedList<AVLTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            temp = queue.poll();
            System.out.print(temp.getKey() + "\t");
            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
        }
        System.out.println();
    }

}
