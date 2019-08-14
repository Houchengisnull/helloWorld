[TOC]

# Model 接口

实例 `ExtendedModelMap`

# spring mvc限制list传递大小

- 缺省值 256

``` java
@InitBinder
public void initBinder() {
    // 将list限制大小修改为1000
    binder.setAutoGrowCollectionLimit(1000);
}
```

或通过转换成json字符串发送到后台，再通过jsonArray反序列化成集合对象以解决类似问题。

