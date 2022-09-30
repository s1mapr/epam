<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<p>First name: ${sessionScope.user.firstName}</p>
<p>Last name: ${sessionScope.user.lastName}</p>
<p>Email: ${sessionScope.user.email}</p>
<p>Phone number: ${sessionScope.user.phoneNumber}</p>
<a href = "${pageContext.request.contextPath}/addNewAccount">add new account</a>
</body>
</html>
