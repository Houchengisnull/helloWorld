[TOC]

# `mqsicreatebar`

生成`bar`文件

```shell
$ mqsicreatebar -data .\ -b .\bin\ESB.bar a ESBProject -cleanBuild -deployAsSource
```



## `UTFDataFormatException`异常

> 2019-09-19
>
> 某`ESB`项目打包成`bar`时出现该错误，移除`.metadata`文件夹即可。本人通过对比成功与失败环境的差异后猜测得出该结果，对此感到十分神奇。