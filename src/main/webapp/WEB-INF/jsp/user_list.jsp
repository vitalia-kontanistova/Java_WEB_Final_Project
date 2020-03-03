<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/bootstrap.jsp" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <title><fmt:message bundle="${loc}" key="local.phrase.readers_list"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navigation.jsp" flush="true"/>
    <br>
    <form class="form-inline" method="post" action="Controller" accept-charset="UTF-8">
        <c:if test="${main_user.role!=\"READER\"}">
            <form class="form-inline" action="Controller" method="post">
                <a href="${pageContext.request.contextPath}/add_user">
                    <input type="button" class="btn btn-secondary" disabled
                           value="<fmt:message bundle="${loc}" key="local.phrase.add_user"/>"/>
                </a>
            </form>
        </c:if>
    </form>
    <div>
        <h3><fmt:message bundle="${loc}" key="local.phrase.readers"/>:</h3>
    </div>
    <table class="table table-striped table-hover">
        <thead class="thead-light">
        <tr>
            <th>№</th>
            <th><fmt:message bundle="${loc}" key="local.phrase.full_name"/></th>
            <th><fmt:message bundle="${loc}" key="local.phrase.birth_date"/></th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <c:if test="${user.getRole().toString()==\"READER\"}">
                <tbody>
                <tr>
                    <td><c:out value="${user.getId()}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/Controller?command=take_user_card&user_id=${user.getId()}">
                            <c:out value="${user.getSurname()} ${user.getName()} ${user.getPatronymic()}"/>
                        </a></td>
                    <td><c:out value="${user.getBirthday()}"/></td>
                </tr>
                </tbody>
            </c:if>
        </c:forEach>
    </table>
    <c:if test="${main_user.role.toString()==\"ADMIN\"}">
        <div>
            <h3><fmt:message bundle="${loc}" key="local.phrase.librarians"/>:</h3>
        </div>
        <table class="table table-striped table-hover">
            <thead class="thead-light">
            <tr>
                <th>№</th>
                <th><fmt:message bundle="${loc}" key="local.phrase.full_name"/></th>
                <th><fmt:message bundle="${loc}" key="local.phrase.birth_date"/></th>
            </tr>
            </thead>
            <c:forEach items="${users}" var="user">
                <c:if test="${user.getRole().toString()==\"LIBRARIAN\"}">
                    <tbody>
                    <tr>
                        <td><c:out value="${user.getId()}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/Controller?command=take_user_card&user_id=${user.getId()}">
                                <c:out value="${user.getSurname()} ${user.getName()} ${user.getPatronymic()}"/>
                            </a></td>
                        <td><c:out value="${user.getBirthday()}"/></td>
                    </tr>
                    </tbody>
                </c:if>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>