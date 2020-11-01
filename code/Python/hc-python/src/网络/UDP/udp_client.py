import socket

target_host = "localhost"
target_port = 9090

# build a socket object
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
# client.bind((target_host, target_port))
client.connect(("localhost", 9090))
client.settimeout(10)
client.sendto("helloworld".encode(), (target_host, target_port))
# accept message
try:
    data, addr = client.recvfrom(4096)
    print(data)
except Exception as e:
    print("accept udp packet failed")
    print(e)

client.close()