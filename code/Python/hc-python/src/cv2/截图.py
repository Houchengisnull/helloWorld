import cv2
import dictionary as dict

img = cv2.imread(dict.img)
temp = cv2.imread(dict.temp)
result = cv2.matchTemplate(img, temp, cv2.TM_CCOEFF_NORMED)

min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(result)
# 起始点位
x, y = max_loc

h, w = temp.shape[:2]
print("width", w)
print("height", h)

copy = img.copy()
copy = copy[y:y+h, x:x+w]

# 绘图测试
# cv2.rectangle(copy, (x, y), (x+w, y+h), (0, 255, 0), 2)
# cv2.imshow('copy', copy)
# cv2.waitKey()

cv2.imwrite(dict.copy, copy)

temp2 = cv2.imread(dict.temp2)
result2 = cv2.matchTemplate(copy, temp2, cv2.TM_CCOEFF_NORMED)
min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(result2)
print("置信度", max_val)
print(max_val)