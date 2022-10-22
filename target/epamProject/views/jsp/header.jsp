<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="m" uri="/WEB-INF/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <style>
        a {
            margin-left: 10px
        }
    </style>
</head>
<body>

<c:choose>
    <c:when test="${sessionScope.user==null}">
        <nav class="navbar navbar-expand-lg navbar-dark">
            <div class="container-fluid">
                <div class="collapse navbar-collapse justify-content-end">
            <span class="navbar-text">

        <c:if test="${sessionScope.lang == 'uk'}">
            <b><a class="text-decoration-none text-dark" style="opacity: 0.3;"
                  href="${pageContext.request.contextPath}/changeLang?lang=en">${sessionScope.lang}</a></b>
        </c:if>
        <c:if test="${sessionScope.lang == 'en'}">
            <b><a class="text-decoration-none text-dark" style="opacity: 0.3;"
                  href="${pageContext.request.contextPath}/changeLang?lang=uk">${sessionScope.lang}</a></b>
        </c:if>
                <b><a class="text-decoration-none text-dark" style="opacity: 0.3;"><m:today/></a></b>
            </span>
                </div>
            </div>
        </nav>
    </c:when>
    <c:when test="${sessionScope.user.role == 'user'}">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="navbarText">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item ">
                            <div class="nav-item dropdown">
                                <a href="#" class="nav-link active dropdown-toggle" data-bs-toggle="dropdown"
                                   onclick="return false"><fmt:message key="newPayment"/></a>
                                <div class="dropdown-menu">
                                    <a href="${pageContext.request.contextPath}/user/phoneRecharge"
                                       class="dropdown-item"><fmt:message key="phoneRecharge"/></a>
                                    <a href="${pageContext.request.contextPath}/user/servicesPayment"
                                       class="dropdown-item"><fmt:message key="servPayment"/></a>
                                    <a href="${pageContext.request.contextPath}/user/cardTransfer"
                                       class="dropdown-item"><fmt:message key="cardTransfer"/></a>
                                    <a href="${pageContext.request.contextPath}/user/utilitiesPayment"
                                       class="dropdown-item"><fmt:message key="utilitiesPayment"/></a>
                                    <a href="${pageContext.request.contextPath}/user/finesPayment"
                                       class="dropdown-item"><fmt:message key="finesPayment"/></a>
                                </div>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active"
                               href="${pageContext.request.contextPath}/user/myReceipts"><fmt:message
                                    key="myPayments"/></a>
                        </li>
                    </ul>
                    <span class="navbar-text">
                        <c:if test="${sessionScope.lang == 'uk'}">
                            <a class="text-decoration-none"
                               href="${pageContext.request.contextPath}/changeLang?lang=en">${sessionScope.lang}</a>
                        </c:if>
                        <c:if test="${sessionScope.lang == 'en'}">
                            <a class="text-decoration-none"
                               href="${pageContext.request.contextPath}/changeLang?lang=uk">${sessionScope.lang}</a>
                        </c:if>
                        <a class="text-decoration-none"><m:today/></a>
                <a href="${pageContext.request.contextPath}/user/profile" class="text-decoration-none"><fmt:message
                        key="profile"/></a>
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
                               href="${pageContext.request.contextPath}/adm/accounts"><fmt:message key="accounts"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/adm/users"><fmt:message
                                    key="users"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active"
                               href="${pageContext.request.contextPath}/adm/requests"><fmt:message key="requests"/></a>
                        </li>
                    </ul>
                    <span class="navbar-text">
                        <c:if test="${sessionScope.lang == 'uk'}">
                            <a class="text-decoration-none"
                               href="${pageContext.request.contextPath}/changeLang?lang=en">${sessionScope.lang}</a>
                        </c:if>
                        <c:if test="${sessionScope.lang == 'en'}">
                            <a class="text-decoration-none"
                               href="${pageContext.request.contextPath}/changeLang?lang=uk">${sessionScope.lang}</a>
                        </c:if>
                        <a class="text-decoration-none"><m:today/></a>
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
