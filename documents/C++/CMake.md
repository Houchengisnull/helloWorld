[toc]

- **参考**

- <a href='https://cmake.org/'>CMake官网</a>
- <a href='https://blog.csdn.net/zhuiyunzhugang/article/details/88142908'>超详细的cmake教程</a>

# CMake

> CMake is an open-source, cross-platform family of tools designed to build, test and package software.
>

`CMake`是一款开源、跨平台的打包工具，如同Java生态中的Maven、Gradle等工具一样。

- 工作流程

  ```mermaid
  graph LR
  	CMake -- CMakeLists.txt --> Makefile
  	make -- Makefile --> *.exe
  ```

  1. CMake将根据CMakeLists.txt生成Makefile

  2. make将根据Makefile生成`*.exe

# FAQ

## Visual Studio Code提示CMake可执行文件不正确...

- 原因

  Visual Studio Code仅仅安装extends是不够的，还需要我们手动安装CMake。

- 参考

  <a href='https://code.visualstudio.com/docs/cpp/CMake-linux'>Visual Studio 使用CMake</a>

  <a href='https://blog.csdn.net/weixin_38428827/article/details/108297796'>[解决]是否已安装该可执行文件或设置是否包含正确的路径(cmake.cmakePath)?</a>