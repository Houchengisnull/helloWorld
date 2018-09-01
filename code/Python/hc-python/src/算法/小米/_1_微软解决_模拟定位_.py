'''
Created on 2018年8月25日

@author: Administrator
'''
'''
#https://zhuanlan.zhihu.com/p/38850888
title:
    一副从1到n的牌，
    每次从牌堆顶取一张放桌子上，
    再取一张放牌堆底，
    直到手机没牌，
    最后桌子上的牌是从1到n有序，
    设计程序，输入n，输出牌堆的顺序数组
'''
#弃牌 排序 
# 1 2 3 4 5 
# table                     | cards
#[]                         | [1,5,2,4,3]
#[1 ]                       | [5,2,4,3]   -> [2,4,3,5]
#[1,2]                      | [4 , 3, 5]  -> [3,5,4]
#[1,2,3]                    | [4,5]       -> [5]
#[1,2,3,4]                  | [5]
#[1,2,3,4,5]                | []

#排序 加牌
#[1,2,3,4,5]                | []
#[1,2,3,4]                  | []        -> [5]
#[1,2,3]                    | [5]       -> [4,5]
#[1,2]                      | [5,4      -> [3,5,4]
#[1]                        | [4,3,5]   -> [2,4,3,5]
#[]                         | [5,2,4,3] -> [1,5,2,4,3]  

# 结果数组    1 2 3 4 5    值
# 定位数组    1 3 5 4 2    位置
# 原数组        1 5 2 4 3

# 模拟定位法
# 将 等长的自然数数组 作为定位数组
# 按照变换规则将定位数组变换n次，得到结果数组进行n次变换后的位置
# 将结果数组 根据 定位数组 变换位置 得到原数组
n = 6
list_result = list( range(1, n ) )      #声明一个结果数组
print( "结果数组:\t\t"+str( list_result ) )

list_location = list( range(1, n ) )    #初始化 定位数组
print( "初始定位数组:\t"+str(list_location) )
#对 定位数组 按照规则 变换
count = 0
length = len(list_location)
while count < length-1 :                #根据变换规则进行变换
    temp = list_location[count+1]
    list_location.remove(temp)
    list_location.append(temp)
    count=count+1
print( "变换后定位数组:\t"+str(list_location ))

list_ori = []
count = 1
while count <= length :
    list_ori.append( list_result[ list_location.index( count )] )
    count = count + 1
print("原数组:\t\t" + str(list_ori))

# 如果能令变换规则更加抽象，事情大概会更简单