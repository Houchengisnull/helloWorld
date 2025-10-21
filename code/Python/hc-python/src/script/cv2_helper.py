import cv2
import logging

# 模板匹配
def matchTemplate(source_path, template_path):
    source = cv2.imread(source_path)
    template = cv2.imread(template_path)

    return cv2.matchTemplate(source, template, cv2.TM_CCOEFF_NORMED)
    
# 返回置信度最高坐标
def maxLoc(match_result, accuracy = 0.90):
    min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(match_result)
    logging.debug("置信度:{0} {1}".format(max_val, max_loc))
    if max_val >= accuracy:
        return max_loc