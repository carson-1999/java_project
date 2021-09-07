<%@ page contentType="text/html;charset=GB2312" %>

<%@ include file="common.jsp" %>
<%@ page import="java.util.*" %>

<%--引入javabean,注意:javabean是一种特殊的符合一定规范的java类--%>
<%--使用jsp行为元素加载javabean,id的值代表了class对应这个类文件,故可以在下面代码使用id值指代这个类文件--%>
<%--jsp通过使用jsp:usebean的id值作为对象,由于Jsp最终处理的是class文件,故最后使用id值对象来调用java编译后的class文件中的方法,这样使jsp和java中的方法产生联系--%>
<jsp:useBean id="cart" scope="session" class="mypack.ShoppingCart"/>

<html>
<head><title>TitleCashier</title></head>
<%@ include file="banner.jsp" %>
<p>您一共购买了<%=cart.getNumberOfItems() %>本书</P>
<p>您应支付的金额为<%=cart.getTotal() %>元</p>

<form action="<%=request.getContextPath()%>/receipt.jsp" method="post">
<table>
<tr>
<td><strong>信用卡用户名</strong></td>
<td><input type="text" name="cardname" value="guest" size="19"></td>
</tr>
<tr>
<td><strong>信用卡账号</strong></td>
<td><input type="text" name="cardnum" value="xxxx xxxx xxxx xxxx" size="19"></td>
</tr>
<tr>
<td></td>
<td><input type="submit" value="递交"></td>
</tr>
</table>
</form>
</body>
</html>
