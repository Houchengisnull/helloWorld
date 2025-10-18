import cv2
import numpy
import dictionary as dict
from matplotlib import pyplot as plt

img = cv2.imread(dict.img, 0)
template = cv2.imread(dict.temp3, 0)

th, tw = template.shape[::]
result = cv2.matchTemplate(img, template, cv2.TM_CCOEFF_NORMED)
minVal, maxVal, minLoc, maxLoc = cv2.minMaxLoc(result)
print('最小置信度：{0}\t{1}'.format(minVal, minLoc))
print('最大置信度：{0}\t{1}'.format(maxVal, maxLoc))
topLeft = maxLoc
bottomRight = (topLeft[0] + tw, topLeft[1] + th)
cv2.rectangle(img, topLeft, bottomRight, 255, 2)
plt.subplot(121)
plt.imshow(result, cmap='gray')
plt.title('Matching Result')
plt.xticks([])
plt.yticks([])
plt.subplot(121)
plt.imshow(img, 'gray')
plt.title('Detected Point')
plt.xticks([])
plt.yticks([])
plt.show()