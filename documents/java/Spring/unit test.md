[TOC]

# MockMultipartFile

> Mock 仿制的，模拟的

小开发通过Class MockMultipartFile构造一个MultipartFile实例以作单元测试



springmvc中常用MultipartFile实现类为 `CommonsMultipartFile`

## 构造方法

- public MockMultipartFile(String, byte[])
- public MockMultipartFile(String, InputStream)
- public MockMultipartFile(String, String, String, byte[])
- public MockMultipartFile(String, String, String, InputStream)

## 参考

https://blog.csdn.net/Altoin/article/details/78275164