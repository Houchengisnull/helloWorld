import socket
# import time

target_host = "localhost"
target_port = 9090

# build a socket object
client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# connect to tcp client
client.connect((target_host, target_port))

# send 2kb message
# 2.7
# client.send("GET / HTTP/1.1\r\nHost: baidu.com\r\n\r\n")
# 3.6
#client.send("GET / HTTP/1.1\r\nHost: baidu.com\r\n\r\n".encode())
client.send("hello world".encode())
# time.sleep(10) 阻塞线程 验证socketReset exception
# accept message
response = client.recv(4096)
# 2.7
# print response
# 3.6
print(response)
client.close()