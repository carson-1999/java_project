<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<form action="${pageContext.request.contextPath}/login" method="get">
    用户名:<input type="text" name="username"><br>
    密码:<input type="text" name="password"><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
