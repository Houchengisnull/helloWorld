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
    is_video = False
    while (True):
        screen_capture()
        # 在任务中心
        if is_in_task_center():
            log.info('[任务中心]')
            
            if is_video :
                # 看视频赚金币
                log.info("[看视频赚金币]")
                touch_(dict.VIDEO)
                video_process()
                is_video = False
            
            # 看广告
            if touch_(dict.ADVERTISING_1):
                log.info("[看广告]")    
                advertising_process()
            
            # 开宝箱
            if touch_(dict.TREASURE):
                log.info("[开宝箱]")
                if touch_(dict.TREASURE_ADVERTISING):
                    screen_capture()
                    log.info("[开宝箱] - [去看广告]")
                    advertising_process()

            # 看广告得金币
            if touch_(dict.ADVERTISING):
                log.info("[看广告得金币]")
                advertising_process()

            # 看指定视频
            # TODO

        # 在首页
        if is_in_homepage():
            log.info('[首页]')
            video_process()
            
        if is_in_watching_live() or is_in_watching_advertising():
            advertising_process()



def video_process():
    # 45分钟
    start_time = time.time()
    elapsed_time = time.time() - start_time
    while elapsed_time <= 2700:
        log.info("[看视频赚金币] elapsed_time:{0}分钟".format(round(elapsed_time/60),1))
        sleep_rand_seconds()
        swipe_lower()
        elapsed_time = time.time() - start_time

            
# 看广告 > 成功领取
# 领取奖励 > 看广告
# 领取奖励 > 领取额外金币
# 领取额外金币 > back * 2 > 成功领取
def advertising_process():
    log.info(">>>>> >>>>> >>>>>")
    # 增加延迟
    sleep()
    screen_capture()

    while (not is_in_task_center()):
        sleep()
        screen_capture()
        if touch_array_([dict.REWARD, dict.REWARD2]):
            log.info("[领取奖励]")
            sleep()
            continue
            
        if touch_(dict.REWARD_OTHER_):
            log.info("[领取额外奖励]")
            sleep()
            helper.back()
            helper.back()
            continue

        if is_in_playlet():
            log.info("[观看短剧]")
            helper.back()
            continue

        if is_in_watching_live() :
            log.info("[观看直播中]")
            watching_live()
            continue
        
        if is_in_watching_advertising():
            log.info("[观看广告中]")
            watching_advertising()
    log.info("<<<<< <<<<< <<<<<")

# 观看广告中
def watching_advertising():
    count = 0
    while (count <= 30):
        if count % 5 == 0:
            screen_capture()
            if touch_success() : 
                break
            if not is_in_watching_advertising():
                break
        time.sleep(1)
        count += 1
        log.info("[观看广告]" + str(count) + "秒")

    log.info("[观看广告结束]")

def watching_live():
    count = 0
    while (count <= 35):
        time.sleep(1)
        count += 1
        log.info("[观看直播] " + str(count) + "秒")
    helper.back()
    log.info("[观看直播结束]")

def is_in_watching_live():
    return match_array_([dict.FOLLOW])

def is_in_homepage():
    return match_array_([dict.BOTTOM_BAR_BLACK, dict.HOMEPAGE]) is not None

def is_in_task_center():
    return match_array_([dict.BOTTOM_BAR, dict.TASK_CENTER]) is not None

def is_in_watching_advertising():
    return match_array_([dict.SUCCESS, dict.SUCCESS2, dict.WATCHING_ADVERTISING], 0.70) is not None

def is_in_playlet():
    return match_array_([dict.PLAYLET])


def match_(template, accuracy=0.90):
    return helper.match_template(dict.SOURCE, template, accuracy)

def match_array_(templates, accuracy=0.90):
    return helper.match_templates(dict.SOURCE, templates, accuracy)

def touch_(template, accuracy=0.90):
    return helper.touch_template(dict.SOURCE, template, accuracy)

def touch_array_(templates, accuracy=0.90):
    return helper.touch_templates(dict.SOURCE, templates, accuracy)

def touch_success():
    # *成功领取验证放最后
    if touch_array_([dict.SUCCESS, dict.SUCCESS2], 0.7):
        log.info("[已成功领取奖励]")
        return True
    return False

def screen_capture():
    helper.screen_capture(dict.SOURCE)
    sleep()

def sleep_rand_seconds():
    time.sleep(random.uniform(5, 15))

def sleep():
    time.sleep(round(random.uniform(0.0, 1.0), 1))

def swipe_lower():
    h = helper.height()
    helper.swipe_vertical(h/4)

if __name__ == '__main__':
    main()