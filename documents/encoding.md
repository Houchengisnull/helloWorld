[toc]

# ASCII

计算机中使用最广泛的字符集及编码，由`美国国家标准局（ANSI）`制定的`ASCII`码。它已被`国际标准化组织（ISO)`规定为国际标准。

具有7位码与8位码两种形式。

# unicode

`unicode`是一种编码方案，扩展自`ASCII`字符集。它使用16位字符集。

- **实现**
- **utf8**
- **utf16**
- **utf32**

``` mermaid
graph BT
	utf8 -- 实现 --> unicode
	utf16 -- 实现 --> unicode
	utf32 -- 实现 --> unicode
	unicode -- 扩展 --> ASCII
```

# HTML字符实体

`Escape Sequence`，转义字符实体，又称字符实体（`Character Entity`)。例如在`HTML`中表示空格需要一般使用`&nbsp;`

## 结构

- **ampersand**	&
- **entity**	`实体名字`或者`#`加上`实体编号`
- **分号**	`;`

## 原因

- 在`HTML`中像`<\>&`等字符具有特殊含义，无法直接使用。所以我们定义`字符实体`，当浏览器的解释器遇到这类字符时直接换成真实字符。
- 部分字符在`ASCII`字符集中没有定义，需要使用转义字符串。

<hr>

## Java转换

- **maven**

  ``` xml
  <dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.11</version>
  </dependency>
  ```

- **java**

  ``` java
  String escapgeSequence = "&#27743;&#35199;&#30465;";
  // 对转义字符解码
  String html = StringEscapeUtils.unescapeHtml(s);
  ```

  