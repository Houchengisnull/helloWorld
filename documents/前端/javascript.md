[TOC]

# BOM

- [什么是BOM](https://www.cnblogs.com/llanq123/p/13768900.html)

  `BOM(Browser Object Model)`，浏览器对象模型。

- BOM的构成

  ``` mermaid
  graph TD
  	windows
  	windows ---- document
  	windows ---- location
  	windows ---- navigation
  	windows ---- screen
  	windows ---- history
  ```

  

## windows

`BOM`的顶级对象。

### location

通常我们使用`location`对页面进行重定向：

``` javascript
windows.location = 'www.baidu.com';
// windows对象作为“最大”的对象, 有时可以省略。
// location.href = 'www.baidu.com';
```

# 剪切板

``` javascript
// 设置剪切板内容
window.clipboardData.setData('text'， 'hello world');
// 获得剪切板内容
window.clipboardData.getData('text');
```

# 字符串操作

## replace

``` 
var str = 'hello-world-!';
str.replace('-'， ''); // 仅替换一个 '-'
str.replace(/-/g， ''); // 通过正则表达式进行全局替换， /g 表示全局替换
```

