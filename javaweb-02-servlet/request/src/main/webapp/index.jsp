<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/8/12
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>登陆</h1>
    <div style="text-align: center">
        <form action="${pageContext.request.contextPath}/login" method="post">
            用户名:<input type="text" name="username"><br>
            密码:<input type="password" name="password"><br>
            爱好:
            <input type="checkbox" name="hobbys" value="女孩">女孩
            <input type="checkbox" name="hobbys" value="代码">代码
            <input type="checkbox" name="hobbys" value="电影">电影
            <input type="checkbox" name="hobbys" value="唱歌">唱歌
            <br>
            <input type="submit" value="提交">
        </form>
    </div>

</body>
</html>
