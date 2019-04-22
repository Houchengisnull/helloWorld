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

