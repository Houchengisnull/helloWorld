[toc]

![HashMap](..\images\java.容器\HashMap.png)

# 使用`HashMap`的目的

存储`键值对`<Key-Value>

# 读写数据

既然涉及存储`键值对`，就需要对数据进行读写。我们在使用`HashMap`时，读写最常用的两个方法是：

- **读**	`public V get(Object key)`
- **写**	`public void put(Object key, Object value)`

## 写入数据

`HashMap`使用散列算法以空间换时间，令我们在可以用接近`随机访问RandomAccess`的方式读取数据。

### 哈希碰撞

由于使用散列算法，那么就可能产生`哈希碰撞`。为了避免`哈希碰撞`带来的问题，我们常常用

- 开放寻址法

  仅使用数组结构，当发生冲突时，地址进行一定偏移后得到一个新的地址。

- 链地址法/拉链法

  数组与链表结合使用，又称为`散列链表`。
  
- 再散列法

  散列时发生冲突，则再散列直到不冲突为止。

而在`HashMap`中使用了`散列链表`。

### 扩容

- **扩容目的**

  已知`HashMap`使用`散列链表`避免哈希冲突。首先采用数组存放元素<Value>，当存取元素较多时，数组就需要扩容以存放更多元素。

  ``` java
  transient Node<K, V>[] table;
  ```

- **扩容时机**

  当`HashMap`的`Node`的数量超过`threshold`[^threshold]，`threshold`的值是`capacity * loadFactor`。

  [^threshold]:阈值。

- **为什么容量是2的倍数**

  ``` java
  i = (n-1) & hash
  ```

  首先在JDK中，通过取余运算来完成`hash寻址`。

  其次当且仅当n=2，`(n-1) & hash`等价于`hash % n`。

  再者使用位运算取代取余运算，可以提高运行效率。

  综上所述，HashMap的容量设置为`2的倍数`。

- **为什么扩容采用2^n**

  另外，扩容采用2^n，可以<u>有效减少元素位置的移动。</u>元素要么位置不变，要么有规律移动（2次幂的偏移量）。

  > 扩容利用2次幂来使元素位置要么不变，要么有规律移动，与容量是否为2的倍数无关。例如：3,6,12同样可以保证以上结果，但不能保证`(n-1)^hash`仍然为取余操作。

## 读取数据

### 遍历方式

- **Iterator**:	迭代器
- **For Each**
- **Lambda**:	JDK1.8+
- **Streams API**

`HashMap`主要可分为以上四种遍历方式，又根据`EntrySet`与`KeySet`共可分为7种：

- **Iterator**：	`EntrySet`

``` java
Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
while(iterator.hasNext()) {
    Map.Entry<Integer, String> entry = iterator.next();
    // 逻辑代码
}
```

- **Iterator**：	`KeySet`

``` java
Iterator<Integer> iterator = map.keySet().iterator();
while (iterator.hasNext()) {
    Integer key = iterator.next();
    String value = map.get(key);
    // service
}
```

- **For Each**：	`EntrySet`

``` java
for (Map.Entry<Integer, String> entry : map.entrySet()) {
	// service
}
```

- **For Each**：	`KeySet`

``` java
for (Integer key : map.keySet()) {
	// service
}
```

- **Lambda**

``` java
map.forEach((key, value) -> {
    // serivce
});
```

这种方式不可修改方法体外参数，存在一定局限性。

- **Streams API**：	单线程

``` java
map.entrySet().stream.forEach((entry) -> {
    // service
});
```

- **Streams API**：	多线程

``` java
map.entrySet().parallelStream().forEach((entry) -> {
    // service
});
```

#### 运行效率

参考`org.hc.learning.datastruct.map.HashMapMicrobenchmark`代码运行结果，除并发条件下，其他遍历方式相差不大。

对编译后的字节码进行反编译我们可以看到，`iterator`与`forEach`相差不大。

``` java
public static void entrySet() {
    Iterator var0 = map.entrySet().iterator();
    while(var0.hasNext()) {
        Entry var1 = (Entry)var0.next();
        // service
    }
}
public static void forEachEntrySet() {
    Iterator var0 = map.entrySet().iterator();
    while(var0.hasNext()) {
        Entry var1 = (Entry)var0.next();
        // service
    }
}
```

> **所以通过字节码来看，使用** `EntrySet` **和** `KeySet` **代码差别不是很大，并不像网上说的那样** `KeySet` **的性能远不如** `EntrySet`**，因此从性能的角度来说** `EntrySet` **和** `KeySet` **几乎是相近的，但从代码的优雅型和可读性来说，还是推荐使用** `EntrySet`**。**

