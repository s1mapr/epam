<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="arrow" required="true" rtexprvalue="true" type="java.lang.String" %>
<c:if test="${arrow.equals('arrow')}">
    <style type="text/css">
        .arrow {
            margin-left: -15px;
        }
    </style>
</c:if>