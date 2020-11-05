package org.hc.learning.datastruct.tree.二叉树;

import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

class BinaryTreeNode <V>{
	// 省事
	public V data;
	public BinaryTreeNode left;
	public BinaryTreeNode right;

	public BinaryTreeNode(V data) {
		this.data = data;
	}
}

public class BinaryTreeNodeTest {

	static BinaryTreeNode<String> root = null;
	/*static ArrayList<String> list = null;*/

	@Before
	public void buildTree() {
		if (root == null) {
			root = new BinaryTreeNode("A");
			root.left = new BinaryTreeNode("B");
			root.right = new BinaryTreeNode("C");
			root.left.left = new BinaryTreeNode("D");
			root.left.right = new BinaryTreeNode("F");
			root.left.right.left = new BinaryTreeNode("E");
			root.left.right.left.right = new BinaryTreeNode("J");
			root.right.left = new BinaryTreeNode("G");
			root.right.right = new BinaryTreeNode("I");
			root.right.left.right = new BinaryTreeNode("H");
			/*root.right.left.right = new BinaryTreeNode("K");*/
		}
		// root.right.right = new BinaryTreeNode(6);
		/*list = new ArrayList<>();*/
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
		System.out.println("前序遍历-递归实现");
		preOrderRecursive(root);
		System.out.println();
		System.out.println("前序遍历-非递归实现");
		preOrderNoRecursive(root);
	}

	/**
	 * 递归实现前序遍历
	 * @param root
	 */
	public void preOrderRecursive(BinaryTreeNode root) {
		if (root != null) {
			System.out.print(root.data + " ");
			preOrderRecursive(root.left);
			preOrderRecursive(root.right);
		}
	}

