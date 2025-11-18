import torch
import intel_extension_for_pytorch as ipex


print(torch.__version__)
print(ipex.__version__)
# 检查设备
print(f"XPU可用: {torch.xpu.is_available()}")
print(f"XPU设备数: {torch.xpu.device_count()}")
for i in range(torch.xpu.device_count()):
    print(torch.xpu.get_device_properties(i))
    
if torch.xpu.is_available():
    print(f"当前XPU: {torch.xpu.get_device_name()}")
