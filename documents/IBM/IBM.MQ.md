[TOC]

# 目录结构

| 文件/文件夹 | 作用         |
| ----------- | ------------ |
| registry    | 存储Broker注册信息 |
| config    | 存储Broker配置信息 |

# 启动日志路径

`${mqsi_install_path}/components/#{BROKER}/#{一串神秘数字}/stdout`

# mqsicreatebar

生成`bar`

```shell
$ mqsicreatebar -data .\ -b .\bin\ESB.bar a ESBProject -cleanBuild -deployAsSource
```

## UTFDataFormatException异常

> 2019-09-19
>
> 某ESB项目打包成`*.bar`出现该错误，移除`.metadata`文件夹即可。本人通过对比成功与失败环境的差异后猜测得出该结果，对此感到十分神奇。