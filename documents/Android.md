[toc]

# AOSP

`Android Open Source Project`

# ADB

`Android Debug Bridge`.

## 下载地址

- Windows版本
  https://dl.google.com/android/repository/platform-tools-latest-windows.zip
- Mac版本
  https://dl.google.com/android/repository/platform-tools-latest-windows.zip
- Linux版本
  https://dl.google.com/android/repository/platform-tools-latest-linux.zip

## 安装

解压后配置环境变量指向adb.exe。

## 连接

``` shell
# 雷电模拟器
adb connect 127.0.0.1:5555
```

## 驱动

ADB运行需要安装ADB驱动。

- 官网地址

  https://adb.clockworkmod.com/

# ADB Shell

## Usage

### 配对

``` shell
adb pair $ip:$port
$code
```

### 点击

``` shell
input tap x y
```

### 滑动

``` shell
slide x x1 y y1 $time
```

### 输入文本

``` shell
# input text 不支持中文
adb shell input text 'Hello world'
# 发送中文
adb shell am broadcast -a ADB_INPUT_TEXT --es msg '赞' 

# 粘贴
adb shell input keyevent 279 
```

# Mumu模拟器

## MumuManager

### Usage

- **获取模拟器信息**

  ``` shell
  # 获取0号模拟器信息
  MuMuManager info -v 0
  # 获取所有模拟器信息
  MuMuManager info -v all
  ```

  



