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
    FirstDotX = str(random.randint(250, 350))
    FirstDotY = str(random.randint(600, 700))
    SecondDotX = str(random.randint(250, 350))
    SecondDotY = str(random.randint(150, 250))
    # cmd="adb -s " + addr + " shell input swipe 310 650 310 200"
    cmd="adb -s " + addr + " shell input swipe "+FirstDotX+" " + FirstDotY + " " + SecondDotX + " " + SecondDotY
    res=subprocess.run(cmd,shell=True)
    print(res)
    
# 点赞
def like ():
    dotX = str(random.randint(505, 515))
    dotY = str(random.randint(645, 655)) 
    cmd="adb -s " + addr + " shell input tap " + dotX + " " + dotY
    res=subprocess.run(cmd,shell=True)

if __name__=="__main__":
    connect()
    
    for i in range(50):
        move_up()
        isLike = random.randint(1, 200)
        if isLike == 100:
            like()
            print("click like")
        print("第" + str(i+1) + "个视频")
        print("----------- ----------")
        
        time.sleep(random.randint(20, 25))
   
 
    # ui = subprocess.Popen("adb -s " + addr +" shell uiautomator dump /data/local/tmp/uidump.xml", shell=True, stdout=subprocess.PIPE)

    print("End")