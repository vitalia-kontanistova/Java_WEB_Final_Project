<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/WEB-INF/jsp/bootstrap.jsp" flush="true"/>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <title><fmt:message bundle="${loc}" key="local.phrase.library"/></title>
</head>
<body>
<div class="container login">
    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6" align="center">
            <img src="${pageContext.request.contextPath}/img/Logo_200.png" class="crop" alt="Logo">
            <h1 class="logo">Libalibre.by</h1>
        </div>
        <div class="col-sm-3"></div>
    </div>
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="authorisation"/>
        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4" align="center">
                <label>
                    <input class="form-control" required type="email" size="30" placeholder="E-mail"
                           pattern="[\S]{2,}@[a-z]{3,}\.[a-z]{2,}" name="email">
                </label>
            </div>
            <div class="col-sm-4"></div>
        </div>
        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4" align="center">
                <label>
                    <input class="form-control" required type="password" size="30"
                           placeholder="<fmt:message bundle="${loc}" key="local.phrase.password"/>" pattern=".{9,}"
                           name="password">
                </label></div>
            <div class="col-sm-4"></div>
        </div>
        <c:if test="${authorisation_message!=null}">
            <div class="row">
                <div class="col-sm-4"></div>
                <div class="col-sm-4" align="center">
                    <p class="text-danger">
                        <c:out value="${authorisation_message}"/>
                    </p>
                    <c:remove var="authorisation_message" scope="session"/>
                </div>
                <div class="col-sm-4"></div>
            </div>
        </c:if>
        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4" align="center">
                <input type="submit" class="btn btn-warning"
                       value="<fmt:message bundle="${loc}" key="local.phrase.enter"/>">
            </div>
            <div class="col-sm-4"></div>
        </div>
    </form>
    <form method="post" action="registration.jsp">
        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4" align="center">
                <input type="submit" class="btn btn-secondary btn-sm"
                       value="<fmt:message bundle="${loc}" key="local.phrase.register"/>">
            </div>
            <div class="col-sm-4"></div>
        </div>
    </form>
</div>
</body>
</html>