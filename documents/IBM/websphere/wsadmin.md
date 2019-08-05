[TOC]

# Login

```shell
cd $WAS_HOME/bin
./wsadmin.sh -username #{username} -password ${password} -lang #{jython|jacl}
#./wsadmin.sh -username #{username} -password ${password} -f #{script}

# 本地登录
#./wsadmin.sh -username #{username} -password ${password} -connType NONE 
```

# MBean

> - **AdminControl** **：**用于操作控制。通过 MBean 来进行通讯，包含查询在在的运行中的对象和其属性，并在这些对象上调用操作。另外，支持关于连接服务的查询，客户端跟踪的方便命令，重新连接至服务器，启动和停止服务器。
>
> - **AdminConfig** **：**管理存储在仓库中的配置信息。通过 WAS 的配置服务组件来查询和更改配置。可以使用它来查询存在的配置对象，创建配置对象，修改存在的对象和移除配置对象。在分布式的环境中，此命令仅仅能用于连接到 DM ，不能连接到 Node Agent 或管理某一 Application Server ，因为这些服务器的进程配置仅仅是保存在 DM 上的一个 Copy( 副本 ) 。
>
> - **AdminApp** **：**能更新应用的元数据，映射虚拟主机到 Web 模块，映射已安装的模块至服务器。对一个应用程序进行更改，比如为应用程序指定一个共享库，设置会话管理的配置属性。
>
> - **AdminTask** **：**用于访问面向任务的管理命令。这些命令用于访问配置命令和运行时对象管理命令。当脚本客户端运行时，可以自动发现管理命令。可用的管理命令以用于安装WAS 的版本。
>
> - **Help ：**
>
> ​       print Help.AdminControl()
>
> - **运行环境：** AdminConfig ， AdminTask ， AdminApp 对象都是处理配置功能。可以在连接或不连接至服务器的环境下运行，但 AdminControl 需要连接到服务器才能运行，因为他是通过调用正在运行的 JMX MBean 来执行的。

## AdminConfig

使用 `AdminConfig` 对象，以调用配置命令以及创建或更改 `WebSphere® Application Server` 配置的元素，例如，创建数据源。

如果您只想使用本地操作，那么您可以在服务器不运行时启动脚本客户机。要以本地方式运行，使用

 `-conntype NONE` 选项启动脚本客户机。您会接收到正在以本地方式运行的消息。如果服务器当前在运行，请不要以本地方式运行 AdminConfig 工具。以本地方式进行的配置更改将不会反映到正在运行的服务器配置中。如果您保存冲突的配置，那么会毁坏配置。

在 `Deployment Manager` 环境中，仅当脚本客户机连接到 `Deployment Manager` 时才可更新配置。

当连接到节点代理程序或受管的应用程序服务器时，您将无法更新配置，因为这些服务器进程的配置是驻留在 `Deployment Manager` 的主配置的副本。当在 `Deployment Manager` 和节点代理程序之间进行同步配置时，将在节点机器上创建这些副本。通过将脚本客户机连接到 `Deployment Manager`，对服务器进程进行配置更改。出于该原因，要更改配置，不要在节点机器上用本地方式运行脚本客户机。它不是支持的配置。

### getid

使用 **getid** 命令来返回对象的配置标识。

```
print AdminConfig.getid('/Cell:testcell/Node:testNode/JDBCProvider:Db2JdbcDriver/')
```

### getObjectName

使用 **getObjectName** 命令来返回正在运行的相应 MBean 的对象名的字符串版本。如果不存在相应的运行 MBean，此方法返回空字符串。

```python
server = AdminConfig.getid('/Node:mynode/Server:server1/')
print AdminConfig.getObjectName(server)
```

# Resouces

## JDBC

### JDBC Providers

#### General Properties

- Scope 

  设置`JDBC Driver`的作用域。

  例：`cells:dmgrCell`

- Database type

  通过页面创建`JDBC Provider`时必选项

- Provider type

  在页面上为根据`Database type`调整的选择框。若`Database type`为`User-defined`时无该项。需要手动输入`Implementation class name`

- Name

  名称

- Description

  描述

- Class path

  服务器上`JDBC 驱动包`的绝对路径。可设置多个驱动包。

  ``` shell
  /usr/lib/db2jcc4.jar
  /usr/lib/db2jcc4_license_cisuz.jar
  /usr/lib/db2jcc4_license_cu.jar
  ```

- Native library path

- Implementation class name

  实现类，例：`com.ibm.db2.jcc.DB2ConnectionPoolDataSource`



