<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/bootstrap.jsp" %>


<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <title><fmt:message bundle="${loc}" key="local.phrase.error"/></title>
</head>
<body>
<div class="container">
    <div class="jumbotron bg-light-gray">
        <p>
            <img src="${pageContext.request.contextPath}/img/error.png" alt="error" class="center" style="width:250px;">
        <p class="center">
        <p class="center"><b>
            <fmt:message bundle="${loc}" key="local.message.error"/>
        </b></p>
        <p class="center"><b>
            <fmt:message bundle="${loc}" key="local.message.try_again"/>
        </b></p>
    </div>
</div>
</body>
</html>
