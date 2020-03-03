<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/bootstrap.jsp" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <title><fmt:message bundle="${loc}" key="local.message.page_not_found"/></title>
</head>
<body>
<div class="container">
    <div class="jumbotron bg-light-gray">
        <p><img src="${pageContext.request.contextPath}/img/404.png" alt="404" class="center" style="width:250px;"></p>
        <p class="center"><b>
            <fmt:message bundle="${loc}" key="local.message.page_not_found"/>!
        </b></p>
    </div>
</div>
</body>
</html>
