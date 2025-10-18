import cv2
import dictionary as dict

img = cv2.imread(dict.img)



cp = cv2.imwrite(dict.copy, img)