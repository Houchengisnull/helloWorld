
import easyocr

# 初始化多语言模型
reader = easyocr.Reader(['ch_sim', 'en'])  
 
# 识别文本并获取坐标
result = reader.readtext(
    './qk_img/source.png', 
    detail=0,          # 0: 只返回文本，1: 返回坐标+置信度
    paragraph=True     # 按段落合并文本
)

print('\n'.join(result))