#### DataSource

- Scope

- Data source name

  控制台显示名称，必填项。

- JNDI name

  `web application`访问该访问数据源时所用名称，必填项。

- Database name 数据库名称

- Server name 服务器地址

- Port number 端口号

##### J2C authentication data

权限配置

- User Id
- password

https://stackoverflow.com/questions/19096482/binding-container-managed-authentication-alias-with-datasource-using-jython-scri

##### Security settings

- Component-managed authentication alias

  选择登录user/password

- Mapping-configuration alias

  - Container-managed authentication alias

  未知

# jython脚本

``` python
cd $WAS_HOME/bin
# 所有配置均可在wsadmin控制台完成 但前提是node已啟動
# websphere支持兩種語法Jacl與Jython
./wsadmin.sh -user #{username} -password #{password} -lang #{lang}

# create jdbc driver provider
print '創建數據源驅動'
scope = AdminConfig.getid('/Cell:dmgrCell/')
classpath = ['classpath', '/usr/epayment/lib/db2jcc4.jar /usr/epayment/lib/db2jcc4_license_cisuz.jar /usr/epayment/lib/db2jcc4_license_cu.jar']
name = ['name', 'DB2 universal JDBC Driver Provider']
implementation = ['implementationClassName', 'com.ibm.db2.jcc.DB2ConnectionPoolDataSource']
providerType = ['providerType', 'DB2 Universal JDBC Driver Provider']
providerAttributes = [classpath, name, implementation, providerType]
AdminConfig.create('JDBCProvider', scope , providerAttributes)
AdminConfig.save()
print AdminConfig.list('JDBCProvider','*')
# print AdminConfig.list('JDBCProvider','DB2*')
# providers = AdminConfig.list('JDBCProvider','DB2*')
# AdminConfig.remove(providers)

# create J2C Authentation
security = AdminConfig.getid('/Cell:dmgrCell/Security:/')
alias = ['alias', 'user']
userid = ['userId', 'root']
password = ['password', 'root']
securityAttrs = [alias, userid, password]
AdminConfig.create('JAASAuthData', security, securityAttrs)
AdminConfig.save()
# bind `container-managed` 
#mapping = AdminConfig.showAttribute(dataSource, 'mapping')
#AdminConfig.modify(mapping, [['mappingConfigAlias',alias],['authDataAlias',alias]])

# create datasource
# jdbcProvider:DB2 Universal JDBC Driver Provider
# cells:dmgrCell:nodes:LXEPAYAPP1Node:servers:LXEPAYAPP1 
provider = AdminConfig.getid('/Cell:dmgrCell/JDBCProvider:DB2 universal JDBC Driver Provider/')
alias = ['authDataAlias', 'user']
datasourceHelper = ['datasourceHelperClassname', 'com.ibm.websphere.rsadapter.DB2UniversalDataStoreHelper']
jndiName = ['jndiName', 'jdbc/datasource']
name = ['name', 'database']
dataSourceAttributes = [alias, datasourceHelper, jndiName, name]
AdminConfig.create('DataSource', provider, dataSourceAttributes)
AdminConfig.save()

# create datasource J2EEResourceProperty
dataSource = AdminConfig.getid('/Cell:dmgrCell/JDBCProvider:DB2 universal JDBC Driver Provider/DataSource:database/')
propertySet = AdminConfig.create('J2EEResourcePropertySet', dataSource, [])
databaseName = [['name', 'databaseName'], ['value', 'database']]
driverType = [['name', 'driverType'], ['value', '4']]
serverName = [['name', 'serverName'], ['value', 'LXAPP01']]
portNumber = [['name', 'portNumber'], ['value', '50001']]
AdminConfig.create('J2EEResourceProperty', propertySet, databaseName)
AdminConfig.create('J2EEResourceProperty', propertySet, driverType)
AdminConfig.create('J2EEResourceProperty', propertySet, serverName)
AdminConfig.create('J2EEResourceProperty', propertySet, portNumber)
AdminConfig.save()

# create connection pool
AdminConfig.create('ConnectionPool',dataSource, [['maxConnections', '50']])
AdminConfig.save()

# 測試連接
AdminControl.testConnection(dataSource)
```



# 参考

http://www.blogjava.net/fastzch/archive/2008/09/18/229797.html

- AminConfig

  https://www.ibm.com/support/knowledgecenter/zh/SSAW57_8.5.5/com.ibm.websphere.nd.multiplatform.doc/ae/rxml_adminconfig1.html