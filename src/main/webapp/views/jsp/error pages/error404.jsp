<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 20.10.2022
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="m" uri="/WEB-INF/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="404error"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        img {
            width: 440px;
            height: 400px;
        }
    </style>
</head>
<body>
<div class="d-flex bg-primary">
    <div style="margin-left: 715px" class="py-2 text-white">
        <p class="pt-1" style="font-size: 22px"><fmt:message key="404error"/></p>
    </div>
    <div  style="margin-left: 550px;" class="pt-3">
        <c:if test="${sessionScope.lang == 'uk'}">
            <a class="text-decoration-none text-white"
               href="${pageContext.request.contextPath}/changeLang?lang=en">${sessionScope.lang}</a>
        </c:if>
        <c:if test="${sessionScope.lang == 'en'}">
            <a class="text-decoration-none text-white"
               href="${pageContext.request.contextPath}/changeLang?lang=uk">${sessionScope.lang}</a>
        </c:if>
        <a class="text-decoration-none text-white"><m:today/></a>
    </div>
</div>
<div class="d-flex" style="margin-left: 150px; margin-top: 150px">
    <div>
        <img src="${pageContext.request.contextPath}/views/img/404error.jpg">
    </div>
    <div style="margin-left: 200px; height: 400px; width: 350px; padding-top: 50px">
        <h1><fmt:message key="pageNotFound"/></h1>
        <p class="pt-3" style="font-size: 20px"><fmt:message key="pageNotFoundMsg"/></p>
        <c:if test="${sessionScope.user == null}">
            <a class="btn btn-primary mt-3" style="font-size: 18px"
               href="${pageContext.request.contextPath}/mainPage"><fmt:message key="backToSite"/></a>
        </c:if>
        <c:if test="${sessionScope.user.role == 'user'}">
            <a class="btn btn-primary mt-3" style="font-size: 18px"
               href="${pageContext.request.contextPath}/user/myReceipts"><fmt:message key="backToSite"/></a>
        </c:if>
        <c:if test="${sessionScope.user.role == 'admin'}">
            <a class="btn btn-primary mt-3" style="font-size: 18px"
               href="${pageContext.request.contextPath}/adm/accounts"><fmt:message key="backToSite"/></a>
        </c:if>
    </div>
</div>
</body>
</html>
