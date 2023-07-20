<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hou Cheng Web</title>
</head>
<body>
	<div>
		<!-- ajax结合setInterval 定时更新时间 -->
		<div>
			<div>服务器当前时间为:</div><div style="color:#F00"><p id="serverTime"></p></div>
		</div>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
        var source = new EventSource("${pageContext.request.contextPath}/serverPush/getServerTimeBySSE");

        if (!!window.EventSource) {
            source.onmessage = function (e) {
                var now = e.data;
                console.log(now);
                $('#serverTime').html(now);
            };

            source.onopen = function (e) {
                console.log("Connecting server!");
            };

            source.onerror = function (e) {
                console.error(e);
                source.close();
            };

            /*
            	可以通过添加事件监听器，监听除'message'以外的自定义事件
             */
            // source.addEventListener()
		} else {
            $("#hint").html("您的浏览器不支持SSE！");
		}
	</script>
</body>
</html>