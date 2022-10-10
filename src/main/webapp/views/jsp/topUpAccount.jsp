
<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 08.10.2022
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Top up</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/topUp?id=${sessionScope.accountId}" method="post">
    <input name = cardNumber><br>
    <c:if test="${requestScope.valid.cardNumber == 'false'}">
        card number error<br>
    </c:if>
    <input name = "expiryDate"><br>
    <c:if test="${requestScope.valid.expirationDate == 'false'}">
        expiry date error<br>
    </c:if>
    <input name = "cvv"><br>
    <c:if test="${requestScope.valid.cvv == 'false'}">
        cvv error<br>
    </c:if>
    <input name = "amount"><br>
    <c:if test="${requestScope.valid.paymentAmount == 'false'}">
        amount error<br>
    </c:if>
    <input type="submit" value="Пополнить">
</form>

</body>
</html>
