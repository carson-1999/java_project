<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/6/30
  Time: 13:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<html>
  <head>
    <title>JSP life test!</title>
  </head>

  <body>
    <%!
      private int initVar = 0;
      private int serviceVar = 0;
      private int destroyVar = 0;
    %>

    <%!
      public void jspInit(){
        initVar++;
        System.out.print("jspInit():JSP被初始化了"+initVar+"次");
      }
      public void jspDestroy(){
        destroyVar++;
        System.out.println("jspDestroy():JSP被销毁了"+destroyVar+"次");
      }
    %>

    <%
      serviceVar++;
      String content1 = "初始化次数:"+initVar;
      String content2 = "响应客户请求次数:"+serviceVar;
      String content3 = "被销毁的次数:"+destroyVar;

    %>

    <h2>Carson JSP 测试实例</h2>
    <p><%=content1%></p>
    <p><%=content2%></p>
    <p><%=content3%></p>
    <p>今天的日期是:<%=(new java.util.Date()).toLocaleString()%></p>

  </body>
</html>
