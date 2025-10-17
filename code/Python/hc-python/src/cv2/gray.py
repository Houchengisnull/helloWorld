import cv2
import time

# show
img = cv2.imread('./src/cv2/girl.jpg', cv2.IMREAD_GRAYSCALE)
cv2.namedWindow('girl')
cv2.imshow('girl', img)
key = cv2.waitKey()