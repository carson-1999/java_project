<%@ page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo" %>
<%@ page import="javax.print.DocFlavor" %><%--
  Created by IntelliJ IDEA.
  User: 张盛滨
  Date: 2021/8/18
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>pageContextDemo01</title>
</head>
<body>
        <%--内置对象--%>
        <%
            pageContext.setAttribute("name1","Carson张1号");//保存的数据仅在一个页面中有效
            request.setAttribute("name2","Carson张2号");//保存的数据仅在一个请求中有效,请求转发会携带这个数据
            session.setAttribute("name3","Carson张3号");//保存的数据仅在一个会话中有效,即从打开浏览器到关闭浏览器
            application.setAttribute("name4","Carson张4号");//保存的数据只在服务器中有效,从打开服务器到关闭服务器
        %>

        <%
            //从pageContext中取出,我们通过寻找的方式来
            //从底层到高层(作用域),即page-->request-->session-->application
            String name1 = (String)pageContext.findAttribute("name1");
            String name2 = (String)pageContext.findAttribute("name2");
            String name3 = (String)pageContext.findAttribute("name3");
            String name4 = (String)pageContext.findAttribute("name4");
            String name5 = (String)pageContext.findAttribute("name5");//不存在

            pageContext.forward("pageContextDemo02.jsp");
        %>

        <%--使用EL表达式进行输出,即${}的形式--%>
        <h1>取出的值为:</h1>
        <h3>${name1}</h3>
        <h3>${name2}</h3>
        <h3>${name3}</h3>
        <h3>${name4}</h3>
        <h3>${name5}</h3>
        <h3><%=name5%></h3>

</body>
</html>
