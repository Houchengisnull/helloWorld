[TOC]

# io

## 读取文件

- 创建文件对象

- 使用open函数
- 关闭文件流

```python
fileContent = file.read()
file.close()
print(fileContent)
```

## 写入文件

```python
	# 通过 encoding = "UTF-8"设置编码格式
    file = open(data['title'] + ".md", "w", encoding = "UTF-8")
    file.write(data['content'])
    file.close()
```

# os

`os`是一个`python库`，我们可以借助它快速实现对文件的操作

``` python
import os
```

- **删除文件**

  ``` python
  import os
  
  path = 'hello.txt'
  os.remove(path)
  ```

- **删除文件夹**

  ``` python
  import os
  
  # 无法递归删除
  directory = 'hello'
  os.rmdirs(directory)
  # 递归删除 若文件夹不为空将报错WinErr 145
  directory = 'world'
  os.removedirs(directory)
  ```

- **文件所在目录**

  ``` python
  import os
  
  os.path.dirname('hello.txt')
  ```

- **创建文件**

  ``` python
  import os
  
  os.mkdirs('bak')
  ```

- **获取当前路径**

  ``` python
  import os
  
  print os.getcwd()
  ```

- **判断路径是否存在**

  ``` python
  import os 
  filepath = '/bak/HelloController.txt'
  print os.path.exist(os.path.dirname(filepath))
  ```

  

# shutil

`python`标准库中一款对文件进行高级操作的类库

- **拷贝**

  ``` python
  from shutil import copyfile
  
  # copyfile 无法递归地进行创建文件夹或拷贝
  copyfile('update', 'bak')
  ```

- **删除**

  ``` python
  import shutil
  
  dir = 'C://workspace'
  shuitl.rmtree('dir')
  ```

  