<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<html>
    <head>
        <title>测试</title>
    </head>

    <body>
        <h2>JSP 使用javaBean 实例</h2>
        <jsp:useBean id="test" class="mypack.TestBean" />
        <jsp:setProperty name="test" property="message" value="修改后的..."></jsp:setProperty>
        <p>以下为输出信息...</p>
        <jsp:getProperty name="test" property="message"/>
        

    </body>
</html>