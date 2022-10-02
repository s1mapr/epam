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
        <th>Назва</th>
        <th>Дата</th>
        <th>Мета платежу</th>
        <th>Ім'я аккаунту</th>
        <th>Сума</th>
        <th>Статус</th>

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

</body>
</html>
