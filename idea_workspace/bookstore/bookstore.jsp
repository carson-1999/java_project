<%@ page contentType="text/html;charset=GB2312" %>
<%@ include file="common.jsp" %> <!-- �����ļ�-->

<%--ע��:JSP�ı��ʾ���һ��Servlet��JSP������֮ǰ���ȱ�Tomcat����������Ϊ.java�ļ���Ȼ���ڽ�.java�ı�����--%>
<%--    Ϊ.class�ļ����������ڷ���jspʱ����������ľ����Ǹ��������ࡣ--%>

<%--ע��: ����java�ļ��������ֽ����ļ�,��class�ļ���tomcat�е�classes�ļ����еķ���·��,��Ҫ��java�ļ���package��λ��һ��--%>

<html>
    <head>
        <title>
            BookStore
        </title>
        <%@ include file="banner.jsp" %>  <!-- �����ļ�-->
    </head>

    <body>
        <!-- request.getContextPath()��ʾ��ǰĿ¼-->
        <p><b><a href="<%=request.getContextPath()%>/catalog.jsp">�鿴������Ŀ</a></b></p>
        <center>
        <form action="bookdetails.jsp" method="post">
            <h3>�������ѯ��Ϣ:</h3>
            <b>��ı��:</b>
            <input type="text" size="20" name="bookId" value=""><br><br>
            <input type="submit" value="��ѯ">
        </form>
        </center>
    </body>
</html>