from urllib import request
import json
#https://www.cnblogs.com/zhaof/p/6930955.html
#适用于xml html
#from bs4 import BeautifulSoup

# post
#构造头文件,模拟浏览器访问
#'music.163.com/api/v1/resource/comments/R_SO_4_516997458?limit=20&offset=40'
#url="music.163.com/api/v1/resource/comments/R_SO_4_516997458?limit=20&offset=40"
#url="https://music.163.com/weapi/v1/resource/comments/R_SO_4_864647841?csrf_token="
#url="https://music.163.com/weapi/v1/resource/comments/R_SO_4_864647841?csrf_token="
'''
url="https://music.163.com/api/v1/resource/comments/R_SO_4_516997458?limit=20&offset=40"
headers = {'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'}
data = {'params':'BPjSvSoUfz/VkN1mwiGY2fyyg2PnlEGrAo5eYuPhju+o5/VVXVN7gWo+C5baq5JNvy0j+MbK1nxWS34JXm7Cg2tjt6qb3JDumnuYDdAUWot6JWTAJJEgcDJf1k6OmruQk0fPDUtqh65jGpbe8hudrGN+ixrbc4ftQVW1nD5/V91PVdL092G4dKjNaXabPwWY','encSecKey':'ae43a7422c485ca525a3b48054d2dfc572662a778bdea8e86fbaa96468149f9636468374b87c012f5dc506ebc27b61362e7cddced627b7a9886dd298bc6f7ef8a5055a24174d1b63cf236ba3034c3da41428bc3bdaf63d846677331411a39a72147df7712f25a8db814a4c7f5c67200b722f666beeaad21d9661b739cd5dd7f4'}
postdata=urllib.parse.urlencode().encode('utf8')  #进行编码
request_header = request.Request(url,headers=headers,data=postdata)
response = request.urlopen(request_header).read().decode('UTF-8')
json_dict = json.loads(response)
hot_comments=json_dict['hotComments']

for item in hot_comments:
    print(item['user']['nickname'] + '\t' + item['content'])
''' 
f = open("spider.txt","w")
#get
for i in range(1,2911):
    url="https://music.163.com/api/v1/resource/comments/R_SO_4_516997458?limit=20&offset=" + str(i)
    headers = {'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'}
    request_header = request.Request(url,headers=headers)
    response = request.urlopen(request_header).read().decode('UTF-8')
    print(str(i) + response)
    items = json.loads(response)
    for item in items['comments']:
        if item['user']=='Karen2yy':
            #print(item['time'] + '\t' +  item['content'])
            f.write(item['time'] + '\t' +  item['content'] + '\n')
f.close()