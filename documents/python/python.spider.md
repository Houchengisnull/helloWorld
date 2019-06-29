[TOC]

# BeautifulSoup

## download

``` she
# pip install bs4
```

注意`BeautifulSoup`仅为`bs4`下一个模块。

## 使用

``` python
from bs4 import BeautifulSoup

... 
# 构造方法 得到文档对象
soup = BeautifulSoup(html，'html.parser', from_encoding='utf-8')

# 查找所有h4标签
links = soup.find_all("h4")

# 根据id查找
dir = soup.find('dl', id='dir')

dl = dir.findAll('dd')
for dd in dl:
    # 获得 tag dd 包裹的 超链接
    a = dd.a 
    print(a['href'])
    print(a.text)
```

