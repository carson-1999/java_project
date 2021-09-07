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
        //�ű�������,�൱����дjava����,������Ҳ����ʵ��������,������idֵbookDB������javabean
        BookDetails book = bookDB.getBookDetails(bookId);//���÷���,�õ��鱾����
    %>

    <%
        if(book==null)
        {
    %>
        <p>���"<%=bookId%>"�����ݿ��в�����</p>
        <strong><a href="<%=request.getContextPath()%>/catalog.jsp">��������</a></strong>
    <%
        return;//��bookΪnull��������,ֱ��return,����Ĳ���ִ��,ֻ��book!=null,�Ž���ִ���������
        }
    %>

    <p>����:<%=convert(book.getTitle())%></p>
    ����:<em><%=convert(book.getName())%></em>&nbsp;&nbsp;
    (<%=book.getYear()%>)<br>
    <p>�۸�(Ԫ):<%=book.getPrice()%></p>
    <p>��������:<%=book.getSaleAmount()%></p>
    <p>����:<%=convert(book.getDescription())%></p>

    <p><strong><a href="<%=request.getContextPath()%>/catalog.jsp?Add=<%=bookId%>">���빺�ﳵ</a>&nbsp;&nbsp;&nbsp;
    <a href="<%=request.getContextPath()%>/catalog.jsp">��������</a></strong></p>
    </body>
</html>