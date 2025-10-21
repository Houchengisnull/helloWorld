import cv2, numpy, random

# 点击偏移范围 [0, x]
touch_range = 8

def center(template, locations):
    image = cv2.imread(template)
    x, y = locations
    h, w, channel = image.shape
    return (x + w/2, y + h/2)


def centerBetween(leftTop, rightBottom):
    x1, y1 = leftTop
    x2, y2 = rightBottom
    return ((x1 + x2)/2, (y1 + y2)/2)


def location_random(location):
    x, y = location

    x = position_random(x)
    y = position_random(y)

    return (x, y)


def position_random(value):
    rand = random.randint(1, 10000)
    if rand % 2 == 0:
        value = value + random.randint(0, touch_range)
    else:
        value = value - random.randint(0, touch_range)
    return value


def randint_between(min, max):
    return random.randint(int(min), int(max))