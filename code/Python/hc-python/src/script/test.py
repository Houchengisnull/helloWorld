import time
import random
import datetime
import logging as log
import douyin_logging_config as douyin_logging_config
import script_helper as helper
import quick_dictionary as dict

device = '192.168.8.167:38607'
helper.set_device(device)
helper.screen_capture(dict.SOURCE)