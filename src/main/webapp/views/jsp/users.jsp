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
    <th><a href="${pageContext.request.contextPath}/users?sortAction=sortLogin&sortId=${sessionScope.usersSortLoginId}&page=1">Логін</a></th>
    <th><a href="${pageContext.request.contextPath}/users?sortAction=sortName&sortId=${sessionScope.usersSortUserName}&page=1">Ім'я</a></th>
    <th><a href="${pageContext.request.contextPath}/users?sortAction=sortLastName&sortId=${sessionScope.usersSortUserLastName}&page=1">Прізвище</a></th>
    <th><a href="${pageContext.request.contextPath}/users?sortAction=sortEmail&sortId=${sessionScope.usersSortEmailId}&page=1">Пошта</a></th>
    <th><a href="${pageContext.request.contextPath}/users?sortAction=sortPhoneNumber&sortId=${sessionScope.usersSortPhoneNumberId}&page=1">Номер телефону</a></th>
    <th>Кількість платежів</th>
    <th><a href="${pageContext.request.contextPath}/users?sortAction=sortStatus&sortId=${sessionScope.usersSortStatusId}&page=1">Статус</a></th>
  </tr>
  <c:forEach var="users" items="${requestScope.list}">
    <tr>
      <td>${users.login}</td>
      <td>${users.firstName}</td>
      <td>${users.lastName}</td>
      <td>${users.email}</td>
      <td>${users.phoneNumber}</td>
      <td>${users.paymentsCount}</td>
      <td>${users.status}</td>
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
