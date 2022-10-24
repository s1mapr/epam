
<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="authorization"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="container-fluid vh-50" style="margin-top:150px">
    <div class="rounded d-flex justify-content-center">
        <div class="col-md-4 col-sm-12 shadow-lg px-5 pt-5 bg-light">
            <div class="text-center">
                <h3 class="text-primary"><fmt:message key="authorization"/></h3>
            </div>
            <form action="${pageContext.request.contextPath}/authorization" method="post">
                <div class="p-4">
                    <div class="input-group mb-3">
                        <input name="login" class="form-control" placeholder="<fmt:message key="login"/>">
                    </div>
                    <div class="input-group mb-3">
                        <input name="password" type="password" class="form-control" placeholder="<fmt:message key="password"/>">
                    </div>
                    <c:if test="${requestScope.youAreBlocked != null}">
                    <div><cite style="color: red; font-size:11px"><fmt:message key="blockMsg"/></cite></div>
                    </c:if>
                    <c:if test="${requestScope.loginError != null}">
                        <div><cite style="color: red; font-size:11px"><fmt:message key="logInError"/></cite></div>
                    </c:if>

                    <div class="form-row text-center">
                        <button class="btn btn-primary text-center mt-4" type="submit">
                            <fmt:message key="logIn"/>
                        </button>
                    </div>
                    <p class="text-center mt-4"><fmt:message key="noReg"/>
                        <a href="${pageContext.request.contextPath}/registration" class="text-decoration-none"><fmt:message key="register"/></a>
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
