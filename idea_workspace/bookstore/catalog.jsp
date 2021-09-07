<%@ page contentType="text/html;charset=GB2312" %>

<%@ include file="common.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.nio.file.attribute.GroupPrincipal" %>

<%--ʹ��jsp��ΪԪ�ؼ���javabean,id��ֵ������class��Ӧ������ļ�,�ʿ������������ʹ��idֵָ��������ļ�--%>
<%--jspͨ��ʹ��jsp:usebean��idֵ��Ϊ����,����Jsp���մ������class�ļ�,�����ʹ��idֵ����������java������class�ļ��еķ���,����ʹjsp��java�еķ���������ϵ��--%>
<jsp:useBean id="cart" scope="session" class="mypack.ShoppingCart" />

<html>
    <head><title>BookCatalog</title><%@ include file="banner.jsp"%></head>
    <body>
    <%
        //Additions to the shopping cart
        String bookId = request.getParameter("Add");
        if(bookId != null){
            BookDetails book = bookDB.getBookDetails(bookId);
            cart.add(bookId,book);
    %>
    <p><h3>
        <font color="red">���ѽ�<i><%=convert(book.getTitle())%></i>���빺�ﳵ</font>
    </h3></p>
    <%
        } if (cart.getNumberOfItems() > 0){
    %>

    <p><strong><a href="<%=request.getContextPath()%>/showcart.jsp">�鿴���ﳵ</a>&nbsp;&nbsp;&nbsp;
        <a href="<%=request.getContextPath()%>/cashier.jsp">����</a></strong></p>
    <%
        }
    %>
    <h3>��ѡ��Ҫ�������:</h3>
    <table>
        <%
            Collection c = bookDB.getBooks();
            Iterator i = c.iterator();
            while (i.hasNext()){
                BookDetails book = (BookDetails)i.next();
                bookId = book.getBookId();
        %>
        <tr>
            <td bgcolor="#ffffaa"><a href="<%=request.getContextPath()%>/bookdetails.jsp?bookId=<%=bookId%>"><strong>
                <%=convert(book.getTitle())%>&nbsp;
            </strong></a></td>
            <td bgcolor="#ffffaa" rowspan="2"></td>
            <td bgcolor="#ffffaa" rowspan="2"><a href="<%=request.getContextPath()%>/catalog.jsp?Add=<%=bookId%>">&nbsp;���빺�ﳵ&nbsp;
            </a></td>
        </tr>
        <tr>
            <td bgcolor="#ffffff">
                &nbsp;&nbsp;����:<em><%=convert(book.getName())%>&nbsp;</em>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>