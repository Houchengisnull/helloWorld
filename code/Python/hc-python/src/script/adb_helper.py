import os, time, re,logging

# 返回第一个设备
def device():
    device = devices()[0]
    logging.info('设备:{0}'.format(device))
    return device 


# 获取设备列表
def devices():
    content = os.popen('adb devices').read()
    if "daemon not running" in content:
        content = os.popen('adb devices').read()
    row_list = content.split('List of devices attached\n')[1].split('\n')
    devices_list = [i for i in row_list if len(i) > 1]
    res = []
    for d in devices_list:
        res.append(d.split('\t')[0])
    return res


# Override size
def screen_size(device):
    content = os.popen('adb -s {0} '.format(device) + "shell wm size").read()
    logging.debug(content)
    override_match = re.search(r'Override size:\s*(\d+)x(\d+)', content)
    if override_match:
        w = int(override_match.group(1))
        h = int(override_match.group(2))
        return (w, h)

    physical_match = re.search(r'Physical size:\s*(\d+)x(\d+)', content)
    if physical_match:
        w = int(physical_match.group(1))
        h = int(physical_match.group(2))
        return (w, h)


# 设备屏幕截图
def scree_capture(device, path):
    screenCap = 'adb -s {0} shell screencap -p sdcard/temp.png'.format(device)
    pull = "adb -s {0} pull sdcard/temp.png {1}".format(device, path)
   
    for row in [screenCap, pull]:
        time.sleep(0.1)
        logging.debug(row)
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

# 滑动
def swipe(device, pos, pos1, time = 1000):
    x, y = pos
    x1, y1 = pos1
    command = "adb -s {0} shell input swipe {1} {2} {3} {4} {5}".format(device, x, y, x1, y1, time)
    os.system(command)


if __name__ == "__main__":
    pass