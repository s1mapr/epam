<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    login: <input name="login"><br>
    <c:if test="${requestScope.valid.login == 'false'}">
        login error<br>
    </c:if>
    password: <input name="password" type="password"><br>
    <c:if test="${requestScope.valid.password == 'false'}">
        password error<br>
    </c:if>
    first name: <input name="firstName"><br>
    <c:if test="${requestScope.valid.firstName == 'false'}">
        error first name<br>
    </c:if>
    last name: <input name="lastName"><br>
    <c:if test="${requestScope.valid.lastName == 'false'}">
        error last name<br>
    </c:if>
    email: <input name="email"><br>
    <c:if test="${requestScope.valid.email == 'false'}">
        error email<br>
    </c:if>
    phone number: <input name="phoneNumber"><br>
    <c:if test="${requestScope.valid.phoneNumber == 'false'}">
        error phone number<br>
    </c:if>
    <input value="Gay Development!" type ="submit">
</form>
</body>
</html>
