[TOC]

Exception | Description | Solution
:---: | :---: | :---:
CWTE_NORMAL_J2CA1009 | 数据源连接数不足 | 修改websphere数据源最大连接数

# ADML3000E

> 某同事在升级`websphere`使用的`JDK`失败后，服务无法启动，日志显示`ADML3000E`错误

该异常可能由错误的配置文件或者`node`之间未同步导致。

故需要执行`./syncNode.sh`脚本。

https://developer.ibm.com/answers/questions/258586/how-to-resolve-adml3000e-during-jvm-startup-after/

# ADMG0011E 删除Server报错

> 2019-09-12
>
> ​	在删除`Websphere`某`Server`时失败，原因为配置文件未清理干净

- 关闭控制台
- 进入管理结点`profile/dmgr`
- 进入` config/cells/$Cell_Name/applications`，清理相关`*.ear`
- 进入`../blas`，清理相关文件
- 进入`../cus`，清理相关文件
- 启动控制台
- 进入控制台 - 结点 - 全部同步

https://developer.ibm.com/answers/questions/195947/how-to-resolve-admg0011e-error-attempting-to-delet/

# NoSuchMethodException

## 原因分析

由于`wasphere`与`tomcat`类加载顺序不一样：

- `tomcat`通常先加载应用`lib`

- `wasphere`通常先加载自身`lib`

当遇到`wasphere`自身引用类包名与类名与应用所引用的类一样时，且在`was console`设置类加载顺序为`parent first`时，则会出现类似错误。

# 解决方案

- 修改类加载顺序为`parent last`
- 设置共享库，并设置对此共享库使用隔离类加载器

https://blog.csdn.net/sinat_34703020/article/details/51179670