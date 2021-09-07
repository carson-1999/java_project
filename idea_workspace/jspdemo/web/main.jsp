<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/7/22
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="mypack.Student" %>
<%@ page import="java.rmi.StubNotFoundException" %>
<jsp:useBean id="login" class="mypack.DataBaseOperator" scope="session"></jsp:useBean>

<%
    if((session.isNew())){
        response.sendRedirect("login.jsp");
    }
    String success = login.getSuccess();
    if(success==null){
        success = "";
    }
    if(!(success.equals("ok"))){
        response.sendRedirect("login.jsp");
    }
%>


<html>
<head>
    <title>主界面</title>
</head>

<body>
    <Font size="2">
        <p>显示学生信息：</p>
        <%
            String logname = login.getLogname();
            if(logname==null){
                logname="";
            }
            Set<Student>sts = login.searchAllStudents();
            Iterator<Student>it = sts.iterator();//进行遍历
        %>
        <table frame="border" bordercolor="black" style="width: 600px;">
            <tr>
                <td style="border: 1px solid black;">学号</td>
                <td style="border: 1px solid black;">姓名</td>
                <td style="border: 1px solid black;">年龄</td>
                <td style="border: 1px solid black;">性别</td>
                <td style="border: 1px solid black;">专业</td>
                <td style="border: 1px solid black;">操作</td>
            </tr>
            <%
                //运用了Iterator的迭代器运用hasNext()来判断是否有数据
                while(it.hasNext()){
                    Student st = it.next();//有数据之后利用next()函数来遍历数据
            %>
            <tr>
                <td style="border: 1px solid black;"><%=st.getId()%></td>
                <td style="border: 1px solid black;"><%=st.getName()%></td>
                <td style="border: 1px solid black;"><%=st.getAge()%></td>
                <td style="border: 1px solid black;"><%=st.getGender()%></td>
                <td style="border: 1px solid black;"><%=st.getMajor()%></td>
                <td style="border: 1px solid black;"><a href="update.jsp?op=del&idn=<%=st.getId()%>">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
                        href="modify.jsp?op=modi&idn=<%=st.getId()%>">修改</a></td>
            </tr>
            <%
                }
            %>
        </table>
    </Font>
    <script type="text/javascript">
        function check() {
            var id = document.getElementById("uid").value;
            var age = document.getElementById("age").value;
            if(isNaN(age)||id == "" || isNaN(id)){
                alert("学号不能为空，且学号和年龄必须是数字!");
                return false;
            }
            return true;
        }

        function checkid() {
            var id = document.getElementById("id").value;
            if(isNaN(id)){
                alert("学号必须是数字!");
                return false;
            }
            return true;
        }
    </script>
    <%--该表单根据学号或姓名查询学生记录--%>
    <form action="showresult.jsp?op=select" method="post" onsubmit="return checkid();">
        <br>学号:<input type="text" name="id" value="">
        <br>姓名:<input type="text" name="name" value="">
        <input type="submit" name="sel" value="查询记录">
    </form>

    <%--该表单输入各属性的值--%>
    <%--onsubmit处理函数返回false，onclick函数返回false，都不会引起表单提交。--%>
    <form action="showresult.jsp?op=add" method="post" onsubmit="return check();">
        <br>学号:<input type="text" name="uid" value="">
        <br>姓名:<input type="text" name="name" value="">
        <br>年龄:<input type="text" name="age" value="">
        <br>性别:<input type="text" name="gender" value="">
        <br>专业:<input type="text" name="major" value="">
        <input type="submit" name="add" value="添加记录">


    </form>


</body>
</html>
