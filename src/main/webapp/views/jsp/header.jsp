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
<style>
    a{margin-left: 10px}
</style>
</head>
<body>

<c:choose>
    <c:when test="${sessionScope.user == null}">
        <a href="${pageContext.request.contextPath}/authorization">authorization</a>
        <a href="${pageContext.request.contextPath}/mainPage">Main Page</a>
    </c:when>
    <c:when test="${sessionScope.user.role == 'user'}">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="navbarText">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="${pageContext.request.contextPath}/mainPage">Home</a>
                        </li>
                        <li class="nav-item ">
                            <div class="nav-item dropdown">
                                <a href="#" class="nav-link active dropdown-toggle" data-bs-toggle="dropdown"
                                   onclick="return false">Створити новий платіж</a>
                                <div class="dropdown-menu">
                                    <a href="${pageContext.request.contextPath}/user/phoneRecharge" class="dropdown-item">Поповнення
                                        телефону</a>
                                    <a href="${pageContext.request.contextPath}/user/servicesPayment" class="dropdown-item">Оплата
                                        посуг</a>
                                    <a href="${pageContext.request.contextPath}/user/cardTransfer" class="dropdown-item">Переказ
                                        на картку</a>
                                    <a href="${pageContext.request.contextPath}/user/utilitiesPayment" class="dropdown-item">Оплата
                                        комунальний послуг</a>
                                    <a href="${pageContext.request.contextPath}/user/finesPayment" class="dropdown-item">Оплата
                                        штрафів</a>
                                </div>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/user/myReceipts">My
                                payments</a>
                        </li>
                    </ul>
                    <span class="navbar-text">
                <a href="${pageContext.request.contextPath}/user/profile" class="text-decoration-none">Profile</a>
                <a href="${pageContext.request.contextPath}/logout" class="text-decoration-none">
                    <i><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
                            class="bi bi-box-arrow-right" viewBox="0 0 20 20">
  <path fill-rule="evenodd"
        d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"/>
  <path fill-rule="evenodd"
        d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
                    </svg></i>
                </a>
      </span>
                </div>
            </div>
        </nav>

    </c:when>
    <c:when test="${sessionScope.user.role == 'admin'}">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="${pageContext.request.contextPath}/adm/accounts">Accounts</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/adm/users">Users</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/adm/requests">Requests</a>
                        </li>
                    </ul>
                    <span class="navbar-text">
                <a href="${pageContext.request.contextPath}/logout" class="text-decoration-none">
                    <i><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
                            class="bi bi-box-arrow-right" viewBox="0 0 20 20">
  <path fill-rule="evenodd"
        d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"/>
  <path fill-rule="evenodd"
        d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
                    </svg></i>
                </a>
      </span>
                </div>
            </div>
        </nav>
    </c:when>
</c:choose>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
