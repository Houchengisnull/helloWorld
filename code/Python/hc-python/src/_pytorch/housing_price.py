import matplotlib.pyplot as plt
import torch
import numpy as np

# 编造房价
# linspace构造0~100之间的均匀数字作为时间变量
# pytorch 1.7取消默认参数steps=100
x = torch.linspace(0, 100, steps=100).type(torch.FloatTensor)
# 噪声
rand = torch.randn(100) * 10
y = x + rand


# 训练数据
x_train = x[:-10]
y_train = y[:-10]
# 测试数据
x_test = x[-10:]
y_test = y[-10:]

# 绘制窗口 10 * 8
#plt.figure(figsize=(10,8))
# plt.plot(x_train.data.numpy(), y_train.data.numpy(), 'o')
# plt.xlabel('x')
# plt.ylabel('y')
# plt.show()

# 定义模型
# y=ax+b

# train
# 定义自动微分变量a b
a = torch.rand(1, requires_grad = True)
b = torch.rand(1, requires_grad = True)

learning_rate = 0.0001

for i in range(1000):
    # 计算在a b条件下模型预测值
    predictions = a.expand_as(x_train) * x_train + b.expand_as(x_train)
    # 将所有训练数据代入模型y=ax+b
    loss= torch.mean((predictions - y_train) ** 2)
    print('loss:', loss)
    loss.backward()
    a.data.add_(- learning_rate * a.grad.data)
    b.data.add_(- learning_rate * b.grad.data)
    a.grad.data.zero_()
    b.grad.data.zero_()

# 绘制窗口
x_data = x_train.data.numpy()
plt.figure(figsize = (10, 7))
xplot, = plt.plot(x_data, y_train.data.numpy(), 'o') # 绘制散点图
yplot, = plt.plot(x_data, a.data.numpy() * x_data + b.data.numpy()) # 绘制拟合直线

plt.xlabel('X')
plt.ylabel('Y')
str1 = str(a.data.numpy()[0]) + 'x + ' + str(b.data.numpy()[0])
plt.legend([xplot, yplot], ['Data', str1])
plt.show()

# 预测
predictions = a.expand_as(x_test) * x_test + b.expand_as(x_test)

x_data = x_train.data.numpy()
x_pred = x_test.data.numpy()
plt.figure(figsize=(10, 7))
plt.plot(x_data, y_train.data.numpy(), 'o')
plt.plot(x_pred, y_test.data.numpy(), 's')
x_data = np.r_[x_data, x_test.data.numpy()]
plt.plot(x_data, a.data.numpy() * x_data + b.data.numpy())
plt.plot(x_pred, a.data.numpy() * x_pred + b.data.numpy(), 'o')

plt.xlabel('X')
plt.ylabel('Y')
str1 = str(a.data.numpy()[0]) + 'x + ' + str(b.data.numpy()[0])
plt.legend([xplot, yplot], ['Data', str1])
plt.show()