<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 03.10.2022
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="users"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .arrow {
            margin-left: -15px;
        }
    </style>
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="px-2 pt-4 ">
    <table class="table border border-primary">
        <thead class="table bg-primary">
        <tr>
            <th scope="col">
                <a class="text-white text-decoration-none "><fmt:message key="login"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortLogin&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortLogin&type=DESC&page=1">&#129045;</a>
            </th>

            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="fName"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortName&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortName&type=DESC&page=1">&#129045;</a>
            </th>

            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="lName"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortLastName&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortLastName&type=DESC&page=1">&#129045;</a>
            </th>

            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="email"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortEmail&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortEmail&type=DESC&page=1">&#129045;</a>
            </th>

            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="phoneNumber"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortPhoneNumber&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortPhoneNumber&type=DESC&page=1">&#129045;</a>
            </th>

            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="paymentsCount"/></a>
            </th>

            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="status"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortStatus&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/adm/users?sortAction=sortStatus&type=DESC&page=1">&#129045;</a>
            </th>

            <th scope="col">
            </th>

            <th scope="col">
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="users" items="${requestScope.list}">
            <tr>
                <td>${users.login}</td>
                <td>${users.firstName}</td>
                <td>${users.lastName}</td>
                <td>${users.email}</td>
                <td>${users.phoneNumber}</td>
                <td>${users.paymentsCount}</td>
                <c:choose>
                    <c:when test="${users.status == 'unblocked'}">
                        <td><fmt:message key="unblocked"/></td>
                    </c:when>
                    <c:when test="${users.status == 'blocked'}">
                        <td><fmt:message key="blocked"/></td>
                    </c:when>
                </c:choose>

                <td><a class="btn btn-primary mx-1 "
                       href="${pageContext.request.contextPath}/adm/users?action=block&id=${users.id}"><fmt:message key="block"/></a>
                </td>
                <td><a class="btn btn-primary mx-1 "
                       href="${pageContext.request.contextPath}/adm/users?action=unblock&id=${users.id}"><fmt:message key="unblock"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<div class="d-flex justify-content-center">
    <c:choose>
        <c:when test="${sessionScope.userPage == 1 && requestScope.pagesCount >1}">
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/adm/users?page=${sessionScope.userPage+1}">&#129046;
            </a>
        </c:when>
        <c:when test="${sessionScope.userPage == requestScope.pagesCount && sessionScope.userPage != 1}">
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/adm/users?page=${sessionScope.userPage-1}">&#129044;
            </a>
        </c:when>
        <c:when test="${sessionScope.userPage > 1 && sessionScope.userPage < requestScope.pagesCount}">
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/adm/users?page=${sessionScope.userPage-1}">&#129044;
            </a>
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/adm/users?page=${sessionScope.userPage-1}">&#129046;
            </a>
        </c:when>
    </c:choose>
</div>
</body>
</html>
