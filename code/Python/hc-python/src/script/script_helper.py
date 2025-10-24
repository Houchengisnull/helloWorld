import adb_helper as adb
import cv2_helper as ch
import location_helper as lh
import logging

# device = 'adb-f8251985-IMF5Fr._adb-tls-connect._tcp'
# device = 'adb-8a466557-HBXAaz._adb-tls-connect._tcp'

device = None

def set_device(id):
    global device 
    device = id

def width():
    w, h = adb.screen_size()
    return w


def height():
    w, h = adb.screen_size()
    return h

# 截屏
def screen_capture(path):
    adb.scree_capture(device, path)

# 切割图片 在source中将template切割出来
# match_result :(template, location)
def image_capture(source, template, copy, position):
    ch.image_capture(source, template, copy, position)

def image_capture_between(source, template, start, end):
    ch.image_capture_between(source, template, start, end)

#
def screencap_and_touch(source, template_obj, accuracy = 0.9):
    adb.scree_capture(device, source)
    if isinstance(template_obj, list):
        return touch_templates(source, template_obj, accuracy)
    else:
        return touch_template(source, template_obj, accuracy)

# 点击模板位置
def touch_template(source, template, accuracy = 0.90):
    return touch_templates(source, [template], accuracy)

# 匹配正确模板并点击模板位置
def touch_templates(source, templates, accuracy=0.90):
    result = match_templates(source, templates, accuracy)
    
    if result is not None:
        template, pos = result
        touch_location(lh.center(template, pos))
        return True
    return False

def match_template(source, template, accuracy=0.90):
    return match_templates(source, [template], accuracy)

# 匹配模板 成功返回(template, location)
def match_templates(source, templates, accuracy=0.90):
    for template in templates:
        logging.debug("匹配[{0}]中".format(template))
        match_result = ch.matchTemplate(source, template)
        location = ch.maxLoc(match_result, accuracy)
        is_match = location is not None
        logging.debug("匹配[{0}]结果:{1}".format(template, is_match))
        if is_match:
            return (template, location)
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


def back():
    adb.back(device)


if __name__ == "__main__":
    pass