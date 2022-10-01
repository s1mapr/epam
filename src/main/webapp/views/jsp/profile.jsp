<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<p>First name: ${sessionScope.user.firstName}</p>
<p>Last name: ${sessionScope.user.lastName}</p>
<p>Email: ${sessionScope.user.email}</p>
<p>Phone number: ${sessionScope.user.phoneNumber}</p>
<table border="3">
    <tr>
        <th>Название</th>
    </tr>
    <c:forEach var="names" items="${sessionScope.accounts}">
        <tr>
            <td>${names.name}</td>
        </tr>
    </c:forEach>
</table>
<a href = "${pageContext.request.contextPath}/addNewAccount">add new account</a>
</body>
</html>
