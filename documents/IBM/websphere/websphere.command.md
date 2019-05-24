[toc]

# 常用命令命令

## profile文件

### 查看

- 查看已存在profile
  `# ./manageprofiles.sh -listProfiles`

### 删除

#### method1

- 于$WAS_HOME下properties文件夹中找到**propertiesRegister.xml**, 删除配置即可
- 删除$WAS_HOME/properties/fsdn/中指定脚本
- 删除$WAS_HOME/profiles/概要文件实际存储目录

#### method2

- 删除profile文件
  `# ./manageprofiles.sh -delete -profileName profile name`
- 刷新profile文件注册表
  `# ./manageprofiles.sh -validateAndUpdateRegisty`

> 程序开发里面的profile 是什么意思
> <https://zhidao.baidu.com/question/88935405.html>

> 删除profile文件例子： /opt/IBM/WebSphere/AppServer/bin/manageprofiles.sh -delete -profileName AppSrv01 /opt/IBM/WebSphere/AppServer/bin/manageprofiles.sh -validateAndUpdateRegistry

需要手动删除profiles下#{app_server}目录

### create 管理 profile

```
# ./manageprofiles.sh -create
-profileName dmgr
-serverType DEPLOYMENT_MANAGER
-enableAdminSecurity true
-adminUserName pgadm
-adminPassword password
-profilePath $WAS_HOME/profiles/dmgr
-templatePath $WAS_HOME/profileTemplates/management
-cellName dmgrCell
-nodeName dmgrNode
-serverName dmgr
-hostName AppSrv01
```

- hostName 主机不存在时将无法启动管理结点
- hostName 对应一台机器

> Linux 查看下/ect/host 文件

### create profile

- linux 命令行

```
# ./manageprofile.sh -create 
-tempatePath $WAS_HOME/profileTemplates/managed -profileName AppSrv01
-profilePath $WAS_HOME/ArrSrv01
-hostName AppSrv01Host
-nodeName AppSrv01Node
-dmgrHost AppSrv01Host
-dmgrPort 8879
-dmgrAdminUserName pgadm
-dmgrAdminPassword password
-isDefault
```

- profileName profile name of this node,cannot change after created
- dmgrProt SOAP connector port of deploymentmanager
- $WAS_HOME/profile/#{AppSrv01}/logs/AboutThisProfile.txt可查看相关信息

## 查看集群状态

```
# ./serverStatus -all
```

### 参数

- -all 全部

## 查看服务器状态

进入$WAS_HOME/profiles/#{主机/概要/集群}/bin/serverStatus.sh

# wasphere启动与关闭

## 启动was

启动存在顺序要求 manager, node, server

- 启动manager
  路径 :$WAS_HOME/bin
  命令 :sh [startManger.sh](http://startManger.sh)
- 启动node和server
  路径 :$WAS_HOME/profiles/#{profile_directory}/bin/startNode.sh
  命令 : ./startNode.sh 与 ./startServer.sh #{serverName}

## 关闭was

获得端口号
`# ps -ef | gerp wasphere`
杀死进程
`kill #{port}`

# 参考

<https://www.cnblogs.com/lczean/p/7357961.html>

# 吐槽

这样子慢慢百度实在太难解决问题了, 还是耐心看官网吧。 然后官网什么都找不到艹。