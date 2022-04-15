<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h3 style="color:#ff0000">${errorMsg}</h3>
<form method="post" id="user" action="${pageContext.request.contextPath}/login"></form>
<h3>Welcome to Java Chat!</h3>
<table>
    <tr>
        <th>Name</th>
        <th>Submit</th>
    </tr>
    <tr>
        <td>
            <input type="text" name="name" form="user" required>
        </td>
        <td>
            <input type="submit" name="submit" form="user">
        </td>
    </tr>
</table>
</body>
</html>
