[toc]

`Postman`是后端工程师、测试工程师的好帮手。

- **参考**
- [Learning Center](https://learning.postman.com/docs/getting-started/introduction/)
- [Postman官方文档翻译 - 北京临渊的博客](https://www.cnblogs.com/superhin/p/10983986.html)

# 控制台

- **什么时候需要控制台**

  当需要编写自动化测试脚本的时候，清晰的日志输出能够更好的帮助我们构建可以、复杂的脚本或者定位脚本中的问题。

  控制台会输出脚本中`console.log()`的内容以及请求的`URL`。

  > 例如：在调用某接口时，该接口重定向后`Request Method`从`POST`变为`GET`，导致鉴权失败。就可以从控制台中观察到该现象，从而更精确地定位问题。

- 打开控制台

  你会发现有个`Console`在左下角或者在`View > Show Postman Console`中打开。

  > 藏得确实挺深的。

# 脚本

> Postman contains a powerful runtime based on Node.js that allows you to add dynamic behavior to requests and collections.

- **请求执行流程**

  ``` mermaid
  graph LR
  	pre[pre-request script]
  	request
  	response
  	test[test script]
  	pre --> request --> response --> test
  ```

  而我们写的测试脚本就是`pre-request script`与`test script`：

  - **pre-request script**	发送请求前执行的脚本
  - **test script**	收到响应后执行的脚本

## Postman沙箱

`Postman脚本`是通过`Postman sandbox`实现的：`Postman sandbox`是一个`Javascript`运行环境。

## 变量

### 作用域

- 全局
- 环境
- 本地
- 数据

## 内置对象

### pm

`pm`即`Postman`对象。

- **sendRequest**

  用于在沙盒中发送请求，比如需要在调用查看接口前先调用登录接口，或者在调用A接口后返回重定向，我们将`Response Header`中的`Location`替换成新的`url`。

