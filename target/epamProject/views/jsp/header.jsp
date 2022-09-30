<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.user == null}">
        <a href = "${pageContext.request.contextPath}/authorization">authorization</a>
    </c:when>
    <c:when test="${sessionScope.user.role == 'user'}">
        <a href="${pageContext.request.contextPath}/profile">profile</a>
        <a href="${pageContext.request.contextPath}/logout">logout</a>
    </c:when>
</c:choose>
</body>
</html>
