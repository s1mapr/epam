<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 01.10.2022
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/views/css/selectStyle.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="container-fluid vh-50" style="margin-top:50px">
    <div class="rounded d-flex justify-content-center">
        <div class="col-md-4 col-sm-12 shadow-lg px-5 pt-3 bg-light">
            <div class="text-center">
                <h3 class="text-primary">Оплата штрафів</h3>
            </div>

            <form action="${pageContext.request.contextPath}/user/finesPayment" method="post">
                <div class="p-4">
                    <div class="input-group mt-3">
                        <input name="firstName" class="form-control" placeholder="Ім'я">
                    </div>
                    <c:if test="${requestScope.valid.firstName == 'false'}">
                        <div><cite style="color: red">first name error</cite></div>
                    </c:if>

                    <div class="input-group mt-3">
                        <input name="lastName" class="form-control" placeholder="Прізвище">
                    </div>
                    <c:if test="${requestScope.valid.lastName == 'false'}">
                        <div><cite style="color: red">last name error</cite></div>
                    </c:if>

                    <div class="input-group mt-3">
                        <input name="patronymic" class="form-control" placeholder="По-батькові">
                    </div>
                    <c:if test="${requestScope.valid.patronymic == 'false'}">
                        <div><cite style="color: red">patronymic error</cite></div>
                    </c:if>

                    <div class="input-group mt-3">
                        <input name="number" class="form-control" placeholder="Номер штрафа">
                    </div>
                    <c:if test="${requestScope.valid.fineNumber == 'false'}">
                        <div><cite style="color: red">fine number error</cite></div>
                    </c:if>

                    <div class="input-group mt-3">
                        <input name="amount" class="form-control" placeholder="Сума">
                    </div>
                    <c:if test="${requestScope.valid.paymentAmount == 'false'}">
                        <div><cite style="color: red">payment amount error</cite></div>
                    </c:if>

                    <div class="mt-3">
                        <c:choose>
                            <c:when test="${sessionScope.accounts.size() == 0}">
                                <select class="form-select" name="accountId" aria-label="Default select example">

                                    <option value="none" selected>У вас немає рахунків</option>
                                </select>
                            </c:when>
                            <c:when test="${sessionScope.accounts.size() > 0 && sessionScope.accounts.size() <= 5}">
                                <select class="form-select" class="form-control"
                                        onfocus='this.size=${sessionScope.accounts.size()+1};' onblur='this.size=1;'
                                        onchange='this.size=1; this.blur();' name="accountId"
                                        aria-label="Default select example">

                                    <option value="${sessionScope.accounts.get(0).id}"
                                            selected>${sessionScope.accounts.get(0).name}</option>
                                    <c:forEach var="names" items="${sessionScope.accounts}">
                                        <c:if test="${names.status == 'unblocked'}">
                                            <option value="${names.id}">${names.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:when test="${sessionScope.accounts.size() > 5}">
                                <select class="form-select" class="form-control" onfocus='this.size=5;'
                                        onblur='this.size=1;' onchange='this.size=1; this.blur();' name="accountId"
                                        aria-label="Default select example">

                                    <option value="${sessionScope.accounts.get(0).id}"
                                            selected>${sessionScope.accounts.get(0).name}</option>
                                    <c:forEach var="names" items="${sessionScope.accounts}">
                                        <c:if test="${names.status == 'unblocked'}">
                                            <option value="${names.id}">${names.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>

                            </c:when>

                        </c:choose>
                    </div>
                    <div class="text-center"><cite  style="color: red">${requestScope.notEnoughMoney}</cite></div>
                    <div class="form-row text-center">
                        <button class="btn btn-primary text-center mt-4" type="submit">
                            Сплатити
                        </button>
                    </div>
                </div>
            </form>


        </div>
    </div>
</div>

</body>
</html>
