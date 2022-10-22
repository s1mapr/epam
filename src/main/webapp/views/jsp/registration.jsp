<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>

<html>
<head>
    <title><fmt:message key="registration"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="container-fluid vh-50" style="margin-top:60px">
    <div class="rounded d-flex justify-content-center">
        <div class="col-md-4 col-sm-12 shadow-lg py-1 bg-light">
            <div class="text-center">
                <h3 class="text-primary"><fmt:message key="registration"/></h3>
            </div>
            <form action="${pageContext.request.contextPath}/registration" method="post">
                <div class="p-4">
                    <div class="input-group">
                        <input name="login" class="form-control" placeholder="<fmt:message key="login"/>">
                    </div>
                    <c:if test="${requestScope.valid.login == 'false'}">
                        <div><cite style="color: red">login error</cite></div>
                    </c:if>
                    <div class="input-group mt-3">
                        <input name="password" type="password" class="form-control" placeholder="<fmt:message key="password"/>">
                    </div>
                    <c:if test="${requestScope.valid.password == 'false'}">
                        <cite style="color: red">password error</cite>
                    </c:if>
                    <div class="input-group mt-3">
                        <input name="firstName" class="form-control" placeholder="<fmt:message key="fName"/>">
                    </div>
                    <c:if test="${requestScope.valid.firstName == 'false'}">
                        <cite style="color: red">error first name</cite>
                    </c:if>
                    <div class="input-group mt-3">
                        <input name="lastName" class="form-control" placeholder="<fmt:message key="lName"/>">
                    </div>
                    <c:if test="${requestScope.valid.lastName == 'false'}">
                        <cite style="color: red">error last name</cite>
                    </c:if>
                    <div class="input-group mt-3">
                        <input name="email" class="form-control" placeholder="<fmt:message key="email"/>">
                    </div>
                    <c:if test="${requestScope.valid.email == 'false'}">
                        <cite style="color: red">error email</cite>
                    </c:if>
                    <div class="input-group mt-3">
                        <input name="phoneNumber" class="form-control" placeholder="<fmt:message key="phoneNumber"/>">
                    </div>
                    <c:if test="${requestScope.valid.phoneNumber == 'false'}">
                        <cite style="color: red">error phone number</cite>
                    </c:if>
                    <div class="form-row text-center">
                        <button class="btn btn-primary text-center mt-1" type="submit">
                            <fmt:message key="register"/>
                        </button>
                    </div>
                    <a href="${pageContext.request.contextPath}/authorization" class="text-decoration-none text-dark" style="opacity: 0.7;"><fmt:message key="back"/></a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
