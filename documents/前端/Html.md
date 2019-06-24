[TOC]

### **后退按钮访问浏览器缓存**

https://blog.csdn.net/yangshijin1988/article/details/44418587

当点击后退按钮时，默认情况下浏览器不是从Web服务器上重新获取页面，而是从浏览器缓存中载入页面。

 

HTTP头信息“**Expires**”和“**Cache-Control**”为应用程序服务器提供了一个控制浏览器和代理服务器上缓存的机制。

HTTP头信息**Expires**告诉代理服务器它的缓存页面何时将过期。 
HTTP1.1规范中新定义的头信息**Cache-Contro**l可以通知浏览器不缓存任何页面。 

 

如下是使用Cache-Control的基本方法： 
	1) no-cache:强制缓存从服务器上获取新的页面 
	2) no-store: 在任何环境下缓存不保存任何页面

 

例：

对于HTML网页，加入： 

<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
<meta HTTP-EQUIV="expires" CONTENT="0"> 


对于JSP页面，加入：
``` java
<% 
response.setHeader("Cache-Control","no-store"); 
response.setHeader("Pragrma","no-cache"); 
response.setDateHeader("Expires",0); 
%> 
```

- 20190624

  | 名称 | 描述 |
  | ---- | ---- |
  | 系统 | `Windows 7` |
  | Web容器 | `Tomcat 8.5` |
  | 浏览器 | `IE` |
  | 前端 | `JSP` |

在`JSP`中，仅有通过以下方式设置`response`属性后，才能强制浏览器每次从服务器读取页面。

*通过`HTML`方式设置不生效。*

``` java
<% 
response.setHeader("Cache-Control","no-store"); 
response.setHeader("Pragrma","no-cache"); 
response.setDateHeader("Expires",0); 
%> 
```

