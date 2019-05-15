[TOC]

# Project Structure

## Project Setting

### Project

- Project name
- Project SDK
- Project language level  
限定项目编译检查时最低要求的JDK feature
- Project complied output  
项目默认编译输出根目录  
实际上, 每个模块可以设置独自的根目录(Module - (project) - Paths - Use Module compile output path)。
> 这个设置略鸡肋

### Modules

#### Source

- Mark as [ Source(代码) | Tests(单元测试) | Resources(静态资源) | Test Resoures(单元测试静态自由) | Excluded(排除) ]

#### Paths

设置模块编译输出路径
#### Dependencies

Scope 与maven编译范围类似
- complie 编译运行均有效
- Test 仅对测试类有效
- Runtime 仅运行时有效
- Provided 仅编译时有效; 对测试类编译运行均有效

## Libraries

### Facets

> When you select a framework (a facet) in the element selector pane, the settings for the framework are shown in the right-hand part of the dialog.     ——官方文档

### Artifacts 手工艺品

项目打包部署设置

> An artifact is an assembly of your project assets that you put together to test, deploy or distribute your software solution or its part. Examples are a collection of compiled Java classes or a Java application packaged in a Java archive, a Web application as a directory structure or a Web application archive, etc.     ——官方文档

- archive 归档
至少在IBM中常常使用 archive 描述[jar|war]
- exploded 分解的
