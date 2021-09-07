<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/8/18
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--使用JSTL表达式需要引入相应的taglib标签库--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>JSTL test</title>
</head>
<body>
    <h4>if测试</h4>
    <hr>
    <form action="coreif.jsp" method="get">
        <%--EL表达式获取表单中的数据 格式: ${param.参数名}--%>
            <input type="text" name="username" value="${param.username}">
            <input type="submit" value="登陆">
    </form>

    <%--    判断如果提交的用户是管理员,则登陆成功--%>
    <%--    其中test是判断条件,var是存储输出true/false的变量--%>
    <c:if test="${param.username=='admin'}" var="isAdmin">
        <%--其中value指代输出的内容--%>
        <c:out value="管理员admin欢迎您"></c:out>
    </c:if>

    <%--输出true/false的结果--%>
    <c:out value="${isAdmin}"></c:out>

</body>
</html>
