<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 01.10.2022
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="myPayments"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <u:arrow arrow="arrow"/>
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="px-2 pt-4 ">
    <table class="table border border-primary">
        <thead class="table bg-primary">
        <tr>
            <th scope="col">
                <a class="text-white text-decoration-none "><fmt:message key="paymentName"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortName&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortName&type=DESC&page=1">&#129045;</a>
            </th>
            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="paymentDate"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortDate&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortDate&type=DESC&page=1">&#129045;</a>
            </th>
            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="paymentPurpose"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortPurpose&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortPurpose&type=DESC&page=1">&#129045;</a>
            </th>
            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="accountName"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortAccount&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortAccount&type=DESC&page=1">&#129045;</a>
            </th>
            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="paymentAmount"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortAmount&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortAmount&type=DESC&page=1">&#129045;</a>
            </th>
            <th scope="col">
                <a class="text-white text-decoration-none"><fmt:message key="status"/></a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortStatus&type=ASC&page=1">&#129047;</a>
                <a class="text-white text-decoration-none arrow"
                   href="${pageContext.request.contextPath}/user/myReceipts?sortAction=sortStatus&type=DESC&page=1">&#129045;</a>
            </th>
            <th scope="col">
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="receipts" items="${requestScope.list}">
            <tr>
                <td>${receipts.name}</td>
                <td>${receipts.date}</td>
                <td>${receipts.purpose}</td>
                <td>${receipts.accountName}</td>
                <td>${receipts.amount}</td>
                <td>${receipts.status}</td>
                <td><a href="${pageContext.request.contextPath}/user/download?id=${receipts.id}&purpose=${receipts.purpose}&name=${receipts.name}&date=${receipts.date}" class="btn btn-primary"><fmt:message key="receipt"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="d-flex justify-content-center">
    <c:choose>
        <c:when test="${sessionScope.recPage == 1 && requestScope.pagesCount >1}">
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/user/myReceipts?page=${sessionScope.recPage+1}">&#129046;

            </a>
        </c:when>
        <c:when test="${sessionScope.recPage == requestScope.pagesCount && sessionScope.recPage != 1}">
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/user/myReceipts?page=${sessionScope.recPage-1}">&#129044;
            </a>
        </c:when>
        <c:when test="${sessionScope.recPage > 1 && sessionScope.recPage < requestScope.pagesCount}">
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/user/myReceipts?page=${sessionScope.recPage-1}">&#129044;
            </a>
            <a class="btn btn-primary mx-1 mb-1"
               href="${pageContext.request.contextPath}/user/myReceipts?page=${sessionScope.recPage+1}">&#129046;
            </a>
        </c:when>
    </c:choose>
</div>

</body>
</html>
