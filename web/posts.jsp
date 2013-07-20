<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<title>Guestbook</title>
		<link rel="stylesheet" type="text/css" href="style.css"/>

		<script type="text/javascript">
			function goToBottom() {
				location.href = "#bottom";
			}
		</script>

	</head>
	<body onload="goToBottom();">
		<div style="width: 50%;">
			<c:forEach items="${list}" var="record">
				<div class="parent">
					<div>
						<span class="user">
							${record.userName}
						</span>
						<span class="date">
							${record.postDateString}
						</span>
					</div>
					<div class="text">
						${record.message}
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="message-form">
			<div class="parent">
				<form name="test" method="post" action="/guestbook" style="margin-bottom: 0px">

					<b>Your name:</b>
					<br/>
					<input name="userName" type="text" size="40"/>
					<br/>

					<b>Message:</b>
					<br/>
					<textarea name="message" style="resize: none; width: 100%; height: 200px;"></textarea>
					<br/>

					<input type="submit" value="Post"/>

				</form>
			</div>
		</div>
		<a name="bottom"></a>
	</body>
</html>
