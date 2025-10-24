import easyocr
import time
import logging as log

# 初始化多语言模型
init_time_ = time.time()
reader = easyocr.Reader(['ch_sim', 'en'])
log.info(f"[OCR初始化] 耗时{time.time() - init_time_:.2f}秒")

def read(path):
    start_time = time.time()
    texts = reader.readtext(path, detail=0)
    log.info("[OCR识别] {0}".format(texts))
    end_time = time.time()

    log.info(f"[OCR识别] 耗时{end_time-start_time:.2f}秒")

    return texts

if __name__ == '__main__':
    result = read("./qk_img/source.png")
