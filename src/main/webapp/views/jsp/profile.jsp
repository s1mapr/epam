<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 28.09.2022
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="profile"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .mainDiv {
            display: flex;
        }

        .firstDiv {
            width: 1050px;
            height: 550px;

        }

        .secDiv {
            width: 550px;
            height: 550px;

        }

        .avatar {
            width: 150px;
            height: 150px;
            border-radius: 100%;
            box-shadow: 0 10px 20px rgba(134, 134, 134, 0.35);

        }

        .div1 {
            display: inline-block;
            margin-left: 450px;
        }

        .div2 {
            display: inline-block;
            margin-left: 300px;

        }
    </style>
    <u:arrow arrow="arrow"/>
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="mainDiv">
    <div class=" mt-5 mx-3 firstDiv">
        <table class="table border border-primary">
            <thead class="table bg-primary">
            <tr>
                <th scope="col">
                    <a class="text-white text-decoration-none"><fmt:message key="accountName"/></a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortName&type=ASC&page=1">&#129047;</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortName&type=DESC&page=1">&#129045;</a>
                </th>
                <th scope="col">
                    <a class="text-white text-decoration-none"><fmt:message key="cardNumber"/></a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortCardNumber&type=ASC&page=1">&#129047;</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortCardNumber&type=DESC&page=1">&#129045;</a>
                </th>
                <th scope="col">
                    <a class="text-white text-decoration-none"><fmt:message key="balance"/></a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortAmount&type=ASC&page=1">&#129047;</a>
                    <a class="text-white text-decoration-none arrow"
                       href="${pageContext.request.contextPath}/user/profile?sortAction=sortAmount&type=DESC&page=1">&#129045;</a>
                </th>
                <th scope="col">
                    <a class="text-white text-decoration-none"><fmt:message key="status"/></a>
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
                    <td><a class="btn btn-primary" href="${pageContext.request.contextPath}/user/topUp?id=${names.id}"><fmt:message key="topUpAccount"/></a></td>
                    <c:choose>
                        <c:when test="${names.status == 'unblocked'}">
                            <td><a class="btn btn-primary"
                                   href="${pageContext.request.contextPath}/user/profile?action=block&id=${names.id}"><fmt:message key="block"/></a>
                            </td>
                        </c:when>
                        <c:when test="${names.status == 'blocked'}">
                            <td><a class="btn btn-primary"
                                   href="${pageContext.request.contextPath}/user/profile?action=unblock&id=${names.id}"><fmt:message key="unblock"/></a>
                            </td>
                        </c:when>
                        <c:when test="${names.status == 'pending'}">
                            <td>
                                <fmt:message key="reqMsg"/>
                            </td>
                        </c:when>
                    </c:choose>
                </tr>
            </c:forEach>

            </tbody>
        </table>
        <div class="d-flex">
            <div class="justify-content-center div1">
                <c:choose>
                    <c:when test="${sessionScope.profPage == 1 && requestScope.pagesCount >1}">
                        <a class="btn btn-primary mx-1  mb-2"
                           href="${pageContext.request.contextPath}/user/profile?page=${sessionScope.profPage+1}">&#129046</a>
                    </c:when>
                    <c:when test="${sessionScope.profPage == requestScope.pagesCount && sessionScope.profPage != 1}">
                        <a class="btn btn-primary mx-1  mb-2"
                           href="${pageContext.request.contextPath}/user/profile?page=${sessionScope.profPage-1}">&#129044</a>
                    </c:when>
                    <c:when test="${sessionScope.profPage > 1 && sessionScope.profPage < requestScope.pagesCount}">
                        <a class="btn btn-primary mx-1  mb-2"
                           href="${pageContext.request.contextPath}/user/profile?page=${sessionScope.profPage-1}">&#129044</a>
                        <a class="btn btn-primary mx-1 mb-2 "
                           href="${pageContext.request.contextPath}/user/profile?page=${sessionScope.profPage+1}">&#129046</a>
                    </c:when>
                </c:choose>
            </div>
            <div class="justify-content-end div2 ">
                <a class="btn btn-primary mb-2 " href="${pageContext.request.contextPath}/user/addNewAccount"><fmt:message key="addNewAcc"/></a>
            </div>
        </div>
    </div>


    <div class="shadow-lg mx-3 mt-5 secDiv px-4">
        <div class="d-flex justify-content-center my-5">
            <img src="${sessionScope.user.avatarURL}" class="avatar">
        </div>
        <table class="table border-primary">
            <tbody>
            <tr >
                <td><fmt:message key="fName"/>:</td>
                <td>${sessionScope.user.firstName}</td>
            </tr>
            <tr>
                <td><fmt:message key="lName"/>:</td>
                <td>${sessionScope.user.lastName}</td>
            </tr>
            <tr>
                <td><fmt:message key="email"/>:</td>
                <td> ${sessionScope.user.email}</td>
            </tr>
            <tr>
                <td><fmt:message key="phoneNumber"/>:</td>
                <td> ${sessionScope.user.phoneNumber}</td>
            </tr>
            <tr>
                <td><fmt:message key="paymentsCount"/>:</td>
                <td> ${sessionScope.user.paymentsCount}</td>
            </tr>
            <tr>
                <td><fmt:message key="accountsCount"/>:</td>
                <td> ${sessionScope.user.accountsCount}</td>
            </tr>
            </tbody>
        </table>
        <div class="d-flex justify-content-center">
            <a class="btn btn-primary mb-2"
               href="${pageContext.request.contextPath}/user/editProfile"><fmt:message key="editProfile"/></a>
        </div>
    </div>
</div>
</body>
</html>
