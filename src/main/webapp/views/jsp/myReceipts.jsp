<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 01.10.2022
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My receipts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<table border="3">
    <tr>
        <th><a href="${pageContext.request.contextPath}/myReceipts?action=sortName&sortId=${sessionScope.nameSortId}&page=1">Назва</a></th>
        <th><a href="${pageContext.request.contextPath}/myReceipts?action=sortDate&sortId=${sessionScope.dateSortId}&page=1">Дата</a></th>
        <th><a href="${pageContext.request.contextPath}/myReceipts?action=sortPurpose&sortId=${sessionScope.purposeSortId}&page=1">Мета платежу</a></th>
        <th><a href="${pageContext.request.contextPath}/myReceipts?action=sortAccount&sortId=${sessionScope.accountSortId}&page=1">Ім'я аккаунту</a></th>
        <th><a href="${pageContext.request.contextPath}/myReceipts?action=sortAmount&sortId=${sessionScope.amountSortId}&page=1">Сума</a></th>
        <th><a href="${pageContext.request.contextPath}/myReceipts?action=sortStatus&sortId=${sessionScope.statusSortId}&page=1">Статус</a></th>

    </tr>
    <c:forEach var="receipts" items="${requestScope.list}">
        <tr>
            <td>${receipts.name}</td>
            <td>${receipts.date}</td>
            <td>${receipts.purpose}</td>
            <td>${receipts.accountName}</td>
            <td>${receipts.amount}</td>
            <td>${receipts.status}</td>
            <td><a>Квитанція</a></td>
        </tr>
    </c:forEach>
</table>
<c:choose>
    <c:when test="${sessionScope.recPage == 1 && requestScope.pagesCount !=1}">
        <a href="${pageContext.request.contextPath}/myReceipts?page=${sessionScope.recPage+1}" >-></a>
    </c:when>
    <c:when test="${sessionScope.recPage == requestScope.pagesCount && sessionScope.recPage != 1}">
        <a href="${pageContext.request.contextPath}/myReceipts?page=${sessionScope.recPage-1}" ><-</a>
    </c:when>
    <c:when test="${sessionScope.recPage > 1 && sessionScope.recPage < requestScope.pagesCount}">
        <a href="${pageContext.request.contextPath}/myReceipts?page=${sessionScope.recPage-1}" ><-</a>
        <a href="${pageContext.request.contextPath}/myReceipts?page=${sessionScope.recPage+1}" >-></a>
    </c:when>
</c:choose>

</body>
</html>
