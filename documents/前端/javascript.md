[TOC]

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

