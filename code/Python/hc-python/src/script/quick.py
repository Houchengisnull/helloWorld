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
    screen_capture()
    while (True):
        # 在任务中心
        if helper.match_templates(dict.SOURCE, [dict.BOTTOM_BAR, dict.TASK_CENTER]):
            touch_(dict.ADVERTISING)
            log.info("[看广告得金币]")
            screen_capture()
        elif helper.match_templates(dict.SOURCE, [dict.SUCCESS, dict.SUCCESS2], 0.7): 
            advertising_process()
            


# 看广告 > 成功领取
# 领取奖励 > 看广告
# 领取奖励 > 领取额外金币
# 领取额外金币 > back * 2 > 成功领取
def advertising_process():
    while (True):
        screen_capture()
        if helper.touch_templates(dict.SOURCE, [dict.REWARD]):
            log.info("[领取奖励]")
            continue

        if helper.touch_template(dict.SOURCE, dict.REWARD_OTHER_):
            log.info("[领取额外奖励]")
            time.sleep(1)
            helper.back()
            helper.back()
            continue

        watching_advertising()

        if helper.match_templates(dict.SOURCE, [dict.BOTTOM_BAR, dict.TASK_CENTER]):
            return
    

def touch_success():
    
    # *成功领取验证放最后
    if helper.touch_templates(dict.SOURCE, [dict.SUCCESS, dict.SUCCESS2], 0.7):
        log.info("[已成功领取奖励]")
        return True
    return False

def match_(template, accuracy=0.90):
    return helper.match_template(dict.SOURCE, template, accuracy)

def touch_(template, accuracy=0.90):
    helper.touch_template(dict.SOURCE, template, accuracy)

# 观看广告中
def watching_advertising(duration = 30):
    while (duration >= 0):
        log.info("[观看广告中]\t剩余时间:{0}".format(duration))
        time.sleep(1)
        duration -= 1

        if duration % 5 == 0:
            screen_capture()
            if touch_success() : break

    log.info("[观看广告结束]")


def screen_capture():
    helper.screen_capture(dict.SOURCE)


if __name__ == '__main__':
    main()
    