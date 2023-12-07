[toc]

- 官网

  http://doc.autoxjs.com/#/

# 环境

配置`Autox.js`的开发环境：

- Autox.js
- Scrcpy
- Visual Studio Code

## Autox.js

### 版本

官网提供了三个版本：

- [app-v6-arm64-v8a-release-unsigned-signed.apk](https://github.com/kkevsekk1/AutoX/releases/download/6.5.2/app-v6-arm64-v8a-release-unsigned-signed.apk)
- [app-v6-armeabi-v7a-release-unsigned-signed.apk](https://github.com/kkevsekk1/AutoX/releases/download/6.5.2/app-v6-armeabi-v7a-release-unsigned-signed.apk)
- [app-v6-universal-release-unsigned-signed.apk](https://github.com/kkevsekk1/AutoX/releases/download/6.5.2/app-v6-universal-release-unsigned-signed.apk)

其中`arm64-v8a`、`armeabi-v7a`、`universal`指的是安卓手机的CPU类型。

### 查看CPU版本

``` shell
adb shell getprop ro.product.cpu.abi
```

- **参考**

  [adb 查看安卓手机 CPU 类型](https://blog.csdn.net/zz00008888/article/details/133696691)

### 配置

- 开启无障碍服务
- 开启悬浮窗
- 稳定模式

## Scrcpy

投屏软件，用于连接手机。

> 模拟器使用自身的adb，无需Scrcpy。

- 仓库

  https://github.com/Genymobile/scrcpy/tree/master

## Visual Studio Code

- 安装插件`Auto.js-Autox.js-VSCodeExt`

- 启动服务
  1. `Ctrl Shift P`
  2. `autoxjs: start server`
- 开启调试
  1. 在设备上打开autox.js；
  2. 侧边栏菜单开启调试；
- 打包
  1. `Ctrl Shift P`
  2. `autoxjs: save`