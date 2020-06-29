[toc]

# List、Set、Map应用场景对比

| 容器 | 特点     |
| ---- | -------- |
| List | 元素顺序 |
| Set | 元素唯一 |
| Map | 键值对存储（用Key来搜索） |

# 底层数据结构总结

## Collection

### List

- **ArrayList:**	基于数组实现，非线程安全 
- **Vector:**	基于数组实现，线程安全   
- **LinkedList:**	双向链表                 

### Set

- **HashSet(无序唯一):**	基于`HashMap`
- **LinkedLishSet:**	继承于`HashSet`，内部通过`LinkedHashMap`来实现
- **TreeSet(有序唯一):**	红黑树（自平衡的排序二叉树）

## Map

- **HashMap:**	散列链表，在链表过长时转化为红黑树
- **Hashtable:**	散列链表
- **TreeMap:**	红黑树

# List

## ArrayList与LinkedList区别

- 均不保证线程安全

- 底层数据结构不同，ArrayList由数组实现，LinkedList通过双向链表实现

- 插入和删除元素是否受到元素位置影响

  - ArrayList采用数组存储，所以插入和删除元素的时间复杂度受元素位置影响大
    - 末尾位置时间复杂度位O(1)
    - **i**位置位O(n-i)
  - LinkedList采用链表存储，所以插入与删除元素时间复杂度不受元素位置影响，近似O(1)

- ArrayList支持快速随机访问，LinkedList不支持

  > 快速访问即通过元素序号快速获取元素对象

- 内存空间占用

  - ArrayList的空 间浪费主要体现在在list列表的结尾会预留一定的容量空间

  - 而LinkedList的空间花费则体现在它的每一个元素都需要消耗比ArrayList更多的空间（因为要存放直接后继和直接前驱以及数据）。

# Map

## HashMap与Hashtable区别

- Hashtable继承自Dictionary；HashMap继承自AbstractMap（两者均实现Map接口）

- Hashtable多提供了elements()、contains()

- Hashtable不允许Key或Value为空，而HashMap允许为空。

- 初始容量大学和每次扩充容量大学不同

  - Hashtable初始大小为11，每次扩充变为原来的2n+1
  - HashMap初始大小为16，每次扩充为原来的2倍

- 计算Hash方式不同

  - HashMap通过hash()——通过`key.hashCode`右移16位再与之异或运算`^`
  - Hashtable直接通过key.hashCode()得到Hash值

- 解决Hash地址冲突方式不同

  在JDK8中，HashMap解决方式如下：

  - 若冲突数量小于8，以链表方式解决
  - 大于或等于8，将冲突Entry转换位红黑树进行存储
  - 而又当数量小于6时，则又转化位链表存储

  而Hashtable则一直以链表方式存储。
