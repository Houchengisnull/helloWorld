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

# 命名规范

- **Package**

  简短全小写，不建议下划线。

- **Module**

  简短全小写，可以使用下划线。

- **Class**

  首字母大写。

- 全局变量

  仅在模块内使用，命名前加下划线。

- **Function**

  函数名应该小写，如果想提高可读性可以用下划线分隔。

- **Method & Instance Variables**

  使用下划线分隔小写单词以提高可读性。

  在非共有方法和实例变量前使用单下划线。

- **Constants**

  通过下划线分隔的全大写字母命名。

# Import

解释器在导入时，依次从内置模块、`sys.path`中查找Package。

而`sys.path`中仅包含了主函数的当前路径和其他lib。

所以，常规情况下，解释器无法找到同级文件夹中的Package、Module。

## Concept

- **Package**

  包含`__init__.py`的文件夹。

- **Module**

  `*.py`文件。

- **The Module Search Path**

  1. 内置模块；
  2. `sys.path`上定义的文件夹列表；

  若未找到模块，将抛出异常。

# 循环

## for-index

``` python
fruits = ['banana', 'apple']
for index in range(len(fruits)):
    print '当前水果:', fruits[index]
    
print "Good bye!"
```

### range函数

``` python
print(range(5))
# result
# range(0,5)
```

在`python2.x`中，`range()`返回一个整数列表。

>  `python3`中` range()` 返回的是一个可迭代对象（类型是对象），而不是列表类型， 所以打印的时候不会打印列表

## foreach

``` python
''' 输出脚本参数 '''
import sys

for i in sys.argv:
    print(argv)
```

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

## 以...开头/结尾

``` python
if str.startswith('Hello') :
    print 'start with Hello'
if str.endswith('.java'):
    print 'end with .java'
```

## 替换

``` python
new_str = old_str.replace('hello', 'world')
```

## 长度

``` python
str = 'Hello world'
print len(str)
```

## 类型转换

- 字符串转整形

  ``` python
  stringType = "1"
  intType = int(stringType)
  ```

- 整形转字符串

  ``` python
  intType = 1
  stringType = str(intType)
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

``` shell
# BeautifulSoup
pip install bs4
# 微信类库
pip install itchat
# 安装数据可视化
pip install echarts-python
# 安装词云
pip install wordcloud
# 安装jieba分词
pip install jieba

# 查看已安装类库
pip list

# 指定版本
pip install numpy==1.19.5 opencv-python==4.5.1.48

# 指定下载镜像
# 使用清华大学镜像下载pillow
pip install pillow -i https://pypi.tuna.tsinghua.edu.cn/simple/
```

