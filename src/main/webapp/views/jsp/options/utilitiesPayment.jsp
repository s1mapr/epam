<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 01.10.2022
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/views/css/selectStyle.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<form action="${pageContext.request.contextPath}/utilitiesPayment" method="post">
    Показання лічильника води: <input name="meter_w"><br>
    Сума: <input name="amount_w"><br>
    Показання лічильника елктроенергії:<input name="meter_e"><br>
    Сума: <input name="amount_e"><br>
    Показання лічильника елктроенергії:<input name="meter_g"><br>
    Сума: <input name="amount_g"><br>
    <select name="accountId" aria-label="Default select example">
        <option value="0" selected>Оберіть рахунок</option>
        <c:forEach var="names" items="${sessionScope.accounts}">
            <option value="${names.id}">${names.name}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Сплатити">
</form>
</body>
</html>
