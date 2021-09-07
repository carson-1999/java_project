<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/7/22
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<jsp:useBean id="login" class="mypack.DataBaseOperator" scope="session"></jsp:useBean>

<html>
  <head>
    <title>Login demo</title>
  </head>

  <body>
  <Font size="2">
    <%--??--%>
    <% String string = response.encodeURL("login.jsp"); %>
    <%--    登陆界面表单--%>
    <p>输入用户名和密码:</p>`
    <form action="<%=string%>" method="post">
      <br>登陆名:<input type="text" name="logname">
      <br>输入密码:<input type="password" name="password">
      <br><input type="submit" name="g" value="提交">
    </form>
  </Font>>

<%--  提交信息后,验证信息是否正确--%>
  <%
    String message = "",logname="",password="";
    if(!session.isNew()){
      logname = request.getParameter("logname");
      logname = login.codeString(logname);
      if(logname==null){
        logname = "";
      }
      password = request.getParameter("password");
      password = login.codeString(password);
      if(password == null){
        password = "";
      }
    }
  %>
  <%
    if(!(logname.equals(""))){
  %>
  <%--name = "JavaBean实例名" property = "JavaBean属性名" value="属性值"--%>
  <jsp:setProperty property="logname" name="login" value="<%=logname%>"/>
  <jsp:setProperty property="password" name="login" value="<%=password%>"/>
  <%
//    获取返回的验证信息
    message = login.getMessage();
    if(message==null){
      message = "";
    }
    }
    if(!(session.isNew())){
      if(message.equals("ok")){
        //??
        String str = response.encodeURL("main.jsp");
        response.sendRedirect(str);
      }
      else{
        out.print(message);
      }
    }
  %>
  </body>
</html>
