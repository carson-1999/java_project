<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/8/18
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>javabean</title>
</head>
<body>
    <jsp:useBean id="people" class="com.carson.pojo.People" scope="page"></jsp:useBean>

    <jsp:setProperty name="people" property="id" value="1"></jsp:setProperty>
    <jsp:setProperty name="people" property="age" value="21"></jsp:setProperty>
    <jsp:setProperty name="people" property="address" value="SHENZHEN"></jsp:setProperty>
    <jsp:setProperty name="people" property="name" value="Carson张"></jsp:setProperty>

    id:<jsp:getProperty name="people" property="id"/>
    姓名:<jsp:getProperty name="people" property="name"/>
    年龄:<jsp:getProperty name="people" property="age"/>
    地址:<jsp:getProperty name="people" property="address"/>

</body>
</html>
