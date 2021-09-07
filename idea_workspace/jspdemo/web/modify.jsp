<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/7/22
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="mypack.Student" %>
<jsp:useBean id="login" class="mypack.DataBaseOperator" scope="session"></jsp:useBean>

<%
    String success = login.getSuccess();
    String logname = login.getLogname();
    String stuid = request.getParameter("idn");
    if(success==null){
        success = "";
    }
    if((session.isNew())){
        response.sendRedirect("login.jsp");
    }
    else if(!(success.equals("ok"))){
        response.sendRedirect("login.jsp");
    }
    else if(stuid==null){
        response.sendRedirect("login.jsp");
    }
    else{
        int id = Integer.parseInt(stuid);
        Student st = new Student();
        st = login.searchOneStudent(id);
%>
<script type="text/javascript">
    function check() {
        var id = document.getElementById("id").value;
        var age = document.getElementById("age").value;
        if(isNaN(age)||id==""||isNaN(id)){
            alert("学号不能为空,且学号和年龄必须是数字!");
            return false;
        }
        return true;
    }
</script>

<html>
<head>
    <title>修改界面</title>
</head>

<body>
    <font size="2">
        <p>修改学生信息:</p>
        <form action="update.jsp" method="post" onsubmit="return check();">
            <br>学号:<input type="text" name="id" value="<%=st.getId()%>">
            <br>姓名:<input type="text" name="name" value="<%=st.getName()%>">
            <br>年龄:<input type="text" name="age" value="<%=st.getAge()%>">
            <br>性别:<input type="text" name="gender" value="<%=st.getGender()%>">
            <br>专业:<input type="text" name="major" value="<%=st.getMajor()%>">
            <br><input type="submit" name="g" value="提交修改">

        </form>
    </font>
    <% } %>
</body>
</html>
