<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--使用JSTL标签库需要引入相应的taglib标签库--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>coreforeach</title>
</head>
<body>
    <%
        ArrayList<String> people = new ArrayList<String>();
        people.add(0, "张三");
        people.add(1, "李四");
        people.add(2, "王五");
        people.add(3, "赵六");
        people.add(4, "田七");
        people.add(5, "carson");
        //利用request存储
        request.setAttribute("list",people);
    %>

    <%--var,每一次遍历出来的变量
    items,要遍历的对象--%>
    <c:forEach var="people" items="${list}">
        <c:out value="${people}" /><br>
    </c:forEach>
    <hr>

    <%--设置了 起始,终止,步长的遍历--%>
    <c:forEach var="people" items="${list}" begin="1" end="5" step="2">
        <c:out value="${people}"></c:out><br>
    </c:forEach>
    <hr>


</body>
</html>
