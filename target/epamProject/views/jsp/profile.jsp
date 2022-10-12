<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .mainDiv {
            display: flex;
        }

        .firstDiv {
            width: 1050px;
            height: 500px;

        }

        .secDiv {
            width: 550px;
            height: 550px;

        }

        .arrow {
            margin-left: -15px;
        }
    </style>
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="mainDiv">
    <div class="pt-5 mt-5 mx-3 firstDiv">
        <table class="table border border-primary">
            <thead class="table bg-primary">
            <tr>
                <th scope="col">
                    <a class="text-white text-decoration-none">Назва</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortName&type=ASC&page=1">&#129047;</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortName&type=DESC&page=1">&#129045;</a>
                </th>
                <th scope="col">
                    <a class="text-white text-decoration-none">Номер картки</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortCardNumber&type=ASC&page=1">&#129047;</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortCardNumber&type=DESC&page=1">&#129045;</a>
                </th>
                <th scope="col">
                    <a class="text-white text-decoration-none">Баланс</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortAmount&type=ASC&page=1">&#129047;</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortAmount&type=DESC&page=1">&#129045;</a>
                </th>
                <th scope="col">
                    <a class="text-white text-decoration-none">Статус</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortStatus&type=ASC&page=1">&#129047;</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortStatus&type=DESC&page=1">&#129045;</a>
                </th>
                <th scope="col">
                </th>
                <th scope="col">
                </th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="names" items="${requestScope.accountsPag}">
                <tr>

                    <td>${names.name}</td>
                    <td>${names.cardNumber}</td>
                    <td>${names.amount}</td>
                    <td>${names.status}</td>
                    <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/user/topUp?id=${names.id}">Пополнить
                        счет</a></td>
                    <c:choose>
                        <c:when test="${names.status == 'unblocked'}">
                            <td><a class="btn btn-primary"
                                   href="${pageContext.request.contextPath}/user/profile?action=block&id=${names.id}">Заблокувати</a>
                            </td>
                        </c:when>
                        <c:when test="${names.status == 'blocked'}">
                            <td><a class="btn btn-primary"
                                   href="${pageContext.request.contextPath}/user/profile?action=unblock&id=${names.id}">Розблокувати</a>
                            </td>
                        </c:when>
                    </c:choose>
                </tr>
            </c:forEach>

            </tbody>
        </table>
        <div class="d-flex justify-content-end">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/user/addNewAccount">add new account</a>
        </div>
        <div class="d-flex justify-content-center">
            <c:choose>
                <c:when test="${sessionScope.profPage == 1 && requestScope.pagesCount >1}">
                    <a class="btn btn-primary mx-1 mb-1"
                       href="${pageContext.request.contextPath}/user/profile?page=${sessionScope.profPage+1}">&#129046</a>
                </c:when>
                <c:when test="${sessionScope.profPage == requestScope.pagesCount && sessionScope.profPage != 1}">
                    <a class="btn btn-primary mx-1 mb-1"
                       href="${pageContext.request.contextPath}/user/profile?page=${sessionScope.profPage-1}">&#129044</a>
                </c:when>
                <c:when test="${sessionScope.profPage > 1 && sessionScope.profPage < requestScope.pagesCount}">
                    <a class="btn btn-primary mx-1 mb-1"
                       href="${pageContext.request.contextPath}/user/profile?page=${sessionScope.profPage-1}">&#129044</a>
                    <a class="btn btn-primary mx-1 mb-1"
                       href="${pageContext.request.contextPath}/user/profile?page=${sessionScope.profPage+1}">&#129046</a>
                </c:when>
            </c:choose>
        </div>

    </div>
    <div class="border border-2 border-primary mx-3 mt-5 secDiv">
        <table class="table border-primary">
            <tbody>
            <tr>
                <td>First name:</td>
                <td>${sessionScope.user.firstName}</td>
            </tr>
            <tr>
                <td>Last name:</td>
                <td>${sessionScope.user.lastName}</td>
            </tr>
            <tr>
                <td>Email:</td>
                <td> ${sessionScope.user.email}</td>
            </tr>
            <tr>
                <td>Phone number:</td>
                <td> ${sessionScope.user.phoneNumber}</td>
            </tr>
            <tr>
                <td>Кількість платежів:</td>
                <td> ${sessionScope.user.paymentsCount}</td>
            </tr>
            <tr>
                <td>Кількість рахунків:</td>
                <td> ${sessionScope.user.accountsCount}</td>
            </tr>
            </tbody>
        </table>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/user/editProfile">Edit profile</a>

    </div>
</div>
</body>
</html>
