[TOC]

Exception | Description | Solution
:---: | :---: | :---:
CWTE_NORMAL_J2CA1009 | 数据源连接数不足 | 修改websphere数据源最大连接数

# ADML3000E

> 某同事在升级`websphere`使用的`JDK`失败后，服务无法启动，日志显示`ADML3000E`错误

该异常可能由错误的配置文件或者`node`之间未同步导致。

故需要执行`./syncNode.sh`脚本。

https://developer.ibm.com/answers/questions/258586/how-to-resolve-adml3000e-during-jvm-startup-after/