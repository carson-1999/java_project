<%@ page contentType="text/html;charset=GB2312" %>
<%@ include file="common.jsp" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="cart" scope="session" class="mypack.ShoppingCart" />

<%
    bookDB.buyBooks(cart);//�����ﳵ�е�������
    //payment received  -- invalidate the session(���������Ự)
    session.invalidate();
%>

<html>
    <head><title>TitleReceipt</title><%@ include file="banner.jsp"%></head>
    <body>
        <h3><%=request.getParameter("cardname")%>:лл������������������ꡣ</h3><br>
        <strong>
            <a href="<%=request.getContextPath()%>/bookstore.jsp">��������</a>&nbsp;&nbsp;&nbsp;
        </strong>
    </body>
</html>