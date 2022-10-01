<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <form action="${pageContext.request.contextPath}/authorization" method="post">
        <input name="login"/><br>
        <input name="password" type="password"/><br>
        <input value="Authorization!" type="submit"><br>
    </form>
    Create account: <a href="${pageContext.request.contextPath}/registration">тык</a>
</body>
</html>
