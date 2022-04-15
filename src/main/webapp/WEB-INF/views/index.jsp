<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Welcome, ${user_name}! Choose your chat...</h1>
<table>
    <tr>
        <th>Chats</th>
        <th>Enter</th>
    </tr>
    <c:forEach var="chat" items="${chats}">
        <tr>
            <td>
                <c:out value="${chat.type}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/chats/${chat.type}">enter</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
