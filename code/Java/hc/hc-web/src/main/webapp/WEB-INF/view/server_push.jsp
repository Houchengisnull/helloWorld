<%@ page language = "java" contentType= "text/html; charset=UTF-8" pageEncoding= "UTF-8" %>
<! DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd" >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>服务器推送技术</title>
</head>
<body>
<h1>服务器推送技术演示</h1>

<ul>
    <li><h2><a href="${pageContext.request.contextPath}/view/server_push/interval">Interval</a></h2></li>
    <li><h2><a href="${pageContext.request.contextPath}/view/server_push/sse">SSE(server-sent-event)</a></h2></li>
    <li><h2><a href="${pageContext.request.contextPath}/view/server_push/servlet_async">Servlet Asynchronous</a></h2></li>
    <li><h2><a href="weChatPay">在线支付</a></h2></li>
</ul>
</body>
</html>