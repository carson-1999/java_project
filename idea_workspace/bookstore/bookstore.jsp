<%@ page contentType="text/html;charset=GB2312" %>
<%@ include file="common.jsp" %> <!-- 引入文件-->

<%--注意:JSP的本质就是一个Servlet，JSP的运行之前会先被Tomcat服务器翻译为.java文件，然后在将.java文本编译--%>
<%--    为.class文件，而我们在访问jsp时，处理请求的就是那个翻译后的类。--%>

<%--注意: 各个java文件编码后的字节码文件,即class文件在tomcat中的classes文件夹中的放置路径,需要和java文件中package的位置一样--%>

<html>
    <head>
        <title>
            BookStore
        </title>
        <%@ include file="banner.jsp" %>  <!-- 引入文件-->
    </head>

    <body>
        <!-- request.getContextPath()表示当前目录-->
        <p><b><a href="<%=request.getContextPath()%>/catalog.jsp">查看所有书目</a></b></p>
        <center>
        <form action="bookdetails.jsp" method="post">
            <h3>请输入查询信息:</h3>
            <b>书的编号:</b>
            <input type="text" size="20" name="bookId" value=""><br><br>
            <input type="submit" value="查询">
        </form>
        </center>
    </body>
</html>