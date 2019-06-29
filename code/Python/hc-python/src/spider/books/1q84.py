# coding=utf-8
# 2019-06-29 这次爬虫被对方的热加载为难到了
# 同时感受到自己对Python真的很陌生
# 同时感叹Python 部分API真难用
import os
print(os.path.dirname(os.path.abspath(__file__)))
from bs4 import BeautifulSoup
from src.spider.spidersuit import *

park = SpiderMan()
# <1q84> 卷2
urlIndex = "/book/2886/index.htm"
urlBook2 = "https://www.99lib.net"
# print(park.send("https://www.99lib.net/book/2886/89601.htm"))
dl = '<dl id="dir">' \
    '<dd><a href="/book/2886/89601.htm">第一章 青豆 那是世界上最无聊的地方</a></dd>' \
    '<dd><a href="/book/2886/89602.htm">第二章 天吾 除了灵魂一无所有</a></dd>' \
    '<dd><a href="/book/2886/89603.htm">第三章 青豆 无法选择如何出生，但可以选择如何死</a></dd>' \
    '<dd><a href="/book/2886/89604.htm">第四章 天吾 这种事也许不该期待</a></dd>' \
    '<dd><a href="/book/2886/89605.htm">第五章 青豆 一只老鼠遇到素食主义的猫</a></dd>' \
    '<dd><a href="/book/2886/89606.htm">第六章 天吾 我们拥有很长很长的手臂</a></dd>' \
    '<dd><a href="/book/2886/89607.htm">第七章 青豆 你即将涉足之处</a></dd>' \
    '<dd><a href="/book/2886/89608.htm">第八章 天吾 一会儿猫儿们就该来了</a></dd>' \
    '<dd><a href="/book/2886/89609.htm">第九章 青豆 作为恩宠的代价送来的东西</a></dd>' \
    '<dd><a href="/book/2886/89610.htm">第十章 天吾 提议遭到拒绝</a></dd>' \
    '<dd><a href="/book/2886/89611.htm">第十一章 青豆 平衡本身就是善</a></dd>' \
    '<dd><a href="/book/2886/89612.htm">第十二章 天吾 用手指数不完的东西</a></dd>' \
    '<dd><a href="/book/2886/89613.htm">第十三章 青豆 如果没有你的爱</a></dd>' \
    '<dd><a href="/book/2886/89614.htm">第十四章 天吾 递过来的礼物</a></dd>' \
    '<dd><a href="/book/2886/89615.htm">第十五章 青豆 终于，妖怪登场了</a></dd>' \
    '<dd><a href="/book/2886/89616.htm">第十六章 天吾 就像一艘幽灵船</a></dd>' \
    '<dd><a href="/book/2886/89617.htm">第十七章 青豆 把老鼠掏出来</a></dd>' \
    '<dd><a href="/book/2886/89618.htm">第十八章 天吾 沉默而孤独的卫星</a></dd>' \
    '<dd><a href="/book/2886/89619.htm">第十九章 青豆 当子体醒来时</a></dd>' \
    '<dd><a href="/book/2886/89620.htm">第二十章 天吾 海象和发疯的帽子店老板</a></dd>' \
    '<dd><a href="/book/2886/89621.htm">第二十一章 青豆 我该怎么办？</a></dd>' \
    '<dd><a href="/book/2886/89622.htm">第二十二章 天吾 只要天上浮着两个月亮</a></dd>' \
    '<dd><a href="/book/2886/89623.htm">第二十三章 青豆 请让老虎为您的车加油</a></dd>' \
    '<dd><a href="/book/2886/89624.htm">第二十四章 天吾 趁着暖意尚存</a></dd></dl>'
soup = BeautifulSoup(dl,'html.parser')
dl = soup.findAll('dd')
for dd in dl:
    a = dd.a
    href = a['href']
    title = a.text
    url = urlBook2 + href
    res = park.send(url)
    chapter = BeautifulSoup(res, "html.parser")
    content = chapter.find("div",id = "content")
    for paragraph in content:
        print(paragraph.text)
# <1q84> 卷3
'''
urlBook3 = "https://www.99lib.net/book/2887/"
urlBook3_index = 89625
'''