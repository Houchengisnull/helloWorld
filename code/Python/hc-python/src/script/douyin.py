import time
import random
import datetime
import logging
import log_config
import script_helper as helper
import dy_dictionary as dict

# 记录下滑次数
stack_lower = 0

def swipe_upper():
    global stack_lower
    stack_lower -= 1
    h = helper.height()
    helper.swipe_vertical(h/4, True)

def swipe_lower():
    global stack_lower
    stack_lower += 1
    h = helper.height()
    helper.swipe_vertical(h/4)

# 看广告赚金币 20min
def watch_advertising():
    while(not helper.touch(dict.SOURCE, dict.ADVER)) :
        swipe_lower()
        time.sleep(1)
    logging.info('看广告赚金币')
    time.sleep(35)
    
    success()

    while(reward()):
        success()
    
    eval()

# 开宝箱得金币 15min
# - 
def open_treasure():
    helper.touch_location((900, 2200))
    logging.info('开宝箱得金币')
    time.sleep(3)
    
    while(not helper.touch(dict.SOURCE, dict.TREA_ADEV)) :
        logging.warning("未匹配" + dict.TREA_ADEV)
        time.sleep(1)

        helper.touch_location((900, 2200))
        logging.info('开宝箱得金币')
        time.sleep(3)
    logging.info('看广告再赚')
    time.sleep(35)

    success()

    while(reward()):
        success()
    
    eval()

    

# 领取奖励
def reward():
    if helper.touch(dict.SOURCE, dict.REWARD):
        logging.info('领取奖励')
        time.sleep(35)
        return True
    return False
        

# 右上角'领取成功'
def success():
    helper.touch_location((900, 130))
    logging.info('领取成功')
    time.sleep(3)

# 评价或关闭
def eval():
    if random.randint(0, 3) % 2 != 0:
        logging.info('评价')
        helper.touch(dict.SOURCE, dict.EVAL)
    else:
        logging.info('关闭')
        helper.touch_location((850, 850))


def main():
    advertising_interval = 20 * 60
    
    advertising_start_time = datetime.datetime.now()
    advertising_count = 0

    treasure_interval = 20 * 60
    treasure_start_time = datetime.datetime.now()
    treasure_count = 0

    while (True) :
        now = datetime.datetime.now()
        advertising_time_diff = now - advertising_start_time
        treasure_time_diff = now - treasure_start_time

        # if (advertising_time_diff.seconds > advertising_interval):
        if (advertising_time_diff.seconds > advertising_interval) or advertising_count == 0:
            watch_advertising()
            advertising_start_time = now
            advertising_count += 1
            logging.info("'看广告赚金币'执行:{0}".format(advertising_count))
        
        # if (treasure_time_diff.seconds > treasure_interval):
        if (treasure_time_diff.seconds > treasure_interval) or treasure_count == 0:
            open_treasure()
            treasure_start_time = now
            treasure_count += 1
            logging.info("'开宝箱得金币'执行:{0}".format(treasure_count))

        time.sleep(60)


if __name__ == "__main__":
    main()