<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/bootstrap.jsp" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <title><fmt:message bundle="${loc}" key="local.phrase.book_info"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navigation.jsp" flush="true"/>
    <br>
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
        <tr>
            <td><c:out value="${book.id}"/></td>
            <td><c:out value="${book.title}"/></td>
            <td><c:forEach items="${book.authors}" var="author">
                <c:out value="${author.getSurname()} ${author.getName()} ${author.getPatronymic()} "/>
            </c:forEach></td>
            <td><c:out value="${book.year}"/></td>
            <td><c:out value="${book.lang}"/></td>
            <td><c:out value="${book.numberOfPages}"/></td>
            <td><c:out value="${book.availableBooks}"/></td>
            <c:if test="${main_user.role.toString()!=\"READER\"}">
                <td><c:out value="${book.quantity}"/></td>
            </c:if>
        </tr>
        <tr>
            <td colspan="8"><c:out value="${book.annotation}"/></td>
        </tr>
        </tbody>
    </table>
    <br>
    <div class="container">
        <c:if test="${main_user.role.toString()==\"READER\"}">
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="add_card_note"/>
                <input type="hidden" name="destination" value="order"/>
                <input type="hidden" name="book_id" value="${book.id}"/>
                <c:if test="${book.availableBooks > 0}">
                    <input type="submit" class="btn btn-secondary"
                           value="<fmt:message bundle="${loc}" key="local.phrase.order"/>"/>
                </c:if>
                <c:if test="${book.availableBooks == 0}">
                    <input type="submit" class="btn btn-secondary" disabled
                           value="<fmt:message bundle="${loc}" key="local.phrase.order"/>"/>
                </c:if>
            </form>
        </c:if>
        <c:if test="${main_user.role.toString()!=\"READER\"}">
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="delete_book"/>
                <input type="hidden" name="book_id" value="${book.id}"/>
                <c:if test="${book.availableBooks > 0}">
                    <input type="submit" class="btn btn-secondary"
                           value="<fmt:message bundle="${loc}" key="local.phrase.delete"/>"/>
                </c:if>
                <c:if test="${book.availableBooks == 0}">
                    <input type="submit" disabled class="btn btn-secondary"
                           value="<fmt:message bundle="${loc}" key="local.phrase.delete"/>"/>
                </c:if>
            </form>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="update_book"/>
                <input type="hidden" name="book_id" value="${book.id}"/>
                <input type="submit" disabled class="btn btn-secondary"
                       value="<fmt:message bundle="${loc}" key="local.phrase.update"/>"/>
            </form>
        </c:if>
    </div>
</div>
</body>
</html>