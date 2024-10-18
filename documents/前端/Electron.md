[toc]

# Overview

Electron是一款桌面应用开发框架。通过内置Chromium和Node.js，Electron可以使用Javascript、HTML、CSS来构建跨平台的桌面应用。

# Concept

## Preload Script

预加载脚本用于在进程沟通。

- __dirname	指向当前脚本路径

- path.join 	API将多个路径联结在一起，创建一个跨平台的字符串

# Usage

## Install

``` shell
# 设置镜像
npm config set registry https://registry.npmmirror.com/
npm config set disturl=https://registry.npmmirror.com/-/binary/node
npm config set electron_mirror=https://registry.npmmirror.com/-/binary/electron/
# 安装
npm install electron --save-dev
```

## Start

- 打开package.json
- 指定入口函数	**main**
- 编辑scripts添加`start": "electron ."`

``` json
{
  "name": "client",
  "version": "1.0.0",
  "main": "main.js",
  "scripts": {
    "start": "electron .",
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [],
  "author": "houcheng",
  "license": "ISC",
  "description": "",
  "devDependencies": {
    "electron": "^33.0.0"
  }
}
```

## Debug

使用vscode进行调试

- 创建.vscode文件夹
- 创建launch.json

``` json
{
  "version": "0.2.0",
  "compounds": [
    {
      "name": "Main + renderer",
      "configurations": ["Main", "Renderer"],
      "stopAll": true
    }
  ],
  "configurations": [
    {
      "name": "Renderer",
      "port": 9222,
      "request": "attach",
      "type": "chrome",
      "webRoot": "${workspaceFolder}"
    },
    {
      "name": "Main",
      "type": "node",
      "request": "launch",
      "cwd": "${workspaceFolder}",
      "runtimeExecutable": "${workspaceFolder}/node_modules/.bin/electron",
      "windows": {
        "runtimeExecutable": "${workspaceFolder}/node_modules/.bin/electron.cmd"
      },
      "args": [".", "--remote-debugging-port=9222"],
      "outputCapture": "std",
      "console": "integratedTerminal"
    }
  ]
}
```

