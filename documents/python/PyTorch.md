[toc]

# PyTorch

## Overview

### *PU是什么

- **CPU**
- **XPU**

## Install

- **CPU版本**

  ``` shell
  pip install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cpu
  ```

- **Intel显卡**

  ``` shell
  pip install torch==2.8.0 torchvision==0.23.0 torchaudio==2.8.0 --index-url https://download.pytorch.org/whl/xpu
  pip install intel-extension-for-pytorch==2.8.10+xpu --extra-index-url https://pytorch-extension.intel.com/release-whl/stable/xpu/cn/
  ```

[^XPU]: 扩展处理器

