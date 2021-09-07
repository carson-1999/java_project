<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/8/20
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>登陆</h1>
    <form action="/servlet/login" method="post">
        <input type="text" name="username">
        <input type="submit" value="登陆">
    </form>

</body>
</html>
