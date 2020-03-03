<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/bootstrap.jsp" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <title><fmt:message bundle="${loc}" key="local.phrase.user_profile"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navigation.jsp" flush="true"/>
    <br>
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="update_password">
        <div class="jumbotron bg-light-gray">
            <h3><fmt:message bundle="${loc}" key="local.phrase.user"/> №<c:out value="${main_user.id}"/></h3>
            <p><fmt:message bundle="${loc}" key="local.phrase.surname"/>: <c:out value="${main_user.surname}"/></p>
            <p><fmt:message bundle="${loc}" key="local.phrase.name"/>: <c:out value="${main_user.name}"/></p>
            <p><fmt:message bundle="${loc}" key="local.phrase.patronymic"/>: <c:out
                    value="${main_user.patronymic}"/></p>
            <p><fmt:message bundle="${loc}" key="local.phrase.birth_date"/>: <c:out value="${main_user.birthday}"/></p>
            <p><fmt:message bundle="${loc}" key="local.phrase.phone"/>: <c:out value="${main_user.phone}"/></p>
            <p>Email: <c:out value="${email}"/></p>
            <p><fmt:message bundle="${loc}" key="local.phrase.role"/>: <c:out value="${main_user.role}"/></p>
            <p><fmt:message bundle="${loc}" key="local.phrase.change_the_password"/>:
                <input type="password" pattern=".{9,}" name="password"/>
                <input type="submit" class="btn btn-secondary btn-sm"
                       value="<fmt:message bundle="${loc}" key="local.phrase.update"/>"/>
                <c:if test="${update_pass_message != null}">
            <p class="text-danger"><c:out value="${update_pass_message}"/></p>
            <c:remove var="update_pass_message" scope="session"/>
            </c:if>
        </div>
    </form>
</div>
</body>
</html>