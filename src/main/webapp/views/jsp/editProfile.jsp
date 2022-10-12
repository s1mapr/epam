<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 05.10.2022
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit profile</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/user/editProfile" method="post">
    Ім'я: <input name = "firstName" value="${sessionScope.user.firstName}"><br>
    <c:if test="${requestScope.valid.firstName == 'false'}">
        first name error<br>
    </c:if>
    Прізвище: <input name = "lastName" value="${sessionScope.user.lastName}"><br>
    <c:if test="${requestScope.valid.lastName == 'false'}">
        last name error<br>
    </c:if>
    Електрона пошта: <input name = "email" value="${sessionScope.user.email}"><br>
    <c:if test="${requestScope.valid.email == 'false'}">
        email error<br>
    </c:if>
    Номер телефону: <input name = "phoneNumber" value="${sessionScope.user.phoneNumber}"><br>
    <c:if test="${requestScope.valid.phoneNumber == 'false'}">
        phoneNumber error<br>
    </c:if>
    <input type="submit" value="Зберігти зміни">
</form>

</body>
</html>
