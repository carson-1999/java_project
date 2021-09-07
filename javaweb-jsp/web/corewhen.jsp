<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/8/18
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--使用JSTL标签库需要引入相应的taglib标签库--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>corewhen</title>
</head>
<body>
    <%--定义一个变量,值为85--%>
    <c:set var="score" value="85"></c:set>

    <c:choose>
        <c:when test="${score>=90}">
            你的成绩为优秀
        </c:when>
        <c:when test="${score>=80}">
            你的成绩为一般
        </c:when>
        <c:when test="${score>=70}">
            你的成绩为普通
        </c:when>
        <c:when test="${score>=60}">
            你的成绩刚好及格
        </c:when>
        <c:when test="${score<60}">
            你的成绩不及格
        </c:when>
    </c:choose>

</body>
</html>
