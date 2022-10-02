<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 02.10.2022
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Accounts</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<table border="3">
  <tr>
    <th>Назва</th>
    <th></th>
  </tr>
  <c:forEach var="accounts" items="${requestScope.list}">
    <tr>
      <td>${accounts.name}</td>
      <td><a href="${pageContext.request.contextPath}/accounts?action=block&id=${accounts.id}">Заблокувати</a></td>
      <td><a href="${pageContext.request.contextPath}/accounts?action=unblock&id=${accounts.id}">Розблокувати</a></td>
    </tr>
  </c:forEach>
</table>

</body>
</html>
