# 管理

## profile文件

### 安全性管理

即 wasphere控制台

- 默认端口
  - 管理控制端口 9060
  - 管理控制安全端口 9043
  - HTTP传输端口 9080
  - HTTPS传输端口 9443
  - 引导程序端口2809
  - SOAP连接端口 8880

### 通过控制台查看

- Server
  - WebSpere Application Server
    - 端口
      - WC_adminihost 控制台默认端口 (9060)
      - WC_default 应用程序默认访问端口(9080)
    - 详细信息
      - 配置
        - 主机
          - '*' 对所有主机进行开放
        - 端口

> 重启应用服务器生效

### app_install_root 应用程序默认部署路径

> $WAS_HOME/profiles/#{app_server}/installedApps/#{xxxNode01Cell}

### 删除profile文件

- `# manageprofiles -delete -profileName #{profileName}`
- 删除 **$WAS_HOME/profiles**下对应文件夹

# 更新应用程序

## 更改应用程序上下文

- was控制台
- 应用程序
- 应用程序类型
- WebSphere企业应用程序(点击应用)
- Web模块上下文

> 等价修改 Tomcat的 $config/server.xml 中的Host内容

## 手动更新

- 除web.xml文件以外直接替换即可
- web.xml文件
  - 替换安装目录中文件
  - 替换缓存文件
  - was8.5版本同时需要更新web_merge.xml
- 缓存文件目录
  `$WAS_HOME/profiles/#{app_server}/config/cells/#{xxxNode01Cell}/applications`
- web_merge.xml 自动排序 合并

## 使用was自带更新

### 替换, 添加或删除多个文件

- 在META-INF目录下添加属性文件:META-INF/ibm-parialapp-delete.props, 且文件必须是ASCII文件
- 文件路径相对于 META-INF/ibm-parialapp-delete.props

# 修改端口

*$WAS_HOME/profiles/#{serverName}/config/cells/#{cellName}/nodes/#{nodeName}/serverindex.xml*

中定义各端口。

# 升级JDK

# was永久使用

- 修改系统时间
- 删除$WAS_HOME/properties/was.license

# 参考

<https://edu.51cto.com/center/course/lesson/index?id=153550>

http://blog.chinaunix.net/uid-20802110-id-4121432.html



