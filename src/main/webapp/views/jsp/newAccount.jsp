<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 29.09.2022
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Account</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<form action="${pageContext.request.contextPath}/addNewAccount" method="post">
  Name: <input name = "name"><br>
  Card number: <input name="card"><br>
  Expiration date: <input name = "date"><br>
  CVV: <input name="cvv"><br>
  <input  value = "Create new Account" type="submit">
</form>
<a href = "${pageContext.request.contextPath}/profile">Back to profile</a>
</body>
</html>
