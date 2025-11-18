import matplotlib.pyplot as plt
import torch
import numpy

# 编造房价
x = torch.linspace(0, 100, 100).type(torch.FloatTensor)
rand = torch.randn(100) * 10
y = x + rand

# 绘制窗口 10 * 8
plt.figure(figsize=(10,8))

x_train = x[:10]
y_train = y[:10]

plt.plot(x_train.data.numpy(), y_train.data.numpy())

plt.xlabel('x')
plt.ylabel('y')
plt.show()