	/**
	 * 中左右
	 * 非递归前序遍历
	 * 在前序遍历中，
	 * 由于先输出当前结点，所以右子树上的结点均未输出，
	 * 只要我们将当前结点入栈，表明栈中结点的右子树均未输出
	 * 当左子树遍历完成后，我们取出栈顶结点并回溯到其右结点即可实现前序遍历。
	 */
	public void preOrderNoRecursive(BinaryTreeNode root) {
		LinkedList<BinaryTreeNode> stack = new LinkedList<>();
		while (true) {
			while (root != null) {
				System.out.print(root.data + " ");
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
		System.out.println("中序遍历-递归实现");
		inOrderRecursive(root);
		System.out.println();
		System.out.println("中序遍历-非递归实现");
		inOrderNoRecursive(root);
	}

	public void inOrderRecursive(BinaryTreeNode root) {
		if (root != null) {
			inOrderRecursive(root.left);
			System.out.print(root.data + " ");
			inOrderRecursive(root.right);
		}
	}

	/**
	 * 左中右
	 * 非递归中序遍历
	 *
	 * 在中序遍历中，要求在输出当前结点前先输出左子树
	 * 所以在遍历左子树时先不输出，将其入栈
	 * 在抵达最深处后，先弹出栈栈顶元素，输出当前结点，
	 * 判断如果存在右子树则遍历右子树。
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
			System.out.print(root.data + " ");
			root = root.right;
		}
	}
	
	@Test
	public void postOrderRecursiveTest() {
		System.out.println("后序遍历-递归实现");
		postOrderRecursive(root);
	}

	@Test
	public void postOrderNoRecursiveWithoutBacktrackingTest() {
		System.out.println("后序遍历-非递归实现 无回溯点");
		postOrderNoRecursiveWithoutBacktracking(root);
	}

	@Test
	public void postOrderNoRecursiveWithBacktrackingTest() {
		System.out.println("后序遍历-非递归实现 回溯点");
		postOrderNoRecursiveWithBacktracking(root);
	}

	public void postOrderRecursive(BinaryTreeNode root) {
		if (root != null) {
			postOrderRecursive(root.left);
			postOrderRecursive(root.right);
			System.out.print(root.data + " ");
		}
	}
	
	/**
	 * 左右中
	 * 非递归后序遍历
	 *
	 * 非回溯解决方式
	 * 以前序遍历的非递归实现为基础，实现遍历“右左中”
	 * 将结果记录后逆序输出，即为后序遍历
	 *
	 * @param root
	 */
	public void postOrderNoRecursiveWithoutBacktracking(BinaryTreeNode root) {
		LinkedList<BinaryTreeNode> stack = new LinkedList<>();
		ArrayList<BinaryTreeNode> list = new ArrayList<>();
		while (true) {
			while (root != null) {
				list.add(root);
				stack.push(root);
				root = root.right;
			}
			if (stack.size() == 0) {
				break;
			}
			root = stack.pop();
			root = root.left;
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			System.out.print(list.get(i).data + " ");
		}
	}

	/**
	 * 递推与递归不同的是,递推方法采用自底向上的方式产生计算序列,其首先计算规模问题最小的子问题的解,在此基础上依次计算规模较大的子问题的解,直到最后产生原问题的解
	 * 大多数递归问题在求解过程中无法保证求解动作一直向前,往往需要设置一些回溯点
	 *
	 * 实现后序遍历的关键:在判断当前结点是否为栈顶元素的右子树
	 * 首先同样将左子树均放入栈中至最深处
	 * 输出左子结点后，"获取"栈顶元素的右子结点，如果不为空则遍历其左子树
	 * 输出右子结点后，并且其为栈顶元素的右子结点则设置回溯标识符号，令其下次不进入该结点
	 */
	public void postOrderNoRecursiveWithBacktracking(BinaryTreeNode root) {
		LinkedList<BinaryTreeNode> stack = new LinkedList<>();
		boolean hasBack = false; 															// 是否回溯过
		while (root != null){
			while ((root.left != null || root.right != null) && !hasBack) { 				// 遍历至子节点最深处 且当前结点未回溯过
				stack.push(root);
				root = root.left != null ? root.left : root.right;
			}
			System.out.print(root.data + " "); /*list.add(root);*/
			if (stack.size() > 0) { 														// 如果栈中有元素
				if (stack.getFirst().right == null || root == stack.getFirst().right) { 	// 首先判断是否已经回溯:右结点为空 或者 当前结点为栈顶元素的右子树
					hasBack = true;
					root = stack.pop();
				} else { 																	// 否则将右子树设置为root以便进行遍历:right 不为空 且 未回溯
					hasBack = false;
					root = stack.getFirst().right;
				}
			} else
				break;
		}
	}

	@Test
	public void levelOrderTest() {
		levelOrder(root);
	}

	/**
	 * 层次遍历
	 * 利用队列先进先出的思想，实现层次遍历
	 * 尽管可以按照正确的层次遍历输出，但无法区分节点具体在哪一层次
	 * @param root
	 */
	public void levelOrder(BinaryTreeNode root) {
		BinaryTreeNode temp = null;
		LinkedList<BinaryTreeNode> queue = new LinkedList<>();
		if (root == null) {
			return;
		}
		queue.add(root);
		while (!queue.isEmpty()) {
			temp = queue.poll();
			System.out.println(temp.data);
			if (temp.left != null) {
				queue.add(temp.left);
			}
			/*
			 * 由于右子节点会比其同一层次的左子节点的子节点先出列
			 */
			if (temp.right != null) {
				queue.add(temp.right);
			}
		}
	}

	@Test
	public void getMaxDepthTest() {
		System.out.println(getMaxDepth(root));
		System.out.println(getMaxDepthNoRecursive(root));
	}

	/**
	 * 计算树的深度
	 * @param root
	 * @return
	 */
	public int getMaxDepth(BinaryTreeNode root) {
		int leftDepth, rightDepth;
		if (root == null) {
			return 0;
		}
		leftDepth = getMaxDepth(root.left);
		rightDepth = getMaxDepth(root.right);
		if (leftDepth > rightDepth) {
			return leftDepth + 1;
		}
		return rightDepth + 1;
	}

	/**
	 * 非递归计算树的深度
	 * 以空作为分隔符号，在遍历完每次后添加空，当弹出空时level+=1
	 * @param root
	 * @return
	 */
	public int getMaxDepthNoRecursive(BinaryTreeNode root) {
		int level = 0; // 层数

		LinkedList<BinaryTreeNode> queue = new LinkedList<>();
		if (root == null) {
			return level;
		}
		queue.add(root);
		queue.add(null);
		while (!queue.isEmpty()) {
			root = queue.poll();
			// 当前层遍历结束
			if (root == null) {
				if (!queue.isEmpty()) {
					queue.add(null);
				}
				level++;
			} else {
				if (root.left != null) {
					queue.add(root.left);
				}
				if (root.right != null) {
					queue.add(root.right);
				}
 			}
		}
		return level;
	}
}
