import adb_helper as adb
import cv2_helper as ch
import location_helper as lh
import logging

device = adb.device()
w, h = adb.screen_size(device)

def width():
    return w


def height():
    return h

# 截屏
def screen_capture(path):
    adb.scree_capture(device, path)

# 点击模板位置
def touch_template(source, template):
    adb.scree_capture(device, source)
    match_result = ch.matchTemplate(source, template)
    location = ch.maxLoc(match_result)
    
    if location is not None:
        location = lh.center(template, location)
        location_rand = lh.location_random(location)
        adb.touch(device, location_rand)
        return True
    return False

# 匹配正确模板并点击模板位置
def touch_templates(source, templates):
    adb.scree_capture(device, source)
    pos = match_templates(source, templates)
    if pos is not None:
        touch_location(pos)
        return True
    return False

# 匹配模板 成功返回对应模板得中心点位
def match_templates(source, templates, accuracy=0.90):
    for template in templates:
        logging.debug(">>>>>>>>>> 匹配{0}中".format(template))
        match_result = ch.matchTemplate(source, template)
        location = ch.maxLoc(match_result, accuracy)
        is_match = location is not None
        logging.debug("<<<<<<<<<< 匹配{0}结果:{1}".format(template, is_match))
        if is_match:
            return lh.center(template, location)
    return None


# 模拟点击
def touch_location(pos):
    adb.touch(device, lh.location_random(pos))


# -h/4 向上滑动1/4
# h/4  向下滑动1/4
def swipe_vertical(value, upper=False):
    x = lh.randint_between(w/4, 3/4*w)
    y = lh.randint_between(h/2, 3/4*h)
    x1 = x
    y1 = 0
    if upper:
        y1 = y + value # upper
    else:
        y1 = y - value
    pos = (x, y)
    pos1 = (x1, y1)

    adb.swipe(device, pos, pos1)


if __name__ == "__main__":
    pass