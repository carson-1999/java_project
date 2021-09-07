<%@ page import="java.io.*" %>
<%@ page contentType="text/html; charset=GB2312" %>
<%--设置页面可以作为errorPage,故设置相应属性为true--%>
<%@ page isErrorPage="true"  %>

<html>
    <head>
        <title>Error Page</title>
    </head>

<body>
    <p>
        服务器端发生错误:<%= exception.getMessage()  %>
    </p>
    <p>
        错误原因为:<% exception.printStackTrace(new PrintWriter(out)); %>
    </p>

</body>
</html>
