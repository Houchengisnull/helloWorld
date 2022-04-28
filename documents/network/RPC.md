[toc]

# RPC

远程服务调用(Remote Procedure Call，RPC)。

- 目的

  远程通信，为了让计算机调用本地方法一样调用远程方法。

![img](../images/net/rpc.jpg)

| RPC工具       | 序列化协议                                | 数据交换协议                 | 方法 |
| ------------- | ----------------------------------------- | ---------------------------- | ---- |
| ONE RPC       | External Data Representation              |                              |      |
| CORBA         | Common Data Representation                | Internet Inter ORB Protocol  | OIDL |
| Java RMI      | Java Object Serialization Stream Protocol | Java Remote Message Protocol |      |
| gRPC          | Protocol Buffers                          | Http 2.0                     |      |
| Web Service   | XML Serialization                         | SOAP                         | WSDL |
| 众多轻量级RPC | JSON Serialization                        |                              |      |
| Dubbo         |                                           | TCP                          |      |

> gRPC底层使用netty实现。

RPC与具体网络分层、具体协议没有直接关系，只要实现了远程过程调用都被称为RPC。

## Stub

Sever与Client都有Stub。在Java中实现RPC时，就是我们的定义的Interface。	

# 实现RPC

实现RPC框架需要解决的问题：

- **代理问题**	屏蔽网络连接细节
- 序列化问题
- 通信问题
- 登记的服务实例化

# WebService

RPC的一种实现。

- **SOAP**

  简单对象访问协议，`Simple Object Access Protocol`。是一种基于XML描述客户端与服务端之间的数据交换格式协议。

- **WSDL**

  WebService定义语言，`WebService Definition Language`。是一种基于[^XSD]描述WebService的语言。[XSD]: XML Schema Definition

- UDDI

  统一发现和集成服务，`Universal Description Discovery and Integration`。是一个用于存放`WebService`服务信息的目录服务。

# 轻量级RPC工具

## Hessian