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
        serverTimeLoop();

		function serverTimeLoop() {
			$.get("${pageContext.request.contextPath}/serverPush/getServerTimeByAsync", function(data){
			    console.info(data);
				$('#serverTime').html(data);
			   serverTimeLoop();
			}, false);
		}
	</script>
</body>
</html>