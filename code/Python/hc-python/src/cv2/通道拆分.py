import cv2
import dictionary as dictionary
lena = cv2.imread(dictionary.img)
cv2.imshow('lena', lena)

# 通过索引拆分
b = lena[:,:,0]
g = lena[:,:,1]
r = lena[:,:,2]

cv2.imshow('b', b)
# cv2.imshow('g', g)
# cv2.imshow('r', r)
lena[:,:,0] = 0
cv2.imshow('lena b = 0', lena)

cv2.waitKey()
cv2.destroyAllWindows()