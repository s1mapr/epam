<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 30.09.2022
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href = "${pageContext.request.contextPath}/views/css/selectStyle.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<form action="${pageContext.request.contextPath}/phoneRecharge" method="post">
    Номер телефону: <input name="phone"><br>
    Сума: <input name = "amount"><br>
    <select aria-label="Default select example">
        <option selected>Оберіть рахунок</option>
        <option value="1">One</option>
        <option value="2">Two</option>
        <option value="3">Three</option>
    </select>
    <input type="submit" value="Сплатити">
</form>
</body>
</html>
