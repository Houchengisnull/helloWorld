from pyftpdlib.authorizers import DummyAuthorizer  #傻瓜式授权
from pyftpdlib.handlers import FTPHandler          #FTP句柄
from pyftpdlib.servers import FTPServer            #FTP服务


print("Hello world")

authorizer = DummyAuthorizer()                     #新建一个用户组    

authorizer.add_user("fan", "root", "D:/", perm="elr")#将用户名，密码，指定目录，权限 添加到里面
#这个是添加匿名用户,任何人都可以访问，如果去掉的话，需要输入用户名和密码，可以自己尝试
#authorizer.add_anonymous("D:/")

#初始化FTP句柄
handler = FTPHandler
handler.authorizer = authorizer

#开启服务器
server = FTPServer(("127.0.0.1", 21), handler)
server.serve_forever()