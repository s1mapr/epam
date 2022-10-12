<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid vh-50" style="margin-top:150px">
    <div class="rounded d-flex justify-content-center">
        <div class="col-md-4 col-sm-12 shadow-lg px-5 pt-5 bg-light">
            <div class="text-center">
                <h3 class="text-primary">Авторизація</h3>
            </div>
            <form action="${pageContext.request.contextPath}/authorization" method="post">
                <div class="p-4">
                    <div class="input-group mb-3">
                        <input name="login" class="form-control" placeholder="Логін">
                    </div>
                    <div class="input-group mb-3">
                        <input name="password" type="password" class="form-control" placeholder="Пароль">
                    </div>
                    <div class="form-row text-center">
                        <button class="btn btn-primary text-center mt-2" type="submit">
                            Авторизуватися
                        </button>
                    </div>
                    <p class="text-center mt-5">Ще не зареєстровані?
                        <a href="${pageContext.request.contextPath}/registration" class="text-decoration-none">Зареєструватися</a>
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
