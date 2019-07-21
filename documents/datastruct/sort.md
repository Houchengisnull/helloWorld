[TOC]

# 基本介绍

## 排序的分类

- 比较的次数

- 交换的次数

- 内存的使用

- 稳定性

  每次排序后，不影响其他符合`顺序`的`元素位置`

- 内排序/外排序

  - 内部排序：排序时仅使用`主存储器(内存)`的排序算法称为内部排序

  - 外部排序：使用硬盘等外存储器排序的算法属于外部排序

# 冒泡排序

`bubble sort`，最简单的排序算法。

``` java
/**
     * 升序
     */
    @Override
    public void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length-1-i; j++) {
                int temp;
                if (array[j] < array[j+1]){
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }    
    }
```

即使在最好的情况下，该算法复杂度为O()*相比其他排序算法冒泡排序的唯一显著优势时可以检测输入的序列是否已经时排序的。*

# 选择排序

`seletion sort`是一种原地(in-place)排序算法，适用于小文件。

## 优点

- 易实现
- 原地排序（不需要额外的存储空间）

``` java

public class SeletionSort extends Sort<Integer>{

    public SeletionSort(Integer[] array) {
        super(array);
    }

    @Override
    public void sort() {
        for (int i = 0; i < array.length; i++) {
            int max = array[i];
            for (int j = i+1; j < array.length; j++) {
                if (max < array[j]){
                    swap(i, j);
                }
            }
        }
    }

    @Override
    public void swap(int posFront, int posBack) {
        int temp = array[posFront];
        array[posFront] = array[posBack];
        array[posBack] = temp;
    }

    /**
     * 10万数据耗时16703ms
     * @param args
     */
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        Integer[] array = MakeArray.makeArray();
        SeletionSort sort = new SeletionSort(array);
        sort.sort();
        sort.output(array);
        System.out.println("耗时" + (System.currentTimeMillis()-l) + "ms");
    }
}
```

# 插入排序

`insertion sort`，每次迭代过程中算法随机地从输入序列中移除一个元素，并将该元素插入待排序地正确位置。重复该过程，直到所有输入元素都被选择一次。

- 例如

  给定一个序列 6 8 1 4 5

  第一次排序：6

  第二次排序：6 8

  第三次排序：1 6 8

  第四次排序：1 4 6 8

  第五次排序：1 4 5 6 8

## 优点

- 实现简单
- 数据量较少时效率较高
- 算法实际运行效率优于选择排序和冒泡排序，即使在最坏情况下三个算法的时间复杂度均为O(n^2)
- 稳定
- 原地：仅需要常量O(1)的辅助内存空间
- 即时：插入排序能够在**接受序列的同时**对其进行排序

> 该算法最好结合链表实现。

插入排序是典型的原地排序，**经过k次迭代后数组具有性质：前k+1个元素已经排序。**

## 适用场景

- 链表
- 手动输入序列

## 与其他算法比较

在**数据几乎已经排序**和输入数据规模较小时可以使用插入排序。由于上述原因以及插入排序的稳定性，插入排序可用于归并和快速排序等高开销的分治排序算法。

其次，尽管`冒泡排序`、`选择排序`与`插入排序`同为O(n^2)的排序算法，但实际运行效率高于`冒泡排序`与`选择排序`。

