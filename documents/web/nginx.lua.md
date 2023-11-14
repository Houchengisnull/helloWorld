[toc]

# Openresty

- **目标**

  给前端人员使用

OpenResty是一个全功能的Web应用服务器。它打包了标准的Nginx核心，常用的第三方模块以及大多数依赖项。OpenResty致力于将你的服务器端应用完全运行于Nginx服务器中，充分利用Nginx的事件模型来进行非阻塞I/O通信。

不仅仅是和HTTP客户端间的网络是非阻塞的，与MySQL、PostgreSQL、Memcached以及Redis等众多中间件的网络通信也是非阻塞的。

## 安装

``` shell
#wget

yum install readline-devel pcre-devel openssl-devel perl
wget https://openresty.org/download/openresty-1.15.8.1.tar.gz
tar -zxvf openresty-1.15.8.1.tar.gz

# --with-components 激活组件
# --without-components 
./configure --without-http_redis2_module --with-http_iconv_module
make && make install

# 设置环境变量 
vi /etc/profile

export PATH=/usr/local/mysql/bin:$PATH:/usr/local/openresty/nginx/sbin/

source /etc/profile
```

# Lua

- **Hello World**

  这段脚本会在浏览器访问/hello时，在页面显示"Hello world".

  ``` shell
  location /hello {
      
      content_by_lua 'ngx.say("Hello World")';
      
  }
  ```

## Lua嵌入Nginx的时机

| 阶段                  | 作用                               |
| --------------------- | ---------------------------------- |
| set_by_lua*           | 设置nginx变量，实现复杂的赋值逻辑  |
| rewrite_by_lua*       | 实现转发、重定向功能               |
| access_by_lua*        | IP准入、接口访问权限等情况集中处理 |
| content_by_lua*       | 接收请求处理并输出响应             |
| header_filter_by_lua* | 设置header和cookie                 |
| body_filter_by_lua*   | 对响应数据进行过滤，如截断/替换等  |

## 常用API

| 方法名                     | 作用                                          |
| -------------------------- | --------------------------------------------- |
| ngx.arg                    | 指令参数，如跟在content_by_lua_file后面的参数 |
| ngx.var                    | request变量                                   |
| ngx.ctx                    | 请求lua的上下文                               |
| ngx.header                 | 响应头                                        |
| ngx.status                 | 响应码                                        |
| ngx.log                    | 输出到error.log                               |
| ngx.send_headers           | 发送响应头                                    |
| ngx.headers_sent           | 响应头是否已发送                              |
| ngx.resp.get_headers       | 获取响应头                                    |
| ngx.is_subrequest          | 当前请求是否子请求                            |
| ngx.location.capture       | 发布一个子请求                                |
| ngx.location.capture_multi | 发布多个子请求                                |
| ngx.print                  | 输出响应                                      |
| ngx.say                    | 输出响应，自动添加"\n"                        |
| ngx.flush                  | 刷新响应                                      |
| ngx.exit                   | 结束请求                                      |

- **执行lua脚本**

  ``` lua
  location .args_read {
      content_by_lua_file /etc/nginx/lua/lua_args
  }
  ```

- **读取get参数**

  ``` shell
  # 读取请求参数a和b
  location .args {
  	content_by_lua_block {
  		ngx.say(ngx.var.arg_a)
  		ngx.say(ngx.var.arg_b)
  	}
  }
  ```

- **读取全量参数**

  ``` shell 
  local arg = ngx.req.get_uri_args()
  for k,v in pairs(args) do
      ngx.say("[GET ]", k, " :", v)
  end
  ```

- **读取header信息**

  ``` shell
  local headers = ngx.req.get_headers()
  ngx.say("Host: ", headers.HOST)
  ngx.say("Host: ", headers.["HOST"])
  
  for k,v in pairs(headers) do 
  	if type(v) == "table" then
  		ngx.say(k, ":", table.concat(v, ","))
  	else
  		ngx.say(k, ":", v)
  	end
  end
  ```

- **给lua脚本传入参数**

  使用端传参:

  ``` shell 
  locaiton /setfile {
  	set_by_lua_file $val "/etc/nginx/set.lua" $arg_a $arg_b;
  	echo $val;
  }
  ```

  脚本借助ngx.arg取参:

  ``` shell
  local a=tonumber(ngx.arg[1])
  local b=tonumber(ngx.arg[2])
  return a+b
  ```

- **权限校验**

  一般校验动作，指定在access阶段执行脚本

  ``` shell
  location /access {
  	# 在access阶段执行
  	access_by_lua_file "/etc/nginx/lua/access.lua"
  	echo "welcome $arg_name!";
  }
  ```

  脚本处理

  ``` shell
  if ngx.var.arg_passwd == "123456"
  then 
  	return
  else
  	#检验不通过
  	ngx.exit(ngx.HTTP_FORBIDDEN)
  end
  ```

- **内容过滤**

  ``` shell
  location /filter {
  	echo 'hello Peter';
  	echo 'you are welcome';
  	body_filter_by_lua_file "/etc/nginx/lua/filter.lua"
  }
  ```

  脚本中的处理

  ``` lua
  -- ngx。arg[1]时输出块内容
  local chunk = ngx.arg[1]
  if string.match(chunk, "hello") then
  	ngx.arg[2] = true -- 设置为true, 表示输出结束 eof
      return
  end
  ```

## 第三方模块

### Lua-resty-redis

连接redis

### Lua-resty-mysql

连接mysql数据库

