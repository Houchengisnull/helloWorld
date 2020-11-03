[TOC]

# 术语

* **根结点：**	无双亲的结点，一棵树最多一个根结点

* **边：**	双亲与孩子结点的链接
* **叶子结点：**	无孩子结点的结点
* **兄弟结点：**	拥有相同双亲的结点
* **祖先结点：**	如果存在一条根结点到结点q的路径，并且p在该路线上，则p为q的祖先结点
* **结点大小：**	子孙个数，包括其自身
* **层：**	位于相同深度的所有结点的集合叫做树的层
* **结点深度：**	根结点到该结点的路径长度

- **结点高度：**	从该结点到最深结点的路径长度
- **树的高度：**	树中所有结点高度最大值 / 根结点到最深结点的路径长度

  > 深度与高度为分别以***根结点***与***最深结点***为***起点***的标量

- **斜树：**	类链表的树，即除叶子结点每个结点仅一个孩子结点



# 二叉树

## 类型

- **严格二叉树：**	除叶子结点外，每个结点均有两个孩子结点

- **满二叉树：**	所有叶子结点都在同一层的严格二叉树
- **完全二叉树：**	从左到右自上而下结点有序无遗漏的二叉树

## 性质

https://www.cnblogs.com/willwu/p/6007555.html

- 如果A、B互为镜像，那么A的后序遍历的逆序为B以右结点开始的前序遍历。 

## 结构

```java
public class BinaryTreeNode {
	// 省事
	public int data;
	public BinaryTreeNode left;
	public BinaryTreeNode right;
	
}
```

## 常用操作

- 基本操作
  - 插入
  - 删除
  - 查找
  - 遍历

- 辅助操作
  - 获取树的大小
  - 获取树的高度
  - 获取其和最大的层
  - 对于两个或多个结点，找出他们最近的公共结点

## 应用

* 编译器表达式树
* 数据压缩算法中的哈夫曼树
* 支持在集合中查找，插入和删除，其平均时间复杂度为O(logn)的二叉搜索查找树（BST）
* 优先队列（PQ），它支持以对数时间（最坏情况）对集合中的最小或最大数据进行搜索和删除

## 遍历

### 非递归前序遍历  

```java
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
```

### 非递归中序遍历  

```java
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
```

### 非递归后序遍历  

```java
/**
	 * 左右中
	 * 非递归后序遍历
	 * 
	 * 递推与递归不同的是,递推方法采用自底向上的方式产生计算序列,其首先计算规模问题最小的子问题的解,在此基础上依次计算规模较大的子问题的解,直到最后产生原问题的解
	 * 大多数递归问题在求解过程中无法保证求解动作一直向前,往往需要设置一些回溯点
	 * 
	 * 实现关键: 
	 * 检查这个元素与栈顶元素的右子结点是否相同
	 * 如果相同，则说明已经完成左右子树遍历。
	 * 此时,只需要将栈顶元素出栈一次并输出该结点数据即可。
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
					if (stack.getFirst().right == null) { // 
						root = stack.pop();
						System.out.print(root.data); // 输出当前结点
						if (root == stack.getFirst().right) { // 当前元素是否为栈顶元素右子节点
							System.out.print(stack.getFirst().data);
							stack.pop();
						}
					}
				}
				if (stack.size() > 0) { // stack中的尾结点为root, 如果栈中仍然有数据说明刚刚输出了左子节点，下一步应该是输出右子树或遍历右子树的左子节点
					root = stack.getFirst().right; // 找到同一层次的右结点
				}else {
					root = null;
				}
			}
		}
	}
```

- 非递归后序遍历（无回溯点）

我在网上见到一个十分简便也易于理解的方法，其基本思路是这样的：**后序遍历的顺序为左右中，而前序遍历的顺序为中左右，如果在前序遍历的基础上，先遍历右子节点，再遍历左子节点，那么输出顺序为中右左，恰好为后序遍历的逆序输出。**

代码见：`org.hc.learning.datastruct.tree.二叉树.BinaryTreeNode.postOrderNoRecursiveWithoutBacktracking`

- 非递归后序遍历（回溯点）

  ```java
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
              if (stack.getFirst().right == null || root == stack.getFirst().right) { 	// 首先判断是否已经回溯:右结点为空 或者 当前当前root为栈顶元素的右子树
                  hasBack = true;
                  root = stack.pop();
              } else { 																	// 否则将右子树设置为root以便进行遍历:right 不为空 且 未回溯
                  hasBack = false;
                  root = stack.getFirst().right;
              }
          } else
              break;
  }
  ```

# 通用树（N叉树）

由于一棵树中有多个结点，难以表示，包括数据结构难以定义。

``` java
public class TreeNode {
    public int value; // 值
    public TreeNode firstChild;
    public TreeNode secondChild;
    public TreeNode thirdChild;
    ......
}
```

所以通常，在多路树中：

- 同一个双亲结点的孩子结点从左到右排列
- 双亲结点只保存第一个孩子结点，删除从双亲到其他孩子结点的连接。

> 世界上本来没有二叉树，神用一刀分天地，从通用树里就单单拎出了二叉树。

# 线索二叉树

在常规二叉树的`前序遍历`、`后序遍历`、`中序遍历`中，通常使用`栈`作为辅助数据结构；

而在常规二叉树的`层次遍历`中，使用`队列`作为辅助数据结构。

## 常规二叉树的问题

- 栈和队列需要的存储空间很大。
- 存在大量空指针，它们占用的空间被浪费了。
- 难以确定给定结点的后继结点。

因此，我们可以利用这些空指针，将遍历的前驱信息与后继信息放在这些空指针。这样常规二叉树就变成了一棵`线索二叉树`。

## 线索二叉树的类型

- **前序线索二叉树：**	空左指针存储**前序序列**的前驱信息，空右指针将存储**前序序列**的后继信息。
- **中序线索二叉树：**	空左指针存储**中序序列**的前驱信息，空右指针将存储**中序序列**的后继信息。
- **后序线索二叉树：**	空左指针存储**后序序列**的前驱信息，空右指针将存储**后序序列**的后继信息。

# 表达式树

用来表达`表达式`的树。

# 异或树

不需要栈或队列来遍历书，这种表示利用⊕（异或符号）来进行后向遍历（遍历至双亲）或前向遍历（至孩子结点）。

- 每个结点的`left`字段为其双亲与其左孩子的异或。
- 每个结点的`right`字段为其双亲与其右孩子的异或。
- 根结点的字段结点为空，并且叶子结点的孩子结点也是空。

> 关键利用了异或运算，好像是数字电路还是离散数学这门课上曾经讲过：
>
> - `(1^0)^0=1`
>
> - `(1^0)^1=0`