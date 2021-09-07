<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/8/18
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--使用JSTL表达式需要引入相应的taglib标签库--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>jsptag</title>
</head>
<body>
    <h1>jsptag1</h1>
    <%--携带两个参数--%>
    <jsp:forward page="/jsptag2.jsp">
        <jsp:param name="name" value="carson"></jsp:param>
        <jsp:param name="age" value="21"></jsp:param>
    </jsp:forward>

</body>
</html>