#### 安全性

- `Lambda`删除数据

  ``` java
  map.keySet().removeIf(key -> key == 1);
  map.forEach((key, value) -> {
      System.out.println("show:" + key);
  });
  ```

  先使用 `Lambda` 的 `removeIf` 删除多余的数据，再进行循环是一种正确操作集合的方式。

- `stream`

  ``` java
  map.entrySet().stream().filter(m -> 1 != m.getKey()).forEach((entry) -> {
      if (entry.getKey() == 1) {
          System.out.println("del:" + entry.getKey());
      } else {
          System.out.println("show:" + entry.getKey());
      }
  });
  ```

  使用 Stream 中的 `filter` 过滤掉要删除的数据进行循环。

# 源码分析

## 成员常量与成员变量

``` java
public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable {
    // 序列号
    private static final long serialVersionUID = 362498820763181265L;    
    // 默认的初始容量是16
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;   
    // 最大容量
    static final int MAXIMUM_CAPACITY = 1 << 30; 
    // 默认的填充因子
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    // 当桶(bucket)上的结点数大于这个值时会转成红黑树
    static final int TREEIFY_THRESHOLD = 8; 
    // 当桶(bucket)上的结点数小于这个值时树转链表
    static final int UNTREEIFY_THRESHOLD = 6;
    // 桶中结构转化为红黑树对应的table的最小大小
    static final int MIN_TREEIFY_CAPACITY = 64;
    // 存储元素的数组，总是2的幂次倍
    transient Node<k,v>[] table; 
    // 存放具体元素的集
    transient Set<map.entry<k,v>> entrySet;
    // 存放元素的个数，注意这个不等于数组的长度。
    transient int size;
    // 每次扩容和更改map结构的计数器
    transient int modCount;   
    // 临界值 当实际大小(容量*填充因子)超过临界值时，会进行扩容
    int threshold;
    // 加载因子
    final float loadFactor;
}
```

## 确定哈希桶数组索引位置

| 符号 | 作用                  |
| ---- | --------------------- |
| >>   | 右移，高位补充符号位  |
| >>>  | 无符号右移，高位仅补0 |

`HashMap`中算法本质即三步：

1. 取<Key>的`hashCode`
2. 高位参与运算
3. 取模运算

### 扰动函数`hash()`

``` java
    static final int hash(Object key) {
      int h;
      // h = key.hashCode() 为第一步 取hashCode值
      // h ^ (h >>> 16)  为第二步 高位参与运算
      return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }
```

- 目的

  减少碰撞

- 步骤

  1. `h = key.hashCode())` 获取`hashCode`

  2. `h ^ (h >>> 16)` 高位参与运算

     ![hashmap.1.确定索引位置](../images/java.容器/hashmap.1.确定索引位置.png)

### `indexFor()`

> `JDK1.7`中的源码，`JDK1.8`中实现原理相同

``` java
static int indexFor(int h, int length) {
     //第三步 取模运算
     return h & (length-1);  
}
```

通过扰动函数完成第一、二步后，我们需要通过**模运算**将元素**分布相对均匀地**保存到`table`上。

但是由于使用`%`往往消耗较大，为了提高运算效率。首先保证`table`的长度总为2的n次方，在这种条件下，`h & (length - 1)`等价于`h % length`。

> `&`比`%`具有更高的效率。

## 写入`put()`

- `public void put(Object key, Object value)`

``` java
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
```

该方法调用了`putVal`方法。

## 写入核心方法`putVal()`

``` java
/**
* @param hash 经过扰动函数`hash()`得到的哈希值
* @param onlyIfAbsent 仅在缺失条件下保存元素
* @param evict 是否驱逐 || 为false时, table为创造模式
*/
final V putVal(int hash
               , K key
               , V value
               , boolean onlyIfAbsent
               , boolean evict) {
 	Node<K,V>[] tab; Node<K,V> p; int n, i;
    // table未初始化或者长度为0，进行扩容
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    // (n - 1) & hash 确定元素存放在哪个桶中，桶为空，新生成结点放入桶中(此时，这个结点是放在数组中)
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    // 桶中已经存在元素
    else {
        Node<K,V> e; K k;
        // 比较桶中第一个元素(数组中的结点)的hash值相等，key相等
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
                // 将第一个元素赋值给e，用e来记录
                e = p;
        // hash值不相等，即key不相等；为红黑树结点
        else if (p instanceof TreeNode)
            // 放入树中
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        // 为链表结点
        else {
            // 在链表最末插入结点
            for (int binCount = 0; ; ++binCount) {
                // 到达链表的尾部
                if ((e = p.next) == null) {
                    // 在尾部插入新结点
                    p.next = newNode(hash, key, value, null);
                    // 结点数量达到阈值，转化为红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    // 跳出循环
                    break;
                }
                // 判断链表中结点的key值与插入的元素的key值是否相等
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    // 相等，跳出循环
                    break;
                // 用于遍历桶中的链表，与前面的e = p.next组合，可以遍历链表
                p = e;
            }
        }
        // 表示在桶中找到key值、hash值与插入元素相等的结点
        if (e != null) { 
            // 记录e的value
            V oldValue = e.value;
            // onlyIfAbsent为false或者旧值为null
            if (!onlyIfAbsent || oldValue == null)
                //用新值替换旧值
                e.value = value;
            // 访问后回调
            afterNodeAccess(e);
            // 返回旧值
            return oldValue;
        }
    }
    // 结构性修改
    ++modCount;
    // 实际大小大于阈值则扩容
    if (++size > threshold)
        resize();
    // 插入后回调
    afterNodeInsertion(evict);
    return null;
}
```

