<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-image: url("${pageContext.request.contextPath}/views/img/mainPageBackground.jpg");
            background-repeat: no-repeat;
            background-size: 100%;
        }
    </style>
</head>
<title><fmt:message key="mPage"/></title>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="container-fluid vh-50" style="margin-top:180px">
    <div class="rounded d-flex justify-content-center" style="opacity: 0.7;">
        <div style="word-break: break-all;" class="col-md-4 col-sm-12 shadow-lg px-5 pt-5 bg-light">
            <h3><fmt:message key="mTitle"/></h3>
            <p style="font-size: 18px"><fmt:message key="mText"/></p>
            <div class="d-flex justify-content-center">
                <a class="btn btn-primary mb-2" href="${pageContext.request.contextPath}/authorization"><fmt:message key="logIn"/></a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
