# -*- coding: utf-8 -*-
import time
import subprocess
import random

# 参考
# [手把手教你如何利用Python薅羊毛（快手极速版）]https://www.cnblogs.com/sn5200/p/15833969.html

addr="127.0.0.1:5555"

# 连接模拟器
def connect():
    print("connecting...")
    state = subprocess.run("adb connect " + addr,shell=True)
    print("state:", state)
    return state

# 上滑
def move_up():
    # 向adb发送命令:向上滑动屏幕
    cmd="adb -s " + addr + " shell input swipe 310 650 310 200"
    res=subprocess.run(cmd,shell=True)
    print(res)
    
# 点赞
def like ():
    cmd="adb shell input tap 664 878"
    res=subprocess.run(cmd,shell=True)

if __name__=="__main__":
    
    for i in range(100):
        move_up()
        like()
        print("第" + str(i+1) + "个视频")
        print("----------- ----------")
        time.sleep(20)
   
 
    # ui = subprocess.Popen("adb -s " + addr +" shell uiautomator dump /data/local/tmp/uidump.xml", shell=True, stdout=subprocess.PIPE)

    print("End")