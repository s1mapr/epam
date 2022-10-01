<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    login: <input name="login"><br>
    password: <input name="password" type="password"><br>
    first name: <input name="firstName"><br>
    last name: <input name="lastName"><br>
    email: <input name="email"><br>
    phone number: <input name="phoneNumber"><br>
    <input value="Gay Development!" type ="submit">
</form>
</body>
</html>
