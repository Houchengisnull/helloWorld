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
    
# 切割图片
## source_path      原图片
## template_path    模板图片
## save_path        保存路径
## position         模板图片初始位置
def image_capture(source_path, template_path, save_path, position):
    x, y = position
    source = cv2.imread(source_path)
    template = cv2.imread(template_path)
    h, w = template.shape[:2]
    copy = source.copy()
    copy = copy[y:y+h, x:x+w]
    cv2.imwrite(save_path, copy)

def image_capture_between(source_path, save_path, start_pos, end_pos):
    x, y = start_pos
    x1, y1 = end_pos
    source = cv2.imread(source_path)
    copy = source.copy()
    copy = copy[y:y1, x:x1]
    cv2.imwrite(save_path, copy)