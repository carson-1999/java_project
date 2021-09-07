<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.util.*,java.io.*" %>


<html>
    <head>
        <title>demo</title>
    </head>

    <body>
        <h1>JSP 页面重定向测试</h1>
        <%
            //页面重定向
            String site = new String("http://www.baidu.com");
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location",site);

        %>
    </body>
</html>