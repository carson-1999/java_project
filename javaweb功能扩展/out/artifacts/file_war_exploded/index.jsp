
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>File</title>
  </head>
  <body>
  <h1>文件上传</h1>
    <%--表单如果包含一个文件上传输入项的,表单的enctype属性必须设置为:multipart/form-data--%>
    <%--客户端表单的类型如果为:multipart/form-data,服务端想要获取数据就要通过流--%>
  <form action="${pageContext.request.contextPath}/upload.do" method="post" enctype="multipart/form-data">
    <%--${pageContext.request.contextPath}是固定写法,获取服务器路径--%>
    上传用户:<input type="text" name="username"><br>
    <p><input type="file" name="file1"></p>
    <p><input type="file" name="file2"></p>
    <p><input type="submit" value="上传"> | <input type="reset" value="重置"></p>
  </form>

  </body>
</html>
