<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/bootstrap.jsp" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <title><fmt:message bundle="${loc}" key="local.phrase.search_result"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navigation.jsp" flush="true"/>
    <br>
    <h2 align="left"><fmt:message bundle="${loc}" key="local.phrase.search_result"/>: </h2>
    <table class="table table-striped table-hover">
        <thead class="thead-light">
        <tr>
            <th>№</th>
            <th><fmt:message bundle="${loc}" key="local.phrase.title"/></th>
            <th><fmt:message bundle="${loc}" key="local.phrase.authors"/></th>
            <th><fmt:message bundle="${loc}" key="local.phrase.year_up"/></th>
            <th><fmt:message bundle="${loc}" key="local.phrase.lang"/></th>
            <th><fmt:message bundle="${loc}" key="local.phrase.pages"/></th>
            <th><fmt:message bundle="${loc}" key="local.phrase.available"/></th>
            <c:if test="${main_user.role.toString()!=\"READER\"}">
                <th><fmt:message bundle="${loc}" key="local.phrase.total"/></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${books}" var="book">
            <c:set var="book" value="${book}" scope="request"/>
            <tr>
                <td><c:out value="${book.getId()}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/Controller?command=take_book&book_id=${book.getId()}">
                        <c:out value="${book.getTitle()}"/></a>
                </td>
                <td>
                    <c:forEach items="${book.getAuthors()}" var="author">
                    <c:out value="${author.getSurname()} ${author.getName()} ${author.getPatronymic()} "/>
                </td>
                </c:forEach>
                </td>
                <td><c:out value="${book.getYear()}"/></td>
                <td><c:out value="${book.getLang()}"/></td>
                <td><c:out value="${book.getNumberOfPages()}"/></td>
                <td><c:out value="${book.getAvailableBooks()}"/></td>
                <c:if test="${main_user.role.toString()!=\"READER\"}">
                    <td><c:out value="${book.getQuantity()}"/></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>