<%@ page contentType="text/html;charset=GB2312" language="java" %>
<%@ page import="mypack.*" %>
<!--ע��,�Ѿ���common.jsp������mypack�����е�class�ļ�-->
<%--������jsp�ļ�����common.jspҲ�൱���Ѿ�������mypack�е����е�class�ļ�--%>
<%@ page import="java.util.Properties" %>
<%@ page errorPage="errorpage.jsp" %>

<%--����javabean,ע��:javabean��һ������ķ���һ���淶��java��--%>
<%--ʹ��jsp��ΪԪ�ؼ���javabean,id��ֵ������class��Ӧ������ļ�,�ʿ������������ʹ��idֵָ��������ļ�--%>
<%--jspͨ��ʹ��jsp:usebean��idֵ��Ϊ����,����Jsp���մ������class�ļ�,�����ʹ��idֵ����������java������class�ļ��еķ���,����ʹjsp��java�еķ���������ϵ��--%>
<jsp:useBean id="bookDB" scope="application" class="mypack.BookDB" />

<%! //�ַ������ת��,(���ǵ�����)
    public String convert(String s){
        try{
            return new String(s.getBytes("ISO-8859-1"),"GB2312");//String���
        }catch(Exception e){
            return null;
        }
    }
%>