<%@ page contentType="text/html;charset=GB2312" %>
<%@ include file="common.jsp" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="cart" scope="session" class="mypack.ShoppingCart" />

<%
    bookDB.buyBooks(cart);//购买购物车中的所有书
    //payment received  -- invalidate the session(主动结束会话)
    session.invalidate();
%>

<html>
    <head><title>TitleReceipt</title><%@ include file="banner.jsp"%></head>
    <body>
        <h3><%=request.getParameter("cardname")%>:谢谢您光临新世纪网上书店。</h3><br>
        <strong>
            <a href="<%=request.getContextPath()%>/bookstore.jsp">继续购物</a>&nbsp;&nbsp;&nbsp;
        </strong>
    </body>
</html>