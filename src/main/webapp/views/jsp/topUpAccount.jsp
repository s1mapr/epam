<%--
  Created by IntelliJ IDEA.
  User: S1ma
  Date: 08.10.2022
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>
<head>
    <title><fmt:message key="topUpAccount"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/topUpForm.css">
</head>
<body>
<jsp:include page="/views/jsp/header.jsp"/>

<div class="container mt-5 mb-4">

    <div class="card-container">

        <div class="front">
            <div class="image">
                <img src="${pageContext.request.contextPath}/views/img/chip.png">
                <img src="${pageContext.request.contextPath}/views/img/visa.png">
            </div>
            <div class="card-number-box">################</div>
            <div class="flexbox">
                <div class="box">
                    <span>card holder</span>
                    <div class="card-holder-name">full name</div>
                </div>
                <div class="box">
                    <span>expires</span>
                    <div class="expiration">
                        <span class="exp-input">mm/yy</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="back">
            <div class="stripe"></div>
            <div class="box">
                <span>cvv</span>
                <div class="cvv-box"></div>
                <img src="${pageContext.request.contextPath}/views/img/visa.png" alt="">
            </div>
        </div>

    </div>
    <form action="${pageContext.request.contextPath}/user/topUp?id=${sessionScope.accountId}" method="post">
        <div class="inputBox">
            <input type="text" maxlength="16" class="card-number-input" name="cardNumber" placeholder="<fmt:message key="cardNumber"/>">
        </div>
        <c:if test="${requestScope.valid.cardNumber == 'false'}">
            <cite style="color: red; font-size:11px"><fmt:message key="cardNumberError"/></cite>
        </c:if>
        <div class="inputBox">
            <input type="text" class="card-holder-input" name="cardHolder" placeholder="<fmt:message key="cardHolder"/>">
        </div>
        <c:if test="${requestScope.valid.cardHolder == 'false'}">
            <cite style="color: red; font-size:11px"><fmt:message key="cardHolderError"/></cite>
        </c:if>
        <div class="flexbox">
            <div class="inputBox">
                <input class="expy-input" name="expiryDate" placeholder="<fmt:message key="expiry"/>">
                <br>
                <c:if test="${requestScope.valid.expirationDate == 'false'}">
                    <cite style="color: red; font-size:11px"><fmt:message key="expiryError"/></cite>
                </c:if>
            </div>

            <div class="inputBox">
                <input type="text" maxlength="4" class="cvv-input" name="cvv" placeholder="<fmt:message key="cvv"/>">
                <br>
                <c:if test="${requestScope.valid.cvv == 'false'}">
                    <cite style="color: red; font-size:11px"><fmt:message key="cvvError"/></cite>
                </c:if>
            </div>
            <div class="inputBox">
                <input type="text" name="amount" placeholder="<fmt:message key="paymentAmount"/>">
                <br>
                <c:if test="${requestScope.valid.paymentAmount == 'false'}">
                    <cite style="color: red; font-size:11px"><fmt:message key="amountError"/></cite>
                </c:if>
            </div>
        </div>
        <input type="submit" value="<fmt:message key="topUp"/>" class="submit-btn">
    </form>

</div>


<script>

    document.querySelector('.card-number-input').oninput = () => {
        document.querySelector('.card-number-box').innerText = document.querySelector('.card-number-input').value;
    }

    document.querySelector('.card-holder-input').oninput = () => {
        document.querySelector('.card-holder-name').innerText = document.querySelector('.card-holder-input').value;
    }

    document.querySelector('.expy-input').oninput = () => {
        document.querySelector('.exp-input').innerText = document.querySelector('.expy-input').value;
    }

    document.querySelector('.cvv-input').onmouseenter = () => {
        document.querySelector('.front').style.transform = 'perspective(1000px) rotateY(-180deg)';
        document.querySelector('.back').style.transform = 'perspective(1000px) rotateY(0deg)';
    }

    document.querySelector('.cvv-input').onmouseleave = () => {
        document.querySelector('.front').style.transform = 'perspective(1000px) rotateY(0deg)';
        document.querySelector('.back').style.transform = 'perspective(1000px) rotateY(180deg)';
    }

    document.querySelector('.cvv-input').oninput = () => {
        document.querySelector('.cvv-box').innerText = document.querySelector('.cvv-input').value;
    }

</script>

</body>
</html>
