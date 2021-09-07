<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/8/18
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>pageContextDemo03</title>
</head>
<body>
    <%
        pageContext.setAttribute("hello","hello",PageContext.SESSION_SCOPE);
        //另一个重载方法,可以设置属性的作用域,这里等同于 session.setAttribute();
    %>

</body>
</html>
