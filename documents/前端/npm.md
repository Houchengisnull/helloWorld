[toc]

# Overview

npm是Node.js的标准包管理工具。

# Usage

## Init

``` shell
npm init
```

执行完成后，将生成package.json[^package.json]。

[^package.json]: node.js的项目清单管理文件

## Install

格式：npm install --save <package>

``` bat
npm install --save echarts gcoord
// 全局安装
npm install electron -g
// 安装依赖到开发环境
npm install electron --save-dev
```

## 镜像

设置淘宝镜像

``` shell
npm config set registry https://registry.npmmirror.com
```

