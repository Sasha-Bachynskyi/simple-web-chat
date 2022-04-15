<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main chat</title>
</head>
<body>
<h2>Welcome to MAIN chat</h2>
<h3>Users in chat:</h3>
<c:forEach var="user" items="${users_in_chat}">
    <c:out value="${user.name}"/> <b>  |  </b>
</c:forEach>
<form method="post" id="main" action="${pageContext.request.contextPath}/chats/main"></form>
<p>Messages in chat:</p>
<c:forEach var="message" items= "${messages}">
    <c:out value="${message.id}"/> <b>  |  </b> <c:out value="${message.user.name}"/> <b>   :   </b> <c:out value="${message.content}"/> <br/>
</c:forEach>
<p>Text the message:</p>
<input type="text" name="content" form="main" required> <input type="submit" name="send" form="main"> <a href="${pageContext.request.contextPath}/chats/main">Refresh page</a>
</body>
</html>
