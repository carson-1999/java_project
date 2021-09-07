<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/8/18
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>pageContextDemo04</title>
</head>
<body>
    <%
        pageContext.forward("/index.jsp");
        //等同于 request.getRequestDispatcher("/index.jsp").forward(req,resp);
    %>

</body>
</html>
