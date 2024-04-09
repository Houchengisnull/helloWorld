[toc]

# Overview

Strace是Linux环境下的调试工具

# Install

``` shell
yum -y install strace
```

# Usage

``` shell
# 查看进程当前调用栈
strace -p pid

# 仅跟踪kubectl程序的系统调用
strace -eopentat kuberctl version
```

