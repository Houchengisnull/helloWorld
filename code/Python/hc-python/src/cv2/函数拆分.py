import cv2
import dictionary

img = cv2.imread(dictionary.img)
b, g, r = cv2.split(img)


cv2.imshow('b', b)
cv2.imshow('g', g)
cv2.imshow('r', r)

cv2.waitKey()
cv2.destroyAllWindows()