import cv2, numpy

def center(template, locations):
    image = cv2.imread(template)
    x, y = locations
    h, w, channel = image.shape
    return (x + w/2, y + h/2)


def centerBetween(leftTop, rightBottom):
    x1, y1 = leftTop
    x2, y2 = rightBottom
    return ((x1 + x2)/2, (y1 + y2)/2)