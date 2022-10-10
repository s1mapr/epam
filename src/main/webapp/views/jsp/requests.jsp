<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 07.10.2022
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<table border="3">
    <tr>
        <th>
            <a>Название</a>
        </th>
        <th>
            <a>Номер картки</a></th>
        <th>
            <a>Баланс</a>
        </th>
        <th>
            <a>Статус</a>
        </th>
    </tr>

<c:forEach var="request" items="${requestScope.requests}">
    <tr>
        <td>${request.accountName}</td>
        <td>${request.status}</td>
        <td>${request.paymentCount}</td>
        <td><a href="${pageContext.request.contextPath}/requests?action=unblock&id=${request.accountId}">Розблокувати</a></td>
    </tr>
</c:forEach>
</table>
<c:choose>
    <c:when test="${sessionScope.reqPage == 1 && requestScope.pagesCount >1}">
        <a href="${pageContext.request.contextPath}/requests?page=${sessionScope.reqPage+1}">-></a>
    </c:when>
    <c:when test="${sessionScope.reqPage == requestScope.pagesCount && sessionScope.reqPage != 1}">
        <a href="${pageContext.request.contextPath}/requests?page=${sessionScope.reqPage-1}"><-</a>
    </c:when>
    <c:when test="${sessionScope.reqPage > 1 && sessionScope.reqPage < requestScope.pagesCount}">
        <a href="${pageContext.request.contextPath}/requests?page=${sessionScope.reqPage-1}"><-</a>
        <a href="${pageContext.request.contextPath}/requests?page=${sessionScope.reqPage+1}">-></a>
    </c:when>
</c:choose>
</body>
</html>
