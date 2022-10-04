<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 03.10.2022
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Users</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<table border="3">
  <tr>
    <th>Логін</th>
    <th>Ім'я</th>
    <th>Прізвище</th>
    <th>Пошта</th>
    <th>Номер телефону</th>
    <th>Статус</th>
    <th>Кількість акаунтів</th>
    <th>Кількість платежів</th>
  </tr>
  <c:forEach var="users" items="${requestScope.list}">
    <tr>
      <td>${users.login}</td>
      <td>${users.firstName}</td>
      <td>${users.lastName}</td>
      <td>${users.email}</td>
      <td>${users.phoneNumber}</td>
      <td>${users.status}</td>
      <td>${users.accountsCount}</td>
      <td>${users.paymentsCount}</td>
      <td><a href="${pageContext.request.contextPath}/users?action=block&id=${users.id}">Заблокувати</a></td>
      <td><a href="${pageContext.request.contextPath}/users?action=unblock&id=${users.id}">Розблокувати</a></td>
    </tr>
  </c:forEach>
</table>
<c:choose>
  <c:when test="${sessionScope.userPage == 1}">
    <a href="${pageContext.request.contextPath}/users?page=${sessionScope.userPage+1}" >-></a>
  </c:when>
  <c:when test="${sessionScope.userPage == requestScope.pagesCount}">
    <a href="${pageContext.request.contextPath}/users?page=${sessionScope.userPage-1}" ><-</a>
  </c:when>
  <c:when test="${sessionScope.userPage > 1 && sessionScope.userPage < requestScope.pagesCount}">
    <a href="${pageContext.request.contextPath}/users?page=${sessionScope.userPage-1}" ><-</a>
    <a href="${pageContext.request.contextPath}/users?page=${sessionScope.userPage+1}" >-></a>
  </c:when>
</c:choose>

</body>
</html>
