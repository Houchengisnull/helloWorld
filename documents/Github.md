[toc]

# 解决Github网页上图片显示失败的问题

- 原因

  `ERR_CERT_COMMON_NAME_INVALID`，可理解为一个错误的域名访问了某个节点的`https`资源，造成原因如下：

  - `DNS`污染
  - `hosts`配置错误
  - 官方更新`DNS`，但是本地`DNS`缓存没有被更新

- 解决方案

  先通过https://www.ipaddress.com/找到存储图片的正确`IP地址`——`avatars2.githubusercontent.com`

  将以下信息复制进`hosts`中，结果如下：

  ``` text
  # GitHub Start 
  # 20200328 更新
  140.82.113.3      github.com
  140.82.114.20     gist.github.com
  
  151.101.184.133    assets-cdn.github.com
  151.101.184.133    raw.githubusercontent.com
  151.101.184.133    gist.githubusercontent.com
  151.101.184.133    cloud.githubusercontent.com
  151.101.184.133    camo.githubusercontent.com
  # images ip
  151.101.184.133    avatars0.githubusercontent.com
  199.232.68.133     avatars0.githubusercontent.com
  199.232.28.133     avatars1.githubusercontent.com
  151.101.184.133    avatars1.githubusercontent.com
  151.101.184.133    avatars2.githubusercontent.com
  199.232.28.133     avatars2.githubusercontent.com
  151.101.184.133    avatars3.githubusercontent.com
  199.232.68.133     avatars3.githubusercontent.com
  151.101.184.133    avatars4.githubusercontent.com
  199.232.68.133     avatars4.githubusercontent.com
  151.101.184.133    avatars5.githubusercontent.com
  199.232.68.133     avatars5.githubusercontent.com
  151.101.184.133    avatars6.githubusercontent.com
  199.232.68.133     avatars6.githubusercontent.com
  151.101.184.133    avatars7.githubusercontent.com
  199.232.68.133     avatars7.githubusercontent.com
  151.101.184.133    avatars8.githubusercontent.com
  199.232.68.133     avatars8.githubusercontent.com
  
  # GitHub End
  ```

- 属性`DNS`缓存

  ``` bat
  ipconfig /display # 显示DNS缓存
  ipconfig /flushdns # 刷新DNS缓存
  ipconfig /renew # 重新从DNS服务器获取IP
  ```

- 参考

  https://blog.csdn.net/qq_38232598/article/details/91346392

