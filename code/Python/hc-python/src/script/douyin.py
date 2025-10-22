import time
import random
import datetime
import logging as log
import douyin_logging_config
import script_helper as helper
import douyin_dictionary as dict


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


# 看广告赚金币
def advertising():
    helper.screen_capture(dict.SOURCE)
    # 是否满足领取条件
    match_result = helper.match_templates(dict.SOURCE, [dict.ADVER, dict.ADVER_20000])
    if match_result is None:
        log.warning("[看广告赚金币]未找到")
        return False
    template, position = match_result
    helper.image_capture(dict.SOURCE, template, dict.COPY, position)
    if helper.match_templates(dict.COPY, [dict.GO_REWARD]) is None:
        log.warning("[看广告赚金币]未找到")
        return False
    
    helper.touch_templates(dict.SOURCE, [dict.ADVER, dict.ADVER_20000])
    log.info("[看广告赚金币]...")

    while(advertising_process()):
        pass

    return True

# 领取宝箱
def treasure():
    # 点击宝箱
    if not helper.touch_templates(dict.SOURCE, [dict.TREA]):
        log.warning("[开宝箱得金币]未找到")
        return False

    log.info("[开宝箱]")

    # 网路延迟
    time.sleep(3)


    # 点击'看广告再赚'进入广告流程
    if not helper.touch_templates(dict.SOURCE, [dict.TREA_ADEV]):
        log.warning("[看广告再赚]未找到")
        return False
    
    log.info("[看广告再赚]")
    
    while(advertising_process()):
        pass

    return True

# 广告流程
def advertising_process():
    # 观看广告 首次或者继续领取奖励才有广告
    log.info("[观看广告]...start")
    watching_advertising()
    log.info("[观看广告]...end")

    # 观看完毕
    # 存在四种情况:
    # a. 在直播间中
    # b. 在小游戏静态页面
    
    # c**. 点击'领取奖励'
    # c1. 下一个广告
    # c2. 无广告 990 80

    # d. 已处于'领取成功'状态
    # d1. 点击'领取成功' 触发观看下一个广告
    # d2. 点击'领取成功' 触发评价或关闭

    # e**. 评价
    
    while (True):
        time.sleep(1)
        helper.screen_capture(dict.SOURCE)

        # 是否观看下一广告： 根据'领取奖励'判断
        if helper.touch_templates(dict.SOURCE, [dict.REWARD]):
            log.info("[领取奖励]")
            return True

        # 评价或关闭
        if helper.touch_templates(dict.SOURCE, [dict.EVAL]):
            log.info('[评价]')
            return False

        # 直播间判断: 根据是否有关注判断当前流程是否在直播间内
        is_live = helper.match_templates(dict.SOURCE, [dict.FOLLOW]) is not None
        if is_live:
            log.info("当前在直播间内，即将退出")
            helper.touch_location((1020,160))
            continue

        is_game = helper.match_templates(dict.SOURCE, [dict.DOWNLOAD, dict.INSTALL]) is not None
        if is_game:
            log.info("当前在小游戏下载页面，即将退出")
            helper.touch_template(dict.SOURCE, dict.BACK)
            continue

        # 领取成功判断：领取成功只需要 0.80的置信度, 保险起见设置为0.70
        if helper.touch_templates(dict.SOURCE, [dict.SUCCESS], 0.70):
            log.info("[领取成功]")
            continue

        # 无广告
        has_adver = helper.match_templates(dict.SOURCE, [dict.ADVER_NONE]) is not None
        if has_adver :
            log.info("[当前无新视频]")
            helper.touch_location((990, 80))
            # 点击'坚持退出'


# 观看广告中
def watching_advertising(duration = 30):
    while (duration >= 0):
        log.debug("[观看广告中]\t剩余时间:{0}".format(duration))
        time.sleep(1)
        duration -= 1

'''
逛街赚钱
'''
def shopping():
    if helper.touch_templates(dict.SOURCE, [dict.SHOPPING]):
        log.info('[逛街赚钱]')
    else:
        log.warning('[逛街赚钱]未找到')
    
    duration = 90
    while (duration >= 0):
        time.sleep(1)
        duration -= 1
        log.debug("[逛街中]\t剩余时间:" + str(duration))
        # 下滑
        swipe_lower()

def main():
    while (True) :
        if treasure():
            treasure_count += 1
            log.info("[开宝箱得金币]执行:{0}".format(treasure_count))

        if advertising():
            advertising_count += 1
            log.info("[看广告赚金币]执行:{0}".format(advertising_count))
        time.sleep(60)

if __name__ == "__main__":
    main()
    # shopping()