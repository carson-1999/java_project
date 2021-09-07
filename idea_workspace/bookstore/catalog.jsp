<%@ page contentType="text/html;charset=GB2312" %>

<%@ include file="common.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.nio.file.attribute.GroupPrincipal" %>

<%--使用jsp行为元素加载javabean,id的值代表了class对应这个类文件,故可以在下面代码使用id值指代这个类文件--%>
<%--jsp通过使用jsp:usebean的id值作为对象,由于Jsp最终处理的是class文件,故最后使用id值对象来调用java编译后的class文件中的方法,这样使jsp和java中的方法产生联系。--%>
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
        <font color="red">您已将<i><%=convert(book.getTitle())%></i>加入购物车</font>
    </h3></p>
    <%
        } if (cart.getNumberOfItems() > 0){
    %>

    <p><strong><a href="<%=request.getContextPath()%>/showcart.jsp">查看购物车</a>&nbsp;&nbsp;&nbsp;
        <a href="<%=request.getContextPath()%>/cashier.jsp">付款</a></strong></p>
    <%
        }
    %>
    <h3>请选择要购买的书:</h3>
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
            <td bgcolor="#ffffaa" rowspan="2"><a href="<%=request.getContextPath()%>/catalog.jsp?Add=<%=bookId%>">&nbsp;加入购物车&nbsp;
            </a></td>
        </tr>
        <tr>
            <td bgcolor="#ffffff">
                &nbsp;&nbsp;作者:<em><%=convert(book.getName())%>&nbsp;</em>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>