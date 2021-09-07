<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="java.util.*" %>
<%@ include file="common.jsp" %>

<html>
    <head><title>TitleBookDescription</title><%@ include file="banner.jsp"%></head>
    <body>
    <br>

    <%
        //GET the identifier of the book to display
        String bookId = request.getParameter("bookId");
        if(bookId==null){
            bookId = "201";
        }
        //脚本程序内,相当于书写java代码,故这里也可以实例化对象,这里用id值bookDB来引用javabean
        BookDetails book = bookDB.getBookDetails(bookId);//调用方法,得到书本对象
    %>

    <%
        if(book==null)
        {
    %>
        <p>书号"<%=bookId%>"在数据库中不存在</p>
        <strong><a href="<%=request.getContextPath()%>/catalog.jsp">继续购物</a></strong>
    <%
        return;//在book为null的条件下,直接return,下面的不会执行,只有book!=null,才接着执行下面代码
        }
    %>

    <p>书名:<%=convert(book.getTitle())%></p>
    作者:<em><%=convert(book.getName())%></em>&nbsp;&nbsp;
    (<%=book.getYear()%>)<br>
    <p>价格(元):<%=book.getPrice()%></p>
    <p>销售数量:<%=book.getSaleAmount()%></p>
    <p>评论:<%=convert(book.getDescription())%></p>

    <p><strong><a href="<%=request.getContextPath()%>/catalog.jsp?Add=<%=bookId%>">加入购物车</a>&nbsp;&nbsp;&nbsp;
    <a href="<%=request.getContextPath()%>/catalog.jsp">继续购物</a></strong></p>
    </body>
</html>