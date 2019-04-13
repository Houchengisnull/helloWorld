package org.hc.learning.datastruct.tree.二叉树;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

class BinaryTreeNode {
	// 省事
	public int data;
	public BinaryTreeNode left;
	public BinaryTreeNode right;

	public BinaryTreeNode() {}
	
	public BinaryTreeNode(int data) {
		this.data = data;
	}
}

public class BinaryTreeNodeTest {

	static BinaryTreeNode root = null;
	
	@Before
	public void buildTree() {
		root = new BinaryTreeNode(0);
		root.left = new BinaryTreeNode(1);
		root.right = new BinaryTreeNode(2);
	}

	/**
	 * 以层次遍历方式构建二叉树
	 * 1
	 * queue -> 存放上层结点
	 * 生成 2^n-1 个数据值为n的结点/数据
	 * 2
	 * 设定出口条件
	 * 当n < max
	 */
	public BinaryTreeNode buildTree(BinaryTreeNode root, int amount) {
		root = new BinaryTreeNode(0);
		// TODO 
		LinkedList<BinaryTreeNode> stack = new LinkedList<>();
		BinaryTreeNode temp = root;
		for (int i = root.data ; i < amount ; i++ ){
			
		}
		return root;
	}
	
	/**
	 * 换行
	 */
	@After
	public void soutEnter() {
		System.out.println();
	}
	
	@Test
	public void preOrder() {
		System.out.println("非递归前序遍历");
		preOrderNoRecursive(root);
	}
	
	/**
	 * 中左右
	 * 非递归前续遍历
	 */
	public void preOrderNoRecursive(BinaryTreeNode root) {
		LinkedList<BinaryTreeNode> stack = new LinkedList<>();
		while (true) {
			while (root != null) {
				System.out.print(root.data);
				stack.push(root);
				root = root.left;
			}
			if (stack.size() == 0) {
				break;
			}
			root = stack.pop();
			root = root.right;
		}
	}
	
	@Test
	public void inOrder() {
		System.out.println("非递归中序遍历");
		inOrderNoRecursive(root);
	}
	
	/**
	 * 左中右
	 * 非递归中序遍历
	 * @param root
	 */
	public void inOrderNoRecursive(BinaryTreeNode root) {
		LinkedList<BinaryTreeNode> stack = new LinkedList<>();
		while (true) {
			while (root != null) {
				stack.push(root);
				root = root.left;
			}
			if (stack.size() == 0) {
				break;
			}
			root = stack.pop();
			System.out.print(root.data);
			root = root.right;
		}
	}
	
	@Test
	public void postOrder() {
		System.out.println("非递归后序遍历");
		postOrderNoRecursive(root);
	}
	
	/**
	 * 左右中
	 * 非递归后序遍历
	 * 
	 * 递推与递归不同的是,递推方法采用自底向上的方式产生计算序列,其首先计算规模问题最小的子问题的解,在此基础上依次计算规模较大的子问题的解,直到最后产生原问题的解
	 * 大多数递归问题在求解过程中无法保证求解动作一直向前,往往需要设置一些回溯点
	 * 
	 * 假设存在结点a, a.left -> b, b.right -> c
	 * 其非递归后序遍历流程
	 *  
	 * @param root
	 */
	public void postOrderNoRecursive(BinaryTreeNode root) {
		LinkedList<BinaryTreeNode> stack = new LinkedList<>();
		while (true) {
			if (root != null ){ // 左子树遍历
				stack.push(root);
				root = root.left;
			} else {
				if (stack.size() == 0) {
					return;
				} else {
					if (stack.getFirst().right == null) {
						root = stack.pop();
						System.out.print(root.data);
						if (root == stack.getFirst().right) {
							System.out.print(stack.getFirst().data);
							stack.pop();
						}
					}
				}
				if (stack.size() > 0) {
					root = stack.getFirst().right;
				}else {
					root = null;
				}
			}
		}
	}
}
