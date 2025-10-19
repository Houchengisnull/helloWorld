import os, time

# 获取设备列表
def get_devices():
    content = os.popen('adb devices').read()
    if "daemon not running" in content:
        content = os.popen('adb devices').read()
    row_list = content.split('List of devices attached\n')[1].split('\n')
    devices_list = [i for i in row_list if len(i) > 1]
    res = []
    for d in devices_list:
        res.append(d.split('\t')[0])
    return res


# 设备屏幕截图
def scree_capture(device, path):
    screenCap = 'adb -s {0} shell screencap -p sdcard/temp.png'.format(device)
    pull = "adb pull sdcard/temp.png {0}".format(path)
   
    for row in [screenCap, pull]:
        time.sleep(0.1)
        print(row)
        os.system(row)

    if os.path.exists(path) == True:
        return True
    else:
        return False
    

# 点击
def touch(device, pos):
    x, y = pos
    command = "adb -s {0} shell input touchscreen tap {1} {2}".format(device, x, y)
    os.system(command)