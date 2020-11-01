[TOC]

# 禅

``` python
import this
```

# pip

- 更新`pip`工具

``` shell
python -m pip install --upgrade pip
```

## 配置pip镜像

使用默认`pip工具`下载我们的小玩具时，常常由于连接不稳定导致我们无法正常下载我们的小玩具。这时我们需要配置我们的`pip`镜像。

- Windows系统

  1.在用户目录下创建 `\pip\pip.ini`文件

  2.配置 `pip.ini`文件，如下

  ``` ini
  [global] 
  index-url = http://mirrors.aliyun.com/pypi/simple/ 
  [install] 
  trusted-host=mirrors.aliyun.com 
  ```

  测试结果

  ``` bash
  C:\Users\60993>pip install itchat
  Looking in indexes: http://mirrors.aliyun.com/pypi/simple/
  Collecting itchat
    Downloading http://mirrors.aliyun.com/pypi/packages/57/99/20dde4bee645453d1453ae3757b49f24a5fd179ce6e391cf2542cfeac61c/itchat-1.3.10-py2.py3-none-any.whl
  Collecting requests (from itchat)
    Downloading http://mirrors.aliyun.com/pypi/packages/51/bd/23c926cd341ea6b7dd0b2a00aba99ae0f828be89d72b2190f27c11d4b7fb/requests-2.22.0-py2.py3-none-any.whl (57kB)
       |████████████████████████████████| 61kB 4.1MB/s
  Collecting pypng (from itchat)
    Downloading http://mirrors.aliyun.com/pypi/packages/0e/39/993a5feea8ed9c2eebd70c6e7c20cb4b0823588f5ab0afab4b0be95ebc23/pypng-0.0.19.tar.gz (293kB)
       |████████████████████████████████| 296kB 3.3MB/s
  Collecting pyqrcode (from itchat)
    Downloading http://mirrors.aliyun.com/pypi/packages/06/76/1aa11ac094c65005b5d8a042b8bd96d73d4e2c32d9a63a68b21278e4b7d2/PyQRCode-1.2.1.zip (41kB)
       |████████████████████████████████| 51kB 1.6MB/s
  Collecting chardet<3.1.0,>=3.0.2 (from requests->itchat)
    Downloading http://mirrors.aliyun.com/pypi/packages/bc/a9/01ffebfb562e4274b6487b4bb1ddec7ca55ec7510b22e4c51f14098443b8/chardet-3.0.4-py2.py3-none-any.whl (133kB)
       |████████████████████████████████| 143kB 1.7MB/s
  Collecting urllib3!=1.25.0,!=1.25.1,<1.26,>=1.21.1 (from requests->itchat)
    Downloading http://mirrors.aliyun.com/pypi/packages/e6/60/247f23a7121ae632d62811ba7f273d0e58972d75e58a94d329d51550a47d/urllib3-1.25.3-py2.py3-none-any.whl (150kB)
       |████████████████████████████████| 153kB 1.3MB/s
  Collecting idna<2.9,>=2.5 (from requests->itchat)
    Downloading http://mirrors.aliyun.com/pypi/packages/14/2c/cd551d81dbe15200be1cf41cd03869a46fe7226e7450af7a6545bfc474c9/idna-2.8-py2.py3-none-any.whl (58kB)
       |████████████████████████████████| 61kB 1.3MB/s
  Collecting certifi>=2017.4.17 (from requests->itchat)
    Downloading http://mirrors.aliyun.com/pypi/packages/69/1b/b853c7a9d4f6a6d00749e94eb6f3a041e342a885b87340b79c1ef73e3a78/certifi-2019.6.16-py2.py3-none-any.whl (157kB)
       |████████████████████████████████| 163kB 3.3MB/s
  Installing collected packages: chardet, urllib3, idna, certifi, requests, pypng, pyqrcode, itchat
    Running setup.py install for pypng ... done
    Running setup.py install for pyqrcode ... done
  Successfully installed certifi-2019.6.16 chardet-3.0.4 idna-2.8 itchat-1.3.10 pypng-0.0.19 pyqrcode-1.2.1 requests-2.22.0 urllib3-1.25.3
  ```

- 参考

  https://blog.csdn.net/yanjiangdi/article/details/81364686

## Require C++ 2014

可选择多种方案解决该问题

- 安装visual studio build tools

  > 链接：https://pan.baidu.com/s/1uRAPZXu9uyzezCc-r2ORUA 
  > 提取码：qu7o 

- 手动下载whl资源

个人尝试安装`MinGW`并不能解决pip require 2014（Win10）的问题。

## 参考

https://blog.csdn.net/zhou120189162/article/details/82711600	

https://blog.csdn.net/qq_14998713/article/details/78277052

# 设置Python脚本编码

`SyntaxError: Non-UTF-8 code starting with '\xc4' `

- 2019\06\29

  今天在遇到如上错误，出现在声明字符串处

  百度后发现在文件头设置编码即可

  ``` python
  # coding = gbk
  ```

- 参考

  https://blog.csdn.net/mgsg_asp/article/details/80668621

# Python编码规范

## def 方法声明

<span style="color:red">PEP:8 expected 2 blank lines ，found 1</span>

在Python中声明方法时，需要距上空两行

# import

## 导入同路径下Package/Module

.

└──src

​		└──spider



​				├── SpiderUtils

​				│ 		└──\__init__.py

​				└── books

​							└──1q84.py

如上场景，`1q84.py`需要调用SpiderUtils下\____init____.py中方法时

``` python
from src.spider.spidersuit import SpiderMan
```

https://blog.csdn.net/Tyro_java/article/details/80739247

https://www.cnblogs.com/7tiny/p/7209608.html

# 声明变量

## GLOBAL

当我们需要在函数中使用全局变量时，应该对该变量使用`global`声明。

否则`解释器`将认为你在函数中定义了一个局部变量。

# 循环

## while

``` python
count = 0 
while count < 5: 
	print (count, " 小于 5") 
	count = count + 1 
else: 
	print (count, " 大于或等于 5")
```

# 字符串

## 类型转换

- 字符串转整形

  ``` python
  stringType = "1"
  intType = str(stringType)
  ```

- 整形转字符串

  ``` python
  intType = 1
  stringType = int(intType)
  ```


## 正则表达式

``` python
import re

# 正则匹配过滤掉emoji表情，例如emoji1f3c3等
rep = re.compile("1f\d.+")
signature = rep.sub("", signature)
```

# 异次元口袋——python类库

- bs4 (BeautifulSoup)

  ``` 
  pip install bs4
  ```

- 微信

  ``` shell
  pip install itchat
  ```

- 数据可视化

  ``` shell 
  pip install echarts-python
  ```

- 词云 wordcloud

  ``` java
  pip install wordcloud
  ```

- jieba分词 
  ``` java
  pip install jieba
  ```