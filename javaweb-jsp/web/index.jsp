<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/8/16
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <%--  JSP表达式
    作用:用来将程序的输出,输出到客户端
    形式:<%= 变量或表达式%>
    --%>
    <%= new java.util.Date()%>

    <hr>
    <%--  JSP脚本片段
    作用:书写java脚本片段
    形式:<% 脚本片段 %>
    --%>
    <%
      int sum = 0;
      for (int i = 0; i < 100 ; i++) {
        sum = sum+i;
      }
      out.println("<h1>Sum="+sum+"</h1>");
    %>
    <%--脚本片段的再实现--%>
    <%
      int x = 10;
      out.println(x);
    %>
    <p>这是一个JSP文档!</p>
    <%
      int y = 2;
      out.println(y);
    %>
    <%--在Jsp代码中间嵌入HTML元素(大括号分出来)--%>
    <%
      for (int i = 0; i < 4; i++) {

    %>
      <h2>hello,carson！<%=i%></h2>
    <%
      }
    %>

    <hr>
    <%--  JSP声明
    特点:书写产生的java代码不会被放在jspService方法中,而是直接放在类中(jspService方法外)
    格式: <%! 脚本片段 %>
    --%>
    <%!
      static {
        System.out.println("Loading Servlet!");
      }
      private int globalVar = 0;
      public void carson(){
        System.out.println("进入了方法carson!");
      }
    %>


  </body>
</html>
