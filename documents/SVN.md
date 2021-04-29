[toc]

- **参考**
- [SVN 使用（命令行方式）](https://zhuanlan.zhihu.com/p/139529007)

# 命令

- **查看版本**

  ``` shell
  svn --version
  ```

- **查看仓库详细信息**

  ``` shell
  svn info
  ```

- **查看仓库文件到状态**

  ``` svn
  svn status
  ```

- **log**

  ``` shell
  # 查看所有日志信息
  svn log
  # 查看指定版本之间信息
  svn log -r 3:8
  svn log -r 3:head
  # 查看日志及目录信息
  svn log -v
  # 显示限定N条日志信息
  svn log -l N
  ```

  