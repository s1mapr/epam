<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 20.10.2022
  Time: 12:58
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
    <title><fmt:message key="500error"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="d-flex bg-primary">
    <div style="margin-left: 715px" class="py-2 text-white">
        <p class="pt-1" style="font-size: 22px"><fmt:message key="500error"/></p>
    </div>
    <div style="margin-left: 550px;" class="pt-3">
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
<div style="margin-left: 575px; margin-top: 170px;">
    <div>
        <img src="${pageContext.request.contextPath}/views/img/500error.png">
    </div>
    <div>
        <p style="font-size: 30px"><fmt:message key="serverError"/></p>
    </div>

</div>

</body>
</html>
