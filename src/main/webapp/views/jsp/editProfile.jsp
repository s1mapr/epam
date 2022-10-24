<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 05.10.2022
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="editProfile"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .edit-form {
            display: none;
        }

        .avatar {
            width: 100px;
            height: 100px;
            border-radius: 100%;
            box-shadow: 0 10px 20px rgba(134, 134, 134, 0.35);
        }

        .edit-btn {
            margin-right: 50px;
        }

        .edit-form-active {
            display: block;
        }
    </style>
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="container-fluid vh-50" style="margin-top:30px">
    <div class="rounded d-flex justify-content-center">
        <div class="col-md-4 col-sm-12 shadow-lg px-5 pt-3 bg-light">
            <div class="text-center">
                <h3 class="text-primary"><fmt:message key="editProfile"/></h3>
            </div>
            <div class="d-flex justify-content-center my-2">
                <img src="${sessionScope.user.avatarURL}" class="avatar">
            </div>
            <div class="d-flex justify-content-end mb-2">
                <a class="edit-btn btn bg-primary text-white"><fmt:message key="edit"/></a>
            </div>
            <form action="${pageContext.request.contextPath}/user/upload" method="post"
                  enctype="multipart/form-data" class="edit-form">
                <div class="p-2">
                    <div class="input-group d-flex justify-content-end">
                        <label for="files" class="btn btn-outline-primary my-2 btn-sm"><fmt:message key="selectImage"/></label>
                        <input id="files" style="display: none" type="file" name="file"/>
                    </div>
                    <div class="input-group mb-1 d-flex justify-content-center">
                        <input class="sub-btn btn btn-primary" type="submit" value="<fmt:message key="uploadImage"/>"/>
                    </div>
                </div>
            </form>
            <c:if test="${requestScope.imageError != null}">
                <cite style="color: red; font-size:11px"><fmt:message key="imageError"/></cite>
            </c:if>
            <form action="${pageContext.request.contextPath}/user/editProfile" method="post">
                <div class="input-group">
                    <input name="firstName" class="form-control" value="${sessionScope.user.firstName}"
                           placeholder="<fmt:message key="fName"/>"/>
                </div>
                <c:if test="${requestScope.valid.firstName == 'false'}">
                    <cite style="color: red; font-size:11px"><fmt:message key="firstNameError"/></cite>
                </c:if>
                <div class="input-group mt-3">
                    <input name="lastName" class="form-control" value="${sessionScope.user.lastName}"
                           placeholder="<fmt:message key="lName"/>">
                </div>
                <c:if test="${requestScope.valid.lastName == 'false'}">
                    <cite style="color: red; font-size:11px"><fmt:message key="lastNameError"/></cite>
                </c:if>
                <div class="input-group mt-3">
                    <input name="email" placeholder="<fmt:message key="email"/>" class="form-control"
                           value="${sessionScope.user.email}">
                </div>
                <c:if test="${requestScope.valid.email == 'false'}">
                    <cite style="color: red; font-size:11px"><fmt:message key="emailError"/></cite>
                </c:if>
                <div class="input-group mt-3">
                    <input name="phoneNumber" placeholder="<fmt:message key="phoneNumber"/>" class="form-control"
                           value="${sessionScope.user.phoneNumber}">
                </div>
                <c:if test="${requestScope.valid.phoneNumber == 'false'}">
                    <cite style="color: red; font-size:11px"><fmt:message key="phoneNumberError"/></cite>
                </c:if>
                <div class="d-flex justify-content-center">
                    <input class="btn btn-primary text-center mt-2" type="submit" value="<fmt:message key="saveChanges"/>">
                </div>
            </form>

        </div>
    </div>
</div>

<script>
    document.querySelector(".edit-btn").addEventListener("click", () => {
        document.querySelector(".edit-form").classList.toggle("edit-form-active")
    })

    document.querySelector(".edit-btn").addEventListener("click", () => {
        document.querySelector(".edit-btn").style.display = "none"
    })
</script>


</body>
</html>
