# coding = utf-8
from urllib import request
from urllib import parse
from datetime import date
import json
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
    COOKIE = "Cookie"
    WORDS = "2019-06-29 Houc saw a film named <Spider:Far From Home>"
    ENCODING = 'UTF-8'

    def __init__(self):
        today = time.strftime('%m-%d',time.localtime())
        if '06-29' == today :
            self.say_hello(self)

    def new_request(self, url, cookie, data):
        req = request.Request(url)
        req.add_header(self.USER_AGENT, self.USER_AGENT_VALUE)
        if cookie is not None :
            req.add_header(self.COOKIE, cookie)
        req = request.urlopen(req, data)
        return req.read().decode(self.ENCODING)

    # 发送请求 返回响应
    def send(self, url, cookie, data):
        data_byte = parse.urlencode(data).encode(self.ENCODING)
        res = self.new_request(url, cookie, data_byte)
        return res

    def say_hello(self, words):
        if words is None:
            print(words)
        else:
            print(self.WORDS)

    def download(self, url, cookie, data):
        data_byte = parse.urlencode(data).encode(self.ENCODING)
        req = request.Request(url)
        req.add_header(self.USER_AGENT, self.USER_AGENT_VALUE)
        if cookie is not None :
            req.add_header(self.COOKIE, cookie)
        res = request.urlopen(req, data_byte)
        return res.read()

    @staticmethod
    def parse_json(response_string):
        return json.loads(response_string)
