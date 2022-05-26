# 异常处理

- **原则**

- 不可以在`finally`中使用`return`

  这将导致`try`代码块中的`return`失效。

``` java
try {
    // 不稳定的代码
    return true;
} catch (Exception e) {
    e.printStackTrace();
} finally {
    
}

return false;
```