## 扩容机制

- **扩容条件：** 当`table`长度大于`threshold`后，调用`resize()`进行扩容

- `threshold` =  `capacity` * `loadFactor`

  - `capacity` 当前容量，默认16
  - `loadFactor` 加载因子，默认0.75f。

  > loadFactor加载因子是控制数组存放数据的疏密程度，loadFactor越趋近于1，那么   数组中存放的数据(entry)也就越多，也就越密，也就是会让链表的长度增加，loadFactor越小，也就是趋近于0，数组中存放的数据(entry)也就越少，也就越稀疏。
  >
  > 
  >
  > **loadFactor太大导致查找元素效率低，太小导致数组的利用率低，存放的数据会很分散。loadFactor的默认值为0.75f是官方给出的一个比较好的临界值**。
  >
  > 
  >
  > 给定的默认容量为 16，负载因子为 0.75。Map 在使用过程中不断的往里面存放数据，当数量达到了 16 * 0.75 = 12 就需要将当前 16 的容量进行扩容，而扩容这个过程涉及到 rehash、复制数据等操作，所以非常消耗性能。

### JDK1.7

#### `resize()`

``` java
void resize(int newCapacity) {   //传入新的容量
     Entry[] oldTable = table;    //引用扩容前的Entry数组
     int oldCapacity = oldTable.length;         
     if (oldCapacity == MAXIMUM_CAPACITY) {  //扩容前的数组大小如果已经达到最大(2^30)了
         threshold = Integer.MAX_VALUE; //修改阈值为int的最大值(2^31-1)，这样以后就不会扩容了
         return;
     }
  
     Entry[] newTable = new Entry[newCapacity];  //初始化一个新的Entry数组
     transfer(newTable);                         //！！将数据转移到新的Entry数组里
     table = newTable;                           //HashMap的table属性引用新的Entry数组
     threshold = (int)(newCapacity * loadFactor);//修改阈值
 }
```

#### `transfer()`

``` java
 void transfer(Entry[] newTable) {
     Entry[] src = table;                   //src引用了旧的Entry数组
     int newCapacity = newTable.length;
     for (int j = 0; j < src.length; j++) { //遍历旧的Entry数组
         Entry<K,V> e = src[j];             //取得旧Entry数组的每个元素
         if (e != null) {
             src[j] = null;//释放旧Entry数组的对象引用（for循环后，旧的Entry数组不再引用任何对象）
             do {
                 Entry<K,V> next = e.next;
                 int i = indexFor(e.hash, newCapacity); //！！重新计算每个元素在数组中的位置
                 e.next = newTable[i]; //标记[1]
                 newTable[i] = e;      //将元素放在数组上
                 e = next;             //访问下一个Entry链上的元素
             } while (e != null);
         }
     }
 }
```

将原旧中元素转换给扩容之后的新数组。

> JDK1.7中HashMap在扩容时采用`头插法`。

### JDK1.8

#### `resize()`

```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;
    if (oldCap > 0) {
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    else {               // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    if (oldTab != null) {
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```

# 加密应用

由于哈希算法不可逆，则在业务上常常用于对明文加密。

## 加盐（salt）

由于市场上存在诸多`彩虹表`，暴力破解明文。所以在实际场景中，我们常常会对明文进行加盐处理。

- 密文 = hash(明文 + 盐)

# 参考

https://snailclimb.gitee.io/javaguide/#/docs/java/collection/Java集合框架常见面试题?id=listsetmap

- `HashMap`底层实现

https://zhuanlan.zhihu.com/p/149259104

https://zhuanlan.zhihu.com/p/21673805