<%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/7/22
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="mypack.Student" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.swing.JOptionPane" %>
<jsp:useBean id="login" class="mypack.DataBaseOperator" scope="session"></jsp:useBean>

<%
    String success = login.getSuccess();
    if((session.isNew())){
        response.sendRedirect("login.jsp");
    }
    if(success==null){
        success = "";
    }
    if(!(success.equals("ok"))){
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <title>学生信息显示</title>
</head>

<body>
    <font size="2">
        <%
            String op = request.getParameter("op");
            String logname = login.getLogname();
            if(logname==null||op==null){
                logname="";
                response.sendRedirect("login.jsp");
            }
            else{
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                name = login.codeString(name);
                Set<Student> sts = null;
                if(op.equals("add")){
                    //年龄显示不是中文,不需要调用codeString防止中文乱码
                    String age = request.getParameter("age");
                    String gender = request.getParameter("gender");
                    gender = login.codeString(gender);
                    String major = request.getParameter("major");
                    major = login.codeString(major);
                    int idnew = Integer.parseInt(request.getParameter("uid"));
                    //调用DataBaseOperator类中的insert方法插入一条学生记录
                    login.insert(idnew,name,age,gender,major);
                    //调用DataBaseOperator类中的searchAllStudents方法获取所有的学生记录
                    sts = login.searchAllStudents();
                }
                else{
                    //调用DataBaseOperator类中的searchStudents方法获取学生记录
                    sts = login.searchStudents(id,name);
                    Iterator<Student>it = sts.iterator();
        %>
        <p>学生信息:<a href="main.jsp">返回主页</a></p>
        <table frame="border" bordercolor="black" style="width: 600px;">
            <tr>
                <td style="border: 1px solid black;">学号</td>
                <td style="border: 1px solid black;">姓名</td>
                <td style="border: 1px solid black;">年龄</td>
                <td style="border: 1px solid black;">性别</td>
                <td style="border: 1px solid black;">专业</td>
            </tr>
            <%
                while(it.hasNext()){
                    Student st = it.next();
            %>
            <%--根据记录的条数,显示学生记录--%>
            <tr>
                <td style="border: 1px solid black"><%=st.getId()%></td>
                <td style="border: 1px solid black"><%=st.getName()%></td>
                <td style="border: 1px solid black"><%=st.getAge()%></td>
                <td style="border: 1px solid black"><%=st.getGender()%></td>
                <td style="border: 1px solid black"><%=st.getMajor()%></td>
            </tr>
            <%
                }
                }
                }
            %>
        </table>
    </font>


</body>
</html>
