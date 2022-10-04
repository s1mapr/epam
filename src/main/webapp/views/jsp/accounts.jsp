<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 02.10.2022
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Accounts</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<table border="3">
  <tr>
    <th>Назва</th>
    <th>Логін</th>
    <th>Ім'я</th>
    <th>Прізвище</th>
    <th>Кількість операцій</th>
    <th>Статус</th>
  </tr>
  <c:forEach var="accounts" items="${requestScope.list}">
    <tr>
      <td>${accounts.name}</td>
      <td>${accounts.userLogin}</td>
      <td>${accounts.userFirstName}</td>
      <td>${accounts.userLastName}</td>
      <td>${accounts.paymentsCount}</td>
      <td>${accounts.status}</td>
      <td><a href="${pageContext.request.contextPath}/accounts?action=block&id=${accounts.id}">Заблокувати</a></td>
      <td><a href="${pageContext.request.contextPath}/accounts?action=unblock&id=${accounts.id}">Розблокувати</a></td>
    </tr>
  </c:forEach>
</table>
<c:choose>
  <c:when test="${sessionScope.accPage == 1}">
    <a href="${pageContext.request.contextPath}/accounts?page=${sessionScope.accPage+1}" >-></a>
  </c:when>
  <c:when test="${sessionScope.accPage == requestScope.pagesCount}">
    <a href="${pageContext.request.contextPath}/accounts?page=${sessionScope.accPage-1}" ><-</a>
  </c:when>
  <c:when test="${sessionScope.accPage > 1 && sessionScope.accPage < requestScope.pagesCount}">
    <a href="${pageContext.request.contextPath}/accounts?page=${sessionScope.accPage-1}" ><-</a>
    <a href="${pageContext.request.contextPath}/accounts?page=${sessionScope.accPage+1}" >-></a>
  </c:when>
</c:choose>


</body>
</html>
