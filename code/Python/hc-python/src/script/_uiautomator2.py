import uiautomator2 as u2
import location_helper as lh

import time
import datetime
import random

device = u2.connect('adb-f8251985-IMF5Fr._adb-tls-connect._tcp')
# device.app_start("com.kuaishou.nebula", stop=True)

# 隐式等待20s，保证控件加载完成
# device.implicitly_wait(20)

# comment = device(resourceId='com.kuaishou.nebula:id/comment_icon', instance=0)
# Override size
w, h = device.window_size()
start_time = datetime.datetime.now()
end_time = datetime.datetime.now()
diff = end_time - start_time
print("脚本启动")
print("启动时间\t" + start_time.strftime("%Y-%m-%d %H:%M:%S"))
print("----------- -----------")

count = 0
while diff.seconds < 60 * 60:
    x = lh.randint_between(w/4, 3/4*w)
    y = lh.randint_between(h/2, 3/4*h)
    x1 = x
    y1 = y - h/4

    x = lh.position_random(x)
    y = lh.position_random(y)
    x1 = lh.position_random(x1)
    y1 = lh.position_random(y1)

    device.swipe(x, y, x1, y1)
    sleep_time = random.uniform(5, 15)
    time.sleep(sleep_time)

    end_time = datetime.datetime.now()
    diff = end_time - start_time
    count += 1 
    print("{0}\t已观看{1}个视频".format(end_time, count))