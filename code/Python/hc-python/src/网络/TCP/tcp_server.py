import socket
import threading
# import time
bind_ip = "localhost"
bind_port = 9090

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((bind_ip, bind_port))
# 最大连接数设置为5 即监听5个连接
server.listen(5)

print("[*] Listening on %s:%d" % (bind_ip, bind_port))

# 客户处理线程
def handle_client(client_socket):
    request = client_socket.recv(1024)
    print("[*] Received %s " % request)
    
    client_socket.send("ACK".encode())
    client_socket.close()
    print("close")
    
while True:
    client,addr = server.accept()
    print("[*] accepted connection from %s:%d" % (addr[0], addr[1]))
    
    # 挂起客户端线程，处理传入数据
    client_handler = threading.Thread(target=handle_client, args=(client,))
    client_handler.start()
