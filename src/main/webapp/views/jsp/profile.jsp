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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<p>First name: ${sessionScope.user.firstName}</p>
<p>Last name: ${sessionScope.user.lastName}</p>
<p>Email: ${sessionScope.user.email}</p>
<p>Phone number: ${sessionScope.user.phoneNumber}</p>
<a href="${pageContext.request.contextPath}/editProfile">Edit profile</a><br>
<table border="3">
    <tr>
        <th>
            Назва
            <a href = "${pageContext.request.contextPath}/profile?sortAction=sortName&type=ASC&page=1">&#129047;</a>
            <a href = "${pageContext.request.contextPath}/profile?sortAction=sortName&type=DESC&page=1">&#129045;</a>
        </th>
        <th>
            Номер картки
            <a href = "${pageContext.request.contextPath}/profile?sortAction=sortCardNumber&type=ASC&page=1">&#129047;</a>
            <a href = "${pageContext.request.contextPath}/profile?sortAction=sortCardNumber&type=DESC&page=1">&#129045;</a>
        </th>
        <th>
            Баланс
            <a href = "${pageContext.request.contextPath}/profile?sortAction=sortAmount&type=ASC&page=1">&#129047;</a>
            <a href = "${pageContext.request.contextPath}/profile?sortAction=sortAmount&type=DESC&page=1">&#129045;</a>
        </th>
        <th>
            Статус
            <a href = "${pageContext.request.contextPath}/profile?sortAction=sortStatus&type=ASC&page=1">&#129047;</a>
            <a href = "${pageContext.request.contextPath}/profile?sortAction=sortStatus&type=DESC&page=1">&#129045;</a>
        </th>
    </tr>
    <c:forEach var="names" items="${requestScope.accountsPag}">
        <tr>

            <td>${names.name}</td>
            <td>${names.cardNumber}</td>
            <td>${names.amount}</td>
            <td>${names.status}</td>
            <td><a href="${pageContext.request.contextPath}/topUp?id=${names.id}">Пополнить счет</a></td>
            <c:choose>
            <c:when test="${names.status == 'unblocked'}">
                <td><a href="${pageContext.request.contextPath}/profile?action=block&id=${names.id}">Заблокувати</a></td>
            </c:when>
            <c:when test="${names.status == 'blocked'}">
                <td><a href="${pageContext.request.contextPath}/profile?action=unblock&id=${names.id}">Надіслати запит на розблокування</a></td>
            </c:when>
            </c:choose>
        </tr>
    </c:forEach>

</table>
<c:choose>
    <c:when test="${sessionScope.profPage == 1 && requestScope.pagesCount >1}">
        <a href="${pageContext.request.contextPath}/profile?page=${sessionScope.profPage+1}">-></a>
    </c:when>
    <c:when test="${sessionScope.profPage == requestScope.pagesCount && sessionScope.profPage != 1}">
        <a href="${pageContext.request.contextPath}/profile?page=${sessionScope.profPage-1}"><-</a>
    </c:when>
    <c:when test="${sessionScope.profPage > 1 && sessionScope.profPage < requestScope.pagesCount}">
        <a href="${pageContext.request.contextPath}/profile?page=${sessionScope.profPage-1}"><-</a>
        <a href="${pageContext.request.contextPath}/profile?page=${sessionScope.profPage+1}">-></a>
    </c:when>
</c:choose>
<a href="${pageContext.request.contextPath}/addNewAccount">add new account</a>
</body>
</html>
