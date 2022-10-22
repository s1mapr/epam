<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 02.10.2022
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
  <title><fmt:message key="accounts"/></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .arrow {
      margin-left: -15px;
    }
  </style>
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="px-2 pt-4 ">
  <table class="table border border-primary">
    <thead class="table bg-primary">
    <tr>
      <th scope="col">
        <a class="text-white text-decoration-none "><fmt:message key="accountName"/></a>
        <a class="text-white text-decoration-none arrow"
           href="${pageContext.request.contextPath}/adm/accounts?sortAction=sortAccountName&type=ASC&page=1">&#129047;</a>
        <a class="text-white text-decoration-none arrow"
           href="${pageContext.request.contextPath}/adm/accounts?sortAction=sortAccountName&type=DESC&page=1">&#129045;</a>
      </th>
      <th scope="col">
        <a class="text-white text-decoration-none"><fmt:message key="login"/></a>
        <a class="text-white text-decoration-none arrow"
           href="${pageContext.request.contextPath}/adm/accounts?sortAction=sortLogin&type=ASC&page=1">&#129047;</a>
        <a class="text-white text-decoration-none arrow"
           href="${pageContext.request.contextPath}/adm/accounts?sortAction=sortLogin&type=DESC&page=1">&#129045;</a>
      </th>

      <th scope="col">
        <a class="text-white text-decoration-none"><fmt:message key="fName"/></a>
        <a class="text-white text-decoration-none arrow"
           href="${pageContext.request.contextPath}/adm/accounts?sortAction=sortName&type=ASC&page=1">&#129047;</a>
        <a class="text-white text-decoration-none arrow"
           href="${pageContext.request.contextPath}/adm/accounts?sortAction=sortName&type=DESC&page=1">&#129045;</a>
      </th>

      <th scope="col">
        <a class="text-white text-decoration-none"><fmt:message key="lName"/></a>
        <a class="text-white text-decoration-none arrow"
           href="${pageContext.request.contextPath}/adm/accounts?sortAction=sortLastName&type=ASC&page=1">&#129047;</a>
        <a class="text-white text-decoration-none arrow"
           href="${pageContext.request.contextPath}/adm/accounts?sortAction=sortLastName&type=DESC&page=1">&#129045;</a>
      </th>

      <th scope="col">
        <a class="text-white text-decoration-none"><fmt:message key="paymentsCount"/></a>
      </th>

      <th scope="col">
        <a class="text-white text-decoration-none"><fmt:message key="status"/></a>
        <a class="text-white text-decoration-none arrow"
           href="${pageContext.request.contextPath}/adm/accounts?sortAction=sortStatus&type=ASC&page=1">&#129047;</a>
        <a class="text-white text-decoration-none arrow"
           href="${pageContext.request.contextPath}/adm/accounts?sortAction=sortStatus&type=ASC&page=1">&#129045;</a>
      </th>

      <th scope="col">
      </th>

      <th scope="col">
      </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="accounts" items="${requestScope.list}">
      <tr>
        <td>${accounts.name}</td>
        <td>${accounts.userLogin}</td>
        <td>${accounts.userFirstName}</td>
        <td>${accounts.userLastName}</td>
        <td>${accounts.paymentsCount}</td>
        <td>${accounts.status}</td>
        <td><a class="btn btn-primary mx-1 "
               href="${pageContext.request.contextPath}/adm/accounts?action=block&id=${accounts.id}"><fmt:message key="block"/></a></td>
        <td><a class="btn btn-primary mx-1 "
               href="${pageContext.request.contextPath}/adm/accounts?action=unblock&id=${accounts.id}"><fmt:message key="unblock"/></a></td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<div class="d-flex justify-content-center">
  <c:choose>
    <c:when test="${sessionScope.accPage == 1 && requestScope.pagesCount >1}">
      <a class="btn btn-primary mx-1 mb-1"
         href="${pageContext.request.contextPath}/adm/accounts?page=${sessionScope.accPage+1}" >&#129046;

      </a>
    </c:when>
    <c:when test="${sessionScope.accPage == requestScope.pagesCount && sessionScope.accPage != 1}">
      <a class="btn btn-primary mx-1 mb-1"
         href="${pageContext.request.contextPath}/adm/accounts?page=${sessionScope.accPage-1}">&#129044;
      </a>
    </c:when>
    <c:when test="${sessionScope.accPage > 1 && sessionScope.accPage < requestScope.pagesCount}">
      <a class="btn btn-primary mx-1 mb-1"
         href="${pageContext.request.contextPath}/adm/accounts?page=${sessionScope.accPage-1}">&#129044;
      </a>
      <a class="btn btn-primary mx-1 mb-1"
         href="${pageContext.request.contextPath}/adm/accounts?page=${sessionScope.accPage+1}">&#129046;
      </a>
    </c:when>
  </c:choose>
</div>
</body>
</html>
