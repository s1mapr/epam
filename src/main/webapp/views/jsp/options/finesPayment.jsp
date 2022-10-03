<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 01.10.2022
  Time: 16:52
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
<form action="${pageContext.request.contextPath}/finesPayment" method="post">
    Ім'я: <input name="firstName"><br>
    <c:if test="${requestScope.valid.firstName == 'false'}">
        first name error<br>
    </c:if>
    Прізвище: <input name="lastName"><br>
    <c:if test="${requestScope.valid.lastName == 'false'}">
        last name error<br>
    </c:if>
    По-батькові: <input name ="patronymic"><br>
    <c:if test="${requestScope.valid.patronymic == 'false'}">
        patronymic error<br>
    </c:if>
    Номер штрафа: <input name = "number"><br>
    <c:if test="${requestScope.valid.fineNumber == 'false'}">
        fine number error<br>
    </c:if>
    Сума: <input name="amount"><br>
    <c:if test="${requestScope.valid.paymentAmount == 'false'}">
        payment amount error<br>
    </c:if>
    <select name="accountId" aria-label="Default select example">
        <option value="0" selected>Оберіть рахунок</option>
        <c:forEach var="names" items="${sessionScope.accounts}">
            <c:if test="${names.status == 'unblocked'}">
                <option value="${names.id}">${names.name}</option>
            </c:if>
        </c:forEach>
    </select>
    <input type="submit" value="Сплатити">
    ${requestScope.notEnoughMoney}

</form>

</body>
</html>
