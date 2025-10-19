import adb_helper as adb
import cv2_helper as cv_helper
import image_helper 
import dictionary as dict
import time

devices = adb.get_devices()
for device in devices:
    print(device)

# 番茄小说
tomato = devices[0]

# 抓拍并保存到 $dict.temp
adb.scree_capture(tomato, dict.TOMATO_TEMP)

# 比对
match_result = cv_helper.matchTemplate(dict.TOMATO_TEMP, dict.TOMATO_ADVERTISING)
location = cv_helper.maxLoc(match_result)
location = image_helper.center(dict.TOMATO_ADVERTISING, location)

# 点击
adb.touch(tomato, location)
time.sleep(60)

# 关闭
adb.touch(tomato, [980, 110])
# 领取奖励
location = image_helper.centerBetween((300, 1000), (800, 1150))
adb.touch(tomato, location)
