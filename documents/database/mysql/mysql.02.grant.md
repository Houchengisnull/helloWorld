[toc]

[^grant]: 允许
[^privileges]: 特权

# GRANT

``` shell
########### 授权 ###########
# 授予root在任意host上的所有权限
# 默认情况下仅有localhost的所有权限, 故首先在localhost上执行该语句
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION;
FLUSH PRIVILEGES;
# 创建dev用户192.168.0.%任意客户端上对数据库mall上的所有表的select权限
GRANT SELECT ON mall.* TO 'dev'@'192.168.0.%' IDENTIFIED BY 'password' WITH GRANT OPTION;
FLUSH PRIVILEGES;

########### 查看权限 ###########
# 查看当前用户标识的权限
SHOW GRANTS;
# 查看dev用户在192.168.192.%任意客户端上的权限
SHOW GRANTS FOR 'dev'@'192.168.0.%';
```

# 用户标识

用户名@IP。

需要注意的是: `'dev'@'192.168.192.%'`、 `'dev'@'192.168.192.1'`、 `'dev'@'localhost'`、 `'dev'@'127.0.0.1'`是四种用户标识。

# 用户权限涉及表

| 表         | 涉及权限       |
| ---------- | -------------- |
| user       | 用户标识       |
| db         | 对数据库的权限 |
| table_priv | 对表的权限     |
| column     | 对某一列的权限 |

``` mysql 
use mysql;

select * from user;
select * from db;
select * from tables_priv;
select * from  columns_priv;

########### 授予对mall.account的id、name的select权限 ###########
grant select(id,name) on mall.account to 'dev'@'192.168.192.%';
########### 撤销对所有库所有表的select权限 ###########
#注意权限是无法删除的, 仅供修改
revoke select on *.* from 'dev'@'192.168.192.%';
```

# 角色

涉及表`mysql.proxies_priv`

``` mysql
SHOW VARIABLES LIKE '%proxy%';
set GLOBAL check_proxy_users = 1;
set GLOBAL mysql_native_password_proxy_users = 1;

########## 创建角色 5.7后支持 ##########
# 默认情况下ip为%
create user 'dev_role';

create user 'deer';
create user 'enjoy';
grant proxy ON 'dev_role' to 'deer';
grant proxy ON 'dev_role' to 'enjoy';

grant select on mall.account to 'dev_role'@'%';

########## 授予root proxy权限 ##########
# 需要在本机上执行
# GRANT PROXY ON ''@'' TO 'root'@'%' WITH GRANT OPTION;
# SHOW GRANTS FOR 'root'@'%';
```

