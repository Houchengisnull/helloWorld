'''
Created on 2018年8月25日

@author: Administrator
'''
#n = input("输入一个正整数n:")
#print( n )
import math

#微软
#1 2 3 4 5
#1 3 5 4 2
#1 5 2 4 3

#创建 卡牌列表 
list_cards = list(range(1,5))
print("cards:",end=str(list_cards))
print()
#将卡牌放到桌面上
#放置规则 先将卡牌从牌顶抽出一张放置与桌面上，再从牌底抽出一张放置在桌面上
list_table = []
#Java程序员的思想：
#mid = int(list_cards.__len__()/2)  
mid = math.ceil( len(list_cards)/2 )
end = list_cards.__len__() 
list_left = list_cards[0:mid]
list_right = list_cards[mid:end]
#将 list_right倒序 按照原来的想法我以为会返回一个倒序的列表对象
list_right.reverse()

print("left.len : " + str(len(list_left)))
print(list_left)
print("right.len : " + str( len(list_right) ))
print(list_right)

# 1 2 3 4 5
# 1 5 2 4 3
# 1 -1 2 -1 3
# 1  5 2  4  3
for i in list_cards:        #开辟空间
    list_table.append(-1)

count = 0
for obj in list_left:
    list_table[count] = obj
    count = count+2

count = 1
for obj in list_right:
    list_table[count] = obj
    count = count+2
print("table:",end=str( list_table ))
