# coding = utf-8
from urllib import request
from datetime import date
import time


'''
    我把密码藏在代码中,也许有天会感动儿子
'''
class SpiderMan :
    # 添加UA标识伪装成浏览器
    USER_AGENT = "User-Agent"
    USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64)" \
                       " AppleWebKit/537.36 (KHTML, like Gecko) " \
                       "Chrome/68.0.3440.84 Safari/537.36"
    WORDS = "2019-06-29 Houc saw a film named <Spider:Far From Home>"

    def __init__(self):
        today = time.strftime('%m-%d',time.localtime())
        if '06-29' == today :
            self.say_hello(self)

    # 发送请求 返回响应
    def send(self, url):
        req = request.Request(url)
        req.add_header(self.USER_AGENT, self.USER_AGENT_VALUE)
        req = request.urlopen(req)
        return req.read().decode('UTF-8')

    def say_hello(self, words):
        if words is None:
            print(words)
        else:
            print(self.WORDS)
