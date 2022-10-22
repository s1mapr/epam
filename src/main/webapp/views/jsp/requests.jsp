<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 07.10.2022
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="requests"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .div-tb{
            margin-right: 250px;
            margin-left: 250px;
        }
    </style>
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="px-2 pt-4 div-tb">
    <table class="table border border-primary">
        <thead class="table bg-primary">
        <tr>
            <th scope="col">
                <a class="text-white text-decoration-none "><fmt:message key="accountName"/></a>
            </th>
            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="paymentsCount"/></a>
            </th>


            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="status"/></a>
            </th>

            <th scope="col">
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="request" items="${requestScope.requests}">
            <tr>
                <td>${request.accountName}</td>
                <td>${request.paymentCount}</td>
                <td>${request.status}</td>
                <td><a class="btn btn-primary mx-1 "
                       href="${pageContext.request.contextPath}/adm/requests?action=unblock&id=${request.accountId}"><fmt:message key="unblock"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="d-flex justify-content-center">
    <c:choose>
        <c:when test="${sessionScope.reqPage == 1 && requestScope.pagesCount >1}">
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/adm/requests?page=${sessionScope.reqPage+1}">&#129046;
            </a>
        </c:when>
        <c:when test="${sessionScope.reqPage == requestScope.pagesCount && sessionScope.reqPage != 1}">
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/adm/requests?page=${sessionScope.reqPage-1}">&#129044;
            </a>
        </c:when>
        <c:when test="${sessionScope.reqPage > 1 && sessionScope.reqPage < requestScope.pagesCount}">
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/adm/requests?page=${sessionScope.reqPage-1}">&#129044;
            </a>
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/adm/requests?page=${sessionScope.reqPage+1}">&#129046;
            </a>
        </c:when>
    </c:choose>
</div>
</body>
</html>
