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

## HashMap	

### 底层数据结构

`数组` + `链表` + `红黑树`。

这种数据结构又被称为`散列链表`。其中数组相当于一个一个的桶（Bucket）。

**当写入数据时**，它会通过一定的散列算法去计算，尽可能的让数据平均的命中到各个桶上面去以避免`哈希碰撞`。

**当发生`哈希碰撞`时**，则采用链表方式存储。

**当链表过长时，读取效率降低**，在`JDK8`后做了优化，当链表长度大于8则转化为`红黑树`。

### hash()

```java
static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```

- 补充知识

  | 符号 | 作用                                                         |
  | ---- | ------------------------------------------------------------ |
  | >>   | 右移，如果该数为正，则高位补0，若为负数，则高位补1。         |
  | \>>> | 无符号右移，即若该数为正，则高位补0，而若该数为负数，则右移后高位同样补0。 |

关键代码在于`(h = key.hashCode()) ^ (h >>> 16)`。

1. 对变量h进行赋值运算，`h = key.hashCode()`；
2. 对变量h进行有无符号右移16位，即高位补零16位；
3. 已知int变量为32位，将h无符号右移16为相当于将高区16位移动到了低区的16位，再与原hashcode做异或运算，**可以将高低位二进制特征混合起来**；
4. 从下图可知高区的16位与原hashcode相比没有发生变化，仅低区的16位发生了变化；

![img](https://github.com/Houchengisnull/helloWorld/documents/images/java.容器/hash.jpg)

- 为什么这么做呢？

  重新计算出的**新哈希值**将参与`HashMap`中的数组槽位的计算，计算公式`(n-1) & hash`。

  ![img](https://github.com/Houchengisnull/helloWorld/documents/images/java.容器/槽位计算.jpg)

  高区的16位很有可能会被数组槽位数的二进制码锁屏蔽，**如果我们不做刚才移位异或运算，那么在计算槽位时将丢失高区特征**

  也许你可能会说，即使丢失了高区特征不同`hashcode`也可以计算出不同的槽位来，但是细想当两个哈希码很接近时，那么这高区的一点点差异就可能导致一次哈希碰撞，所以这也是将性能做到极致的一种体现。

- 使用异或运算的原因

  异或运算能更好的保留各部分的特征，如果采用&运算计算出来的值会向1靠拢，采用|运算计算出来的值会向0靠拢
  
- 为什么槽位数必须使用2^n

  为了使分布更加均匀。

### put()

```java
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
```

调用`putVal()`。

- `putVal()`

```java
 /**
     * Implements Map.put and related methods
     *
     * @param hash hash for keyput
     * @param onlyIfAbsent if true, don't change existing value
     * @param evict if false, the table is in creation mode.
     * @return previous value, or null if none
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        /**
         * 判断tab是不是为空,如果为空,则将容量进行初始化,也就是说,初始换操作不是在new HashMap()的时候进行的,而是在第一次put的时候进行的
         */
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
 
        /**
         * 初始化操作以后,根据当前key的哈希值算出最终命中到哪个桶上去，并且这个桶上如果没有元素的话,则直接new一个新节点放进去
         */
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
 
        /**
         * 如果对应的桶上已经有元素
         */
        else {
            Node<K,V> e; K k;
            /** 先判断一下这个桶里的第一个Node元素的key是不是和即将要存的key值相同，如果相同,则            
             *把当前桶里第一个Node元素赋值给e,这个else的最下边进行了判断，如果e!=null就执行把
             * 新value进行替换的操作 
             */
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            //如果和桶里第一个Node的key不相同,则判断当前节点是不是TreeNode(红黑树),如果是,则进 
            //行红黑树的插入
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
 
            //如果不是红黑树,则循环链表，把数据插入链表的最后边
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        //判断元素个数是不是大于等于8
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            //转换成红黑树
                            treeifyBin(tab, hash);
                        break;
                    }
 
                    /**
                     * 如果在遍历的时候，发现key值相同（就是key已经存在了）就什么都不做跳出循环。因为在上边e = p.next的时候，已经记录e的Node值了，而下边进行了判断，如果e!=null就执行把新value进行替换的操作
                     */
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    
                    //把当前下标赋值给p并进行下一次循环
                    p = e;
                }
            }
 
            /**
              只要e不为空,说明要插入的key已经存在了,覆盖旧的value值,然后返回原来oldValue
              因为只是替换了旧的value值，并没有插入新的元素，所以不需要下边的扩容判断，直接 
               return掉
             */
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        /**
         * 判断容量是否已经到了需要扩充的阈值了,如果到了,则进行扩充
         * 如果上一步已经判断key是存在的，只是替换了value值，并没有插入新的元素，所以不需要判断 
         * 扩容，不会走这一步的
         */
        if (++size > threshold)
            resize();
 
        afterNodeInsertion(evict);
        return null;
    }
```

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

# 参考

https://snailclimb.gitee.io/javaguide/#/docs/java/collection/Java集合框架常见面试题?id=listsetmap

- `HashMap`底层实现

https://zhuanlan.zhihu.com/p/149259104