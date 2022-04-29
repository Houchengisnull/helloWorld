import json
from urllib import request
from idlelib.iomenu import encoding

headers_ = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36'
    , 'Referer': 'https://ke.qq.com/user/index/index.html'
    , "cookie": 'pgv_pvid=7634321150; RK=FAZh6o+bGa; ptcz=bdbdbb3d8bd6da9858c660a97c37984474c97e429a3778be4d064d524911576f; pac_uid=0_fa1d859045b0d; iip=0; tdw_auin_data=-; tdw_data_testid=; tdw_data_flowid=; tdw_first_visited=1; _qpsvr_localtk=0.6860427764691379; uin=o0609937364; skey=@vs0LdmJgp; luin=o0609937364; lskey=00010000f8c134f302d1f80b0a2e8c5606f8aaaf12d0c96d9589ec828d6f3aef173eee58882bf9bf60e837f4; pt4_token=utJt04SRtHdQW4oFU6VRJRC4ny0MGdX*67UzMKpXxsw_; p_skey=dqqATtjRxlw4AwTUrJRsX4mFYbJBBr5Qw1gegVfnVYg_; p_lskey=00040000a216ef233afae4ddafc21c53970161b3a897908594fe56d4b00711ecafcf6d52552f0284424dbd0d; auth_version=2.0; mix_login_mode=true; uid_type=0; uin=609937364; p_uin=609937364; p_luin=609937364; uid_uin=609937364; uid_origin_uid_type=0; uid_origin_auth_type=0; _pathcode=0.16701906915915243; ke_login_type=1; tdw_data={"ver4":"www.baidu.com","ver5":"","ver6":"16","refer":"www.baidu.com","from_channel":"16","path":"rB-0.16701906915915243","auin":"-","uin":"609937364","real_uin":"9937364"}; sessionPath=165120824671875859196745; tdw_data_new_2={"auin":"-","sourcetype":"from","sourcefrom":"16","ver9":"609937364","uin":"609937364","visitor_id":"35516942611548785","ver10":"","url_page":"","url_module":"","url_position":"","sessionPath":"165120824671875859196745"}'
}

url = "https://ke.qq.com/cgi-bin/user/user_center/get_plan_detail?cid=287404&uin=609937364&term_id=100471025&bkn=2031184298&t=0.6120"
req = request.Request(url = url, headers = headers_)


res = request.urlopen(req)
res_body = res.read().decode("UTF-8")

data = json.loads(res_body)
chapters = data['result']['plan']['chapter_list']
sub_course_list = chapters[0]['sub_course_list']
task_count = 0

for course in sub_course_list:
    # info = "| " + course['name'] + " | " + str(course['task_list']) + " |"
    tasks = course['task_list']
    #print("- - - - - - - - - - - - -- - - - - - - - - - - - -")
    #print("├" + course['name'] + "\t\t\t\t\t任务数:" + str(len(tasks)))
    #print("" + course['name'] + "\t\t\t\t\t任务数:" + str(len(tasks)))
    #print("- - - - - - - - - - - - -- - - - - - - - - - - - -")
    for task in tasks:
        #print("\t├" + task['name'])
        print(course['name'] + "\t" + task['name'])
        task_count+=1
    # print("- - - - - - - - - - - - -- - - - - - - - - - - - -")
print("- - - - - - - - - - - - -- - - - - - - - - - - - -")
print("任务总数:" + str(task_count) + "\t 课程数:" + str(len(sub_course_list)))


