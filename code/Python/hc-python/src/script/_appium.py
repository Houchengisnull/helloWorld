from appium import webdriver
from selenium.webdriver.common.by import By
import time
import subprocess
import random



def kuaishou(adb_name):
    adb_path = r"D:\adb\adb.exe"

    # Appium Server连接的参数
    desired_caps = {
      'platformName': 'Android', # 被测手机是安卓
      'platformVersion': '13', # 手机安卓版本
      'deviceName': 'houcheng', # 设备名，安卓手机可以随意填写
      'appPackage': 'com.kuaishou.nebula', # 启动APP Package名称
      'appActivity': 'com.yxcorp.gifshow.HomeActivity', # 启动Activity名称
      'unicodeKeyboard': True, # 使用自带输入法，输入中文时填True
      'resetKeyboard': True, # 执行完程序恢复原来输入法
      'noReset': True,       # 不要重置App
      'newCommandTimeout': 6000,
      'automationName' : 'UiAutomator2'
    }

    # 连接Appium Server，初始化自动化环境
    driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)
    num = 0
    elapsed = 0
    judge = 0
    box = 1
    while True:
        try:
            # 设置等待时间等待
            driver.implicitly_wait(40)

            # 是否出现评论图标
            Comment_icon = driver.find_elements(By.ID, "com.kuaishou.nebula:id/comment_icon")
            print(bool(Comment_icon),'出现评论图标')

            # 如果页面没有出现评论图标就进行等待
            if bool(Comment_icon) == False:
                # 键盘返回一下
                subprocess.getoutput(f"{adb_path} -s {adb_name} shell input keyevent 4")
                continue

            # 刷视频
            start = time.time()
            subprocess.getoutput(f"{adb_path} -s {adb_name} shell input swipe 360 1000 360 120")
            sleep_time = random.randint(7, 12)
            time.sleep(sleep_time)
            num += 1
            print('观看第{}个视频，观看{}秒'.format(num, sleep_time))
            end = time.time()
            elapsed += int(end - start)

            # 如果刷到的下一个视频是直播或者看图片的就过滤掉
            Comment_icon = driver.find_elements(By.ID, "com.kuaishou.nebula:id/comment_icon")
            if bool(Comment_icon) == False:
                print(bool(Comment_icon), '是直播，图片或其他异常，过滤掉')
                continue

            if judge <= elapsed:
                print('宝箱的时间到了')
                if Comment_icon:
                    # 点击红包，进入任务主页
                    subprocess.getoutput(f"{adb_path} -s {adb_name} shell input tap 68 750")

                # 定位任务页面  xpath定位的是 我的抵用金
                tasks = driver.find_elements(By.XPATH,
                                             "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.widget.ListView/android.view.View[2]/android.view.View[1]/android.widget.TextView")

                # 判断是否进入任务页面
                if tasks:
                    print('进入任务页面')
                    time.sleep(2)
                    # 点击宝箱
                    subprocess.getoutput(f"{adb_path} -s {adb_name} shell input tap 604 1155")

                    time.sleep(2)
                    # 返回
                    subprocess.getoutput(f"{adb_path} -s {adb_name} shell input keyevent 4")

                    print(f'宝箱已领{box}个')
                    judge += 1200
                    box += 1

                # 如果没有定位到 “进入任务主页” 和 “我的抵用金” 就返回
                if (bool(Comment_icon) == False) and (bool(tasks) == False):
                    subprocess.getoutput(f"{adb_path} -s {adb_name} shell input keyevent 4")

        except:
            print('出错了，重启')
            subprocess.getoutput(f"{adb_path} -s {adb_name} shell am force-stop com.kuaishou.nebula")
            kuaishou(adb_name)

def RW(adb_name, num=1):
    adb_path = r"D:\adb\adb.exe"

    # Appium Server连接的参数
    desired_caps = {
      'platformName': 'Android', # 被测手机是安卓
      'platformVersion': '7', # 手机安卓版本
      'deviceName': 'xxx', # 设备名，安卓手机可以随意填写
      'appPackage': 'com.kuaishou.nebula', # 启动APP Package名称
      'appActivity': 'com.yxcorp.gifshow.HomeActivity', # 启动Activity名称
      'unicodeKeyboard': True, # 使用自带输入法，输入中文时填True
      'resetKeyboard': True, # 执行完程序恢复原来输入法
      'noReset': True,       # 不要重置App
      'newCommandTimeout': 6000,
      'automationName' : 'UiAutomator2'
    }

    # 连接Appium Server，初始化自动化环境
    driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

    while True:
        try:
            # 设置等待时间等待
            driver.implicitly_wait(40)

            # 是否出现评论图标
            Comment_icon = driver.find_elements(By.ID, "com.kuaishou.nebula:id/comment_icon")
            print(bool(Comment_icon),'出现评论图标')

            # 如果页面没有出现评论图标就进行等待
            if bool(Comment_icon) == False:
                # 键盘返回一下
                subprocess.getoutput(f"{adb_path} -s {adb_name} shell input keyevent 4")
                continue

            # 点击红包进入任务页面
            subprocess.getoutput(f"{adb_path} -s {adb_name} shell input tap 68 750")

            # 定位任务页面  xpath定位的是 我的抵用金
            tasks = driver.find_elements(By.XPATH,
                                         "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.widget.ListView/android.view.View[2]/android.view.View[1]/android.widget.TextView")

            # 判断是否进入任务页面
            if tasks:
                time.sleep(3)
                for i in range(10):
                    # 滑动任务页面
                    subprocess.getoutput(f"{adb_path} -s {adb_name} shell input swipe 352 1077 352 777")
                    # 定位看广告的任务
                    welfare = driver.find_elements(By.XPATH, "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[4]/android.view.View[1]")
                    if welfare:
                        break
                    else:
                        continue

                for i in range(12-num):
                    # 定位看广告的任务
                    welfare = driver.find_elements(By.XPATH, "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[4]/android.view.View[1]")
                    # 判断是否有看广告的任务
                    if welfare:
                        print('定位到看广告的任务')
                        # 有看广告的任务，则点击
                        welfare[0].click()

                        # 等看完广告了就退出
                        time.sleep(45)
                        subprocess.getoutput(f"{adb_path} -s {adb_name} shell input tap 489 46")
                    print('已完成{}次观看广告任务'.format(num))
                    num += 1
                    time.sleep(1)

                    # 判断是否完成了10次观看，如果完成就结束
                    if num == 10:
                        return

        except:
            print('出错了，重新开始')
            # 判断是否完成了10次观看，如果完成就结束
            if num == 10:
                return
        RW(adb_name, num)


adb_name = input('请输入模拟器的编号:')    # 例如：emulator-5558
RW(adb_name)
kuaishou(adb_name)

