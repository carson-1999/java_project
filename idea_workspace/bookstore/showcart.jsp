<%@ page contentType="text/html;charset=GB2312" language="java" %>
<%@ include file="common.jsp" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="cart" scope="session" class="mypack.ShoppingCart" />

<html>
    <head><title>Title Shopping Cart</title><%@ include file="banner.jsp"%></head>
    <body>

    <%
        String bookId = request.getParameter("Remove");
        if(bookId != null){
            cart.remove(bookId);//��ϣ�����ﳵɾ��һ����������
            BookDetails book = bookDB.getBookDetails(bookId);//��ɾ���Ķ�������õ���Ӧ���鱾����
    %>
    <font color="red" size="+2">��ɾ����һ����:<em><%=convert(book.getTitle())%></em>
        <br>
        &nbsp;
        <br>
    </font>
    <%
        }
        //��չ��ﳵ
        if(request.getParameter("Clear") != null){
            cart.clear();
    %>
    <font color="red" size="+2">
        <strong>
            ��չ��ﳵ
        </strong>
        <br>
        &nbsp;<br>
    </font>
    <%
        }
        //Print a summary of a shopping cart
        int num = cart.getNumberOfItems();
        if(num > 0){
    %>
    <font size="+2">���Ĺ��ﳵ����<%=num%>����</font><br>&nbsp;
    <table>
        <tr>
            <th align="left">����</th>
            <th align="left">����</th>
            <th align="left">�۸�</th>
        </tr>
        <%
            Iterator i = cart.getItems().iterator();
            while(i.hasNext()){
                ShoppingCartItem item = (ShoppingCartItem)i.next();
                BookDetails book = (BookDetails)item.getItem();
        %>
        <tr>
            <td align="right" bgcolor="#ffffff"><%=item.getQuantity()%></td>
            <td bgcolor="#ffffaa"><strong>
                <a href="<%=request.getContextPath()%>/bookdetails.jsp?bookId=<%=book.getBookId()%>"><%=convert(book.getTitle())%></a>
            </strong></td>
            <td bgcolor="#ffffaa" align="right"><%=book.getPrice()%></td>
            <td bgcolor="#ffffaa"><strong>
                <a href="<%=request.getContextPath()%>/showcart.jsp?Remove=<%=book.getBookId()%>">ɾ��</a>
            </strong></td>
        </tr>
        <%
            //end of while
            }
        %>
        <tr>
            <td colspan="5" bgcolor="#ffffff"><br></td>
        </tr>
        <tr>
            <td colspan="2" align="right" bgcolor="#ffffff">�ܶ�(Ԫ)</td>
            <td bgcolor="#ffffaa" align="right"><%=cart.getTotal()%></td>
            <td><br></td>
        </tr>
    </table>
    <p>&nbsp;</p>
    <strong><a href="<%=request.getContextPath()%>/catalog.jsp">��������</a>&nbsp;&nbsp;&nbsp;
        <a href="<%=request.getContextPath()%>/cashier.jsp">����</a>&nbsp;&nbsp;&nbsp;
        <a href="<%=request.getContextPath()%>/showcart.jsp?Clear=clear">��չ��ﳵ</a>
    </strong>
    <%
        } else {
    %>
    <font size="+2">
        ���Ĺ��ﳵĿǰΪ��!
    </font>
    <br>&nbsp;<br>
    <a href="<%=request.getContextPath()%>/catalog.jsp">��������</a>
    <%
        //end of if
        }
    %>
    </body>
</html>