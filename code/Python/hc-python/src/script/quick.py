import time
import random
import datetime
import logging as log
import quick_logging_config

import script_helper as helper
import quick_dictionary as dict

device = '192.168.8.167:38607'
helper.set_device(device)

def main():
    while (True):
        screen_capture()
        # 在任务中心
        if is_in_task_center():
            # 看广告得金币
            log.info("[看广告得金币]")
            touch_(dict.ADVERTISING)
            advertising_process()

        if match_array_([dict.SUCCESS, dict.SUCCESS2, dict.WATCH_ADVERTISING], 0.70):
            log.info("[正在看广告]")
            advertising_process()
            

# 看广告 > 成功领取
# 领取奖励 > 看广告
# 领取奖励 > 领取额外金币
# 领取额外金币 > back * 2 > 成功领取
def advertising_process():
    screen_capture()
    while (not is_in_task_center()):
        screen_capture()
        if touch_array_([dict.REWARD]):
            log.info("[领取奖励]")
            continue
            
        if touch_(dict.REWARD_OTHER_):
            log.info("[领取额外奖励]")
            sleep()
            helper.back()
            helper.back()
            continue

        watching_advertising()

      
def is_in_task_center():
    return match_array_([dict.BOTTOM_BAR, dict.TASK_CENTER]) is not None

def touch_success():
    # *成功领取验证放最后
    if touch_array_([dict.SUCCESS, dict.SUCCESS2], 0.7):
        log.info("[已成功领取奖励]")
        return True
    return False

# 观看广告中
def watching_advertising():
    count = 0
    while (True):
        if count % 5 == 0:
            screen_capture()
            if touch_success() : 
                break
        time.sleep(1)
        count += 1
        log.info("[观看广告]" + str(count) + "秒")

    log.info("[观看广告结束]")

def match_(template, accuracy=0.90):
    return helper.match_template(dict.SOURCE, template, accuracy)

def match_array_(templates, accuracy=0.90):
    return helper.match_templates(dict.SOURCE, templates, accuracy)

def touch_(template, accuracy=0.90):
    return helper.touch_template(dict.SOURCE, template, accuracy)

def touch_array_(templates, accuracy=0.90):
    return helper.touch_templates(dict.SOURCE, templates, accuracy)

def screen_capture():
    helper.screen_capture(dict.SOURCE)
    sleep()
    
def sleep():
    time.sleep(round(random.uniform(0.0, 1.0), 1))


if __name__ == '__main__':
    main()
    