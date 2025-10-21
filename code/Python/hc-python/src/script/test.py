import time
import random
import datetime
import logging as log
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
def advertising():
    if not helper.touch_templates(dict.SOURCE, [dict.ADVER, dict.ADVER_20000]) :
        log.warning("'看广告赚金币' 未找到")
        return False

    while(advertising_process()):
        pass

# 领取宝箱
def treasure():
    helper.touch_location((900, 2200))
    log.info('开宝箱得金币')
    time.sleep(3)
    
    while(not helper.touch(dict.SOURCE, dict.TREA_ADEV)) :
        log.warning("未匹配" + dict.TREA_ADEV)
        time.sleep(1)

        helper.touch_location((900, 2200))
        log.info('开宝箱得金币')
        time.sleep(3)
    log.info('看广告再赚')

    while(advertising_process()):
        pass

# 广告流程
def advertising_process():
    # 观看广告 首次或者继续领取奖励才有广告
    log.info("观看广告中...start")
    watching_advertising()
    log.info("观看广告中...end")

    # 观看完毕
    # 存在四种情况:
    # a. 在直播间中
    # b. 在小游戏静态页面
    # c. 已处于'领取成功'状态
    # c1. 点击'领取成功' 触发观看下一个广告
    # c2. 点击'领取成功' 触发评价或关闭
    # d. 点击'领取奖励'
    # d1. 下一个广告
    # d2. 无广告 990 80
    while (True):
        time.sleep(1)
        helper.screen_capture(dict.SOURCE)

        # 直播间判断: 根据是否有关注判断当前流程是否在直播间内
        follow_pos = helper.match_templates(dict.SOURCE, [dict.FOLLOW])
        if follow_pos is not None:
            log.info("当前在直播间内，即将退出")
            helper.touch_location((1020,160))

        # 领取成功判断：领取成功只需要 0.80的置信度, 保险起见设置为0.70
        success_pos = helper.match_templates(dict.SOURCE, [dict.SUCCESS], 0.70)
        if success_pos is not None:
            log.info("领取成功")
            helper.touch_location((900, 130))
        else:
            log.error("未处于'领取成功'状态")

        # 是否观看下一广告： 根据'领取奖励'判断
        reward_pos = helper.match_templates(dict.SOURCE, [dict.REWARD])
        if reward_pos is not None:
            log.info("领取奖励")
            helper.touch_location(reward_pos)

        # 无广告
        adver_none_pos = helper.match_templates(dict.SOURCE, [dict.ADVER_NONE])
        if adver_none_pos is not None:
            log.info("当前无新视频")
            helper.touch_location((990, 80))
            # 点击'坚持退出'

        # 评价或关闭
        eval_pos = helper.match_templates(dict.SOURCE, [dict.EVAL])
        if eval_pos is not None:
            if random.randint(0, 3) % 2 != 0:
                log.info('评价')
                helper.touch(dict.SOURCE, dict.EVAL)
            else:
                log.info('关闭')
                helper.touch_location((850, 850))
            return False
        
        return True
    


# 观看广告中
def watching_advertising(duration = 30):
    while (duration >= 0):
        log.debug("观看广告中...剩余时间:{0}".format(duration))
        time.sleep(1)
        duration -= 1

if __name__ == "__main__":
    # advertising()