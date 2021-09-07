<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.carson.util.Constants" %>
<%@ page import="com.carson.pojo.User" %>
<%@include file="/jsp/common/head.jsp"%>

<%
    //获取session存储的登陆用户对象
    User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
%>
<div class="right">
    <img class="wColck" src="../images/clock.jpg" alt=""/>
    <div class="wFont">
        <%--<h2><%=user.getUserName()%></h2>--%>
        <%--表面上调用私有属性字段,只要我们在类中写了getUserName（）方法，那么我们用EL表达式实际上是自动帮我们调用了getUserName（）方法。--%>
        <h2>${userSession.userName}</h2> <%--这里是可以访问javabean的私有属性,实际是调用了方法,这里的userSession对应session的属性名称--%>
        <p>欢迎来到超市订单管理系统!</p><%--使用EL表达式相较于jsp表达式标签的好处是当为空时,页面不会显示null且正常显示--%>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
