<%@ page contentType="text/html;charset=GB2312" language="java" %>
<%@ page import="mypack.*" %>
<!--注意,已经在common.jsp引入了mypack中所有的class文件-->
<%--故其它jsp文件引入common.jsp也相当于已经引入了mypack中的所有的class文件--%>
<%@ page import="java.util.Properties" %>
<%@ page errorPage="errorpage.jsp" %>

<%--引入javabean,注意:javabean是一种特殊的符合一定规范的java类--%>
<%--使用jsp行为元素加载javabean,id的值代表了class对应这个类文件,故可以在下面代码使用id值指代这个类文件--%>
<%--jsp通过使用jsp:usebean的id值作为对象,由于Jsp最终处理的是class文件,故最后使用id值对象来调用java编译后的class文件中的方法,这样使jsp和java中的方法产生联系。--%>
<jsp:useBean id="bookDB" scope="application" class="mypack.BookDB" />

<%! //字符编码的转换,(考虑到中文)
    public String convert(String s){
        try{
            return new String(s.getBytes("ISO-8859-1"),"GB2312");//String类的
        }catch(Exception e){
            return null;
        }
    }
%>