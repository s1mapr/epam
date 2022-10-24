<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 01.10.2022
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="utilitiesPayment"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/views/css/selectStyle.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>
<div class="container-fluid vh-50" style="margin-top:50px">
    <div class="rounded d-flex justify-content-center">
        <div class="col-md-4 col-sm-12 shadow-lg px-5 pt-3 bg-light">
            <div class="text-center">
                <h3 class="text-primary"><fmt:message key="utilitiesPayment"/></h3>
            </div>

            <form action="${pageContext.request.contextPath}/user/utilitiesPayment" method="post">
                <div class="p-2">

                    <div class="input-group mt-2">
                        <input name="meter_w" class="form-control" placeholder="<fmt:message key="meterW"/>">
                    </div>
                    <c:if test="${requestScope.valid.meterW == 'false'}">
                        <div><cite style="color: red; font-size: 11px"><fmt:message key="meterError"/></cite></div>
                    </c:if>

                    <div class="input-group mt-2">
                        <input name="amount_w" class="form-control" placeholder="<fmt:message key="paymentAmount"/>">
                    </div>
                    <c:if test="${requestScope.valid.amountW == 'false'}">
                        <div><cite style="color: red; font-size: 11px"><fmt:message key="amountError"/></cite></div>
                    </c:if>


                    <div class="input-group mt-2">
                        <input name="meter_e" class="form-control" placeholder="<fmt:message key="meterE"/>">
                    </div>
                    <c:if test="${requestScope.valid.meterE == 'false'}">
                        <div><cite style="color: red; font-size: 11px"><fmt:message key="meterError"/></cite></div>
                    </c:if>

                    <div class="input-group mt-2">
                        <input name="amount_e" class="form-control" placeholder="<fmt:message key="paymentAmount"/>">
                    </div>
                    <c:if test="${requestScope.valid.amountE == 'false'}">
                        <div><cite style="color: red; font-size: 11px"><fmt:message key="amountError"/></cite></div>
                    </c:if>

                    <div class="input-group mt-2">
                        <input name="meter_g" class="form-control" placeholder="<fmt:message key="meterG"/>">
                    </div>
                    <c:if test="${requestScope.valid.meterG == 'false'}">
                        <div><cite style="color: red; font-size: 11px"><fmt:message key="meterError"/></cite></div>
                    </c:if>

                    <div class="input-group mt-2">
                        <input name="amount_g" class="form-control" placeholder="<fmt:message key="paymentAmount"/>">
                    </div>
                    <c:if test="${requestScope.valid.amountG == 'false'}">
                        <div><cite style="color: red; font-size: 11px"><fmt:message key="amountError"/></cite></div>
                    </c:if>

                    <div class="mt-2">
                        <c:choose>
                            <c:when test="${sessionScope.accLength == 0}">
                                <select class="form-select" name="accountId" aria-label="Default select example">

                                    <option value="none" selected><fmt:message key="noAccounts"/></option>
                                </select>
                            </c:when>
                            <c:when test="${sessionScope.accLength > 0 && sessionScope.accLength <= 5}">
                                <select class="form-select" class="form-control"
                                        onfocus='this.size=${sessionScope.accLength+1};' onblur='this.size=1;'
                                        onchange='this.size=1; this.blur();' name="accountId"
                                        aria-label="Default select example">

                                    <c:forEach var="names" items="${sessionScope.accounts}">
                                        <c:if test="${names.status == 'unblocked'}">
                                            <option value="${names.id}" selected>${names.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:when test="${sessionScope.accLength > 5}">
                                <select class="form-select" class="form-control" onfocus='this.size=5;'
                                        onblur='this.size=1;' onchange='this.size=1; this.blur();' name="accountId"
                                        aria-label="Default select example">

                                    <c:forEach var="names" items="${sessionScope.accounts}">
                                        <c:if test="${names.status == 'unblocked'}">
                                            <option value="${names.id}" selected>${names.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>

                            </c:when>

                        </c:choose>
                    </div>
                    <c:if test="${requestScope.notEnoughMoney != null}">
                        <div><cite style="color: red; font-size:11px"><fmt:message key="notEnoughMoney"/></cite></div>
                    </c:if>
                    <c:if test="${sessionScope.accLength >0}">

                        <div class="form-row text-center">
                            <button class="btn btn-primary text-center mt-3" type="submit">
                                <fmt:message key="pay"/>
                            </button>
                        </div>
                    </c:if>
                </div>
            </form>


        </div>
    </div>
</div>
</body>
</html>