![1563373927782](https://github.com/Houchengisnull/helloWorld/blob/master/documents/images/algorithm/sort/sort.png)

# 快速排序

快速排序算法，又名分区交换排序算法。

## 基本思路

​	从n个待排序的记录中任取一个记录（不妨取第1个记录），设法将该记录放置于排序后它最终应该放的位置，使它前面的记录排序码不大于它的排序码，而后面的记录排序码都大于它的排序码，然后对前、后两部分待排序记录重复上述过程，可以将所有记录放于排序成功后的相应位置，排序即告完成。

## 实现

将思路转换成可执行的代码，需要一定的编程经验。

 

首先，理清算法的基本思路，分区交换排序算法思路可分为：

1、从n个待排序的记录中任取一个记录（不妨取第1个记录）；

2、设法将该记录放置于排序后它最终应该放的位置；

​    位置要求：它前面的记录排序码不大于它；（前部分）

​                    它后面的记录排序码都大于它；（后部分）

3、然后对前、后部份待排序记录重复上述过程；

 

假设有数组static int[] array={126,272,8,165,123,12,28};

首先需要对数组操作的方法static void quicksort（）｛｝

我们看思路1：从n个待排序的记录中任取一个记录（不妨取第1个记录）；

则在方法体里写：

int tab=array[0];

再看思路2：设法将该记录放置于排序后它最终应该放的位置；

那么，我们只要将其他所有记录排序与tab做比较，比tab小的记录放在tab的左边任何位置就可以了:

for(int i=1;i<array.size();i++){

​    if(tab>array[i]){

​        moveLeft(i,location)；//i为当前比tab小的记录在array中的位置，location为tab左边某位置，我们就把它设为数组最左端好了

​        //因为当array[i]放置在最左端时候，i之前的记录数量不变，所以不影响结果

​    }

}

static void moveLeft(int i,int location){

​        int tab=array[location];
​        for(int i=location;i>move;i--){
​            array[i]=array[i-1];
​        }
​        array[move]=tab;

}

然后思路1、2就完成了

思路3需要重复上述过程，那么我们就需要注意方法的结束条件，

我们可以认为：当前后部份只剩下一个记录时，就无需再做分区处理,而且我们可以设置成前、后边界，也可以设置前、后数组；

这里我们设置前、后边界，当前边界与后边界相等时，只剩下一个记录，无需再做比较：

static void quicksort(int left,int right){//left自然为array的左边界，right为array右边界

​    int tab=array[left];

​    if(left<right){

​    for(int i=left+1;i<=right;i++){//left+1到right为array中除array[left]的其它所有记录

​        if(tab>array[i]){

​            moveLeft(i,location)；//i为当前比tab小的记录在array中的位置，location为tab左边某位置，我们就把它设为数组最左端好了

​        }

​       quicksort(left,  前部分的右边界  );//对前部份重复上述过程

​       quicksort(  后部分的左边界 ,right);//对后部份重复上述过程

​    }

}

}

由上可见我们还缺乏一个对象来确定前部分的右边界与后部分的前边界，这个如何确定呢？

我们就需要知道我们在分区后，tab的位置在哪儿，我们就需要设置一个对象来记录tab位置的改变

我们来观察tab位置是如何变化的：

​    开始的时候tab的位置在left处，当遇到一个比tab小的值时，tab往右移动一位，那么我们设置对象 int locationtab=left;

static void quicksort(int left,int right){

if(left<right){

​    int tab=array[left];

​    int locationtab=left;//    开始的时候tab的位置在left处

​    for(int i=left+1;i<=right;i++){

​        if(tab>array[i]){

​            moveLeft(i,location)；

​            locationtab++;    //    当遇到一个比tab小的值时，tab往右移动一位，

​        }

​       quicksort(left,  locationtab-1  );//对前部份重复上述过程

​       quicksort(  locationtab+1,right);//对后部份重复上述过程

​    }

}

}

在该过程中，分区算法应当是稳定的排序算法；

以上资料源于2017年发在开源中国上的博客：

https://my.oschina.net/u/3548836/blog/974048

# 归并排序

> 分治编程
>
> ​	将一个大问题分割成无关联的小问题。

​	归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法的一个非常典型的应用。将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。

​	若将两个有序表合并成一个有序表，称为2-路归并，与之对应的还有多路归并。

​	对于给定的一组数据，利用递归与分治技术将数据序列划分成为越来越小的半子表，在对半子表排序后，再用递归方法将排好序的半子表合并成为越来越大的有序序列。

​	为了提升性能，有时我们在半子表的个数小于某个数（比如15）的情况下，对半子表的排序采用其他排序算法，比如插入排序。

## 特点

- 归并是把两个已排序文件合并成一个更大的已排序的文件的过程
- 选择是把一个文件分成包含k个最小元素和n-k个最大元素两个部分的过程
- 选择和归并互为逆操作
  - 选择把一个序列分成两部分
  - 归并把两个文件合并成一个文件
- 归并排序是快速排序的补充
- 归并排序是以连续的方式访问数据
- 归并排序适用于链表排序
- 归并排序对输入的初始次序不敏感
- 快速排序的大部分任务在递归调用前完成。快速排序从最大子文件开始并以最小子文件结束，因此需要栈结构。此外，快速排序算法也不稳定。归并排序把序列分为两个部分，并对每个部分分别处理。归并排序从最小文件开始并以最大子文件结束。因此不需要栈，并且归并排序算法是稳定的算法。

## 场景

​	需要排序的数据将导致内存溢出，即内存大小小于数据大小。

## 效率比较

相关代码见

https://github.com/Houchengisnull/helloWorld/tree/master/code/Java/hc/hc-learning/src/main/java/org/hc/learning/algorithm/sort

``` text
FkSort implement By Mark
	spend time:64ms
插入排序
	迭代-1800678900次
	耗时9430ms
冒泡排序
	交换-1795838063次
	迭代704940218次
	耗时72180ms
选择排序
	交换100000次
	迭代704982704次
	耗时20643ms
归并排序
	耗时153ms
```

