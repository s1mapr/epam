<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 20.10.2022
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error404</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        img {
            width: 440px;
            height: 400px;
        }
    </style>
</head>
<body>
<div class="bg-primary d-flex justify-content-center py-2 text-white">
    <p class="pt-1" style="font-size: 22px">404 ERROR</p>
</div>
<div class="d-flex" style="margin-left: 150px; margin-top: 150px">
    <div>
        <img src="${pageContext.request.contextPath}/views/img/404error.jpg">
    </div>
    <div style="margin-left: 200px; height: 400px; width: 350px; padding-top: 50px">
        <h1>Page not found...</h1>
        <p class="pt-3" style="font-size: 20px">You are here because page, you want to find, doesn't exist, or it was relocated to another address</p>
        <a class="btn btn-primary mt-3" style="font-size: 18px" href="${pageContext.request.contextPath}/mainPage">Main page</a>
    </div>
</div>
</body>
</html>
