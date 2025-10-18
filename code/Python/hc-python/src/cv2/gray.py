import cv2
import time
import dictionary as dict

# show
img = cv2.imread(dict.img, cv2.IMREAD_GRAYSCALE)
cv2.namedWindow('girl')
cv2.imshow('girl', img)
key = cv2.waitKey()