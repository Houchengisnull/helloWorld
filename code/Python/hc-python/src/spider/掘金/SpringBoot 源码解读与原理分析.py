# 掘金网
## SpringBoot 源码解读与原理分析
## https://juejin.im/book/5da3bc3d6fb9a04e35597a76

import json
from urllib import request
from idlelib.iomenu import encoding
# from bs4 import BeautifulSoup as bs

# 添加UA标识伪装成浏览器
user_agent = "User-Agent"
user_agent_value = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36"

#url = 'https://juejin.im/book/5da3bc3d6fb9a04e35597a76/section/5da3bc3d518825420a2810c4'
# getListSection
# 掘金 getListSection接口 获取所有章节的SectionId
urlListSection = "https://xiaoce-cache-api-ms.juejin.im/v1/getListSection?uid=5cbd5c8df265da03937867bd&client_id=1588918196194&token=eyJhY2Nlc3NfdG9rZW4iOiJXYUdySHo5RGhZcUNvTFhXIiwicmVmcmVzaF90b2tlbiI6IkxCd0I4eXMyWlJSWlQ5S1UiLCJ0b2tlbl90eXBlIjoibWFjIiwiZXhwaXJlX2luIjoyNTkyMDAwfQ%3D%3D&src=web&id=5da3bc3d6fb9a04e35597a76"
req = request.Request(urlListSection)
req.add_header(user_agent, user_agent_value)
req = request.urlopen(req)
res = req.read().decode("UTF-8")
listSection = json.loads(res)

num = 1
for section in listSection["d"]:
    # 掘金 getSection接口
    urlSection = "https://xiaoce-cache-api-ms.juejin.im/v1/getSection?uid=5cbd5c8df265da03937867bd&client_id=1588918196194&token=eyJhY2Nlc3NfdG9rZW4iOiJXYUdySHo5RGhZcUNvTFhXIiwicmVmcmVzaF90b2tlbiI6IkxCd0I4eXMyWlJSWlQ5S1UiLCJ0b2tlbl90eXBlIjoibWFjIiwiZXhwaXJlX2luIjoyNTkyMDAwfQ%3D%3D&src=web&sectionId=" + section["_id"]
    req = request.Request(urlSection)
    req.add_header(user_agent, user_agent_value)
    req = request.urlopen(req)
    res = req.read().decode("UTF-8")
    sectionContent = json.loads(res)
    data = sectionContent['d']
    # title
    file = open(str(num) + "."+ data['title'] + ".md", "w", encoding = "UTF-8")
    num += 1
    # content
    file.write(data['content'])
    file.close()
    
'''
# getSection
url = "https://xiaoce-cache-api-ms.juejin.im/v1/getSection?uid=5cbd5c8df265da03937867bd&client_id=1555913869643&token=eyJhY2Nlc3NfdG9rZW4iOiJpQ2tFR0kyNm15bXJjUmN4IiwicmVmcmVzaF90b2tlbiI6IkV1V0FGSm5CVzVrRFk4TlIiLCJ0b2tlbl90eXBlIjoibWFjIiwiZXhwaXJlX2luIjoyNTkyMDAwfQ%3D%3D&src=web&sectionId=5c0374a06fb9a049d37ed783"

req = request.Request(url)

# 模拟浏览器
req.add_header(user_agent, user_agent_value)

req = request.urlopen(req)
res = req.read().decode("UTF-8")

# soup = bs(res, "html.parser")
items = json.loads(res)
# print(items['d']['title'])
# print(items['d']['content'])
# print(soup.prettify())
'''