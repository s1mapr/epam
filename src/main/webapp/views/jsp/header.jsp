<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.user == null}">
        <a href = "${pageContext.request.contextPath}/authorization">authorization</a>
        <a href="${pageContext.request.contextPath}/mainPage">Main Page</a>
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
    </c:when>
    <c:when test="${sessionScope.user.role == 'user'}">
        <a href="${pageContext.request.contextPath}/mainPage">Main Page</a>
        <a href="${pageContext.request.contextPath}/profile">Profile</a>
        <a href="${pageContext.request.contextPath}/logout">logout</a>
        <div class="nav-item dropdown">
            <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown" onclick="return false">Створити новий платіж</a>
            <div class="dropdown-menu">
                <a href="${pageContext.request.contextPath}/phoneRecharge" class="dropdown-item">Поповнення телефону</a>
                <a href="${pageContext.request.contextPath}/servicesPayment" class="dropdown-item">Оплата посуг</a>
                <a href="${pageContext.request.contextPath}/cardTransfer" class="dropdown-item">Переказ на картку</a>
                <a href="${pageContext.request.contextPath}/utilitiesPayment" class="dropdown-item">Оплата комунальний послуг</a>
                <a href="${pageContext.request.contextPath}/finesPayment" class="dropdown-item">Оплата штрафів</a>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </c:when>
</c:choose>
</body>
</html>
