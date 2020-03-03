<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/bootstrap.jsp" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <title><fmt:message bundle="${loc}" key="local.phrase.card"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navigation.jsp" flush="true"/>
    <br>
    <c:if test="${main_user.role.toString()!=\"READER\"}">
        <div class="jumbotron bg-light-gray">
            <h3><fmt:message bundle="${loc}" key="local.phrase.user"/> № <c:out value="${user.id}"/></h3>
            <p><c:out value="${user.surname} ${user.name} ${user.patronymic}"/></p>
            <p><fmt:message bundle="${loc}" key="local.phrase.birth_date"/>:
                <c:out value="${user.birthday}"/></p>
            <p><fmt:message bundle="${loc}" key="local.phrase.phone"/>:
                <c:out value="${user.phone}"/></p>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="update_user"/>
                <input type="hidden" name="user_id" value="${user.id}"/>
                <p><input type="submit" class="btn btn-secondary" disabled
                          value="<fmt:message bundle="${loc}" key="local.phrase.update_data"/>"/></p>
            </form>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="delete_user"/>
                <input type="hidden" name="user_id" value="${user.id}"/>
                <p><input type="submit" class="btn btn-secondary" disabled
                          value="<fmt:message bundle="${loc}" key="local.phrase.delete_user"/>"/></p>
            </form>
        </div>
    </c:if>
    <c:if test="${user.role.toString()==\"READER\"}">
        <form class="form-inline" action="Controller" method="post">
            <a href="${pageContext.request.contextPath}/add_card_note">
                <input type="button" disabled class="btn btn-secondary mb-2 mr-sm-2"
                       value="<fmt:message bundle="${loc}" key="local.phrase.add_entry"/>"/>
            </a>
        </form>
        <div class="row">
            <div class="col-sm-6">
                <h4 class="logo"><fmt:message bundle="${loc}" key="local.phrase.entries_in_the_card"/>:</h4>
            </div>
            <div class="col-sm-6">
            </div>
        </div>
        <table class="table table-striped table-hover">
            <thead class="thead-light">
            <tr>
                <th>№</th>
                <th width="124"><fmt:message bundle="${loc}" key="local.phrase.gave_away"/></th>
                <th width="124"><fmt:message bundle="${loc}" key="local.phrase.return_till"/></th>
                <th><fmt:message bundle="${loc}" key="local.phrase.destination"/></th>
                <th><fmt:message bundle="${loc}" key="local.phrase.is_returned"/></th>
                <th><fmt:message bundle="${loc}" key="local.phrase.book_no."/></th>
                <th><fmt:message bundle="${loc}" key="local.phrase.title"/></th>
                <th><fmt:message bundle="${loc}" key="local.phrase.authors"/></th>
                <c:if test="${main_user.role.toString()!=\"READER\"}">
                    <th></th>
                    <th></th>
                </c:if>
            </tr>
            </thead>
            <c:forEach items="${card_notes}" var="note">
                <c:if test="${note.getDestination().toString()!=\"ORDER\"}">
                    <tbody>
                    <tr>
                        <td><c:out value="${note.getId()}"/></td>
                        <td><c:out value="${note.getInitialDate()}"/></td>
                        <td><c:out value="${note.getFinalDate()}"/></td>
                        <td>
                            <c:if test="${note.getDestination().toString()==\"CARD\"}">
                                <fmt:message bundle="${loc}" key="local.phrase.card"/>
                            </c:if>
                            <c:if test="${note.getDestination().toString()==\"READING_ROOM\"}">
                                <fmt:message bundle="${loc}" key="local.phrase.reading_room"/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${note.isActive()}"><fmt:message bundle="${loc}" key="local.phrase.n"/></c:if>
                            <c:if test="${!note.isActive()}"><fmt:message bundle="${loc}" key="local.phrase.y"/></c:if>
                        </td>
                        <td><c:out value="${note.getBook().getId()}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/Controller?command=take_book&book_id=${note.getBook().getId()}">
                                <c:out value="${note.getBook().getTitle()}"/>
                            </a>
                        </td>
                        <td>
                            <c:forEach items="${note.getBook().getAuthors()}" var="author">
                                <c:out value="${author.getSurname()} ${author.getName()} ${author.getPatronymic()}"/>
                            </c:forEach>
                        </td>
                        <c:if test="${main_user.role.toString()!=\"READER\"}">
                            <td>
                                <form action="Controller" method="post">
                                    <input type="hidden" name="command" value="update_card_note"/>
                                    <input type="hidden" name="card_note_id" value="${note.getId()}"/>
                                    <p><input type="submit" class="btn btn-secondary" disabled
                                              value="<fmt:message bundle="${loc}" key="local.phrase.update"/>"/></p>
                                </form>
                            </td>
                            <td>
                                <form action="Controller" method="post">
                                    <input type="hidden" name="command" value="delete_card_note"/>
                                    <input type="hidden" name="card_note_id" value="${note.getId()}"/>
                                    <p><input type="submit" class="btn btn-secondary" disabled
                                              value="<fmt:message bundle="${loc}" key="local.phrase.delete"/>"/></p>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                    </tbody>
                </c:if>
            </c:forEach>
        </table>
        <br>
        <div class="row">
            <div class="col-sm-6">
                <h4 class="logo"><fmt:message bundle="${loc}" key="local.phrase.the_ordered_books_list"/>:</h4>
            </div>
            <div class="col-sm-6"></div>
        </div>
        <table class="table table-striped table-hover">
            <thead class="thead-light">
            <tr>
                <th>№</th>
                <th width="124"><fmt:message bundle="${loc}" key="local.phrase.ordered"/></th>
                <th width="124"><fmt:message bundle="${loc}" key="local.phrase.reserved"/></th>
                <th><fmt:message bundle="${loc}" key="local.phrase.is_active"/></th>
                <th><fmt:message bundle="${loc}" key="local.phrase.book_no."/></th>
                <th><fmt:message bundle="${loc}" key="local.phrase.title"/></th>
                <th><fmt:message bundle="${loc}" key="local.phrase.authors"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${card_notes}" var="note">
                <c:if test="${note.getDestination().toString()==\"ORDER\"}">
                    <tbody>
                    <tr>
                        <td><c:out value="${note.getId()}"/></td>
                        <td><c:out value="${note.getInitialDate()}"/></td>
                        <td><c:out value="${note.getFinalDate()}"/></td>
                        <td>
                            <c:if test="${note.isActive()}"><fmt:message bundle="${loc}" key="local.phrase.y"/></c:if>
                            <c:if test="${!note.isActive()}"><fmt:message bundle="${loc}" key="local.phrase.n"/></c:if>
                        </td>
                        <td><c:out value="${note.getBook().getId()}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/Controller?command=take_book&book_id=${note.getBook().getId()}">
                                <c:out value="${note.getBook().getTitle()}"/>
                            </a>
                        </td>
                        <td>
                            <c:forEach items="${note.getBook().getAuthors()}" var="author">
                                <c:out value="${author.getSurname()} ${author.getName()} ${author.getPatronymic()}"/>
                            </c:forEach>
                        </td>
                        <td>
                            <form action="Controller" method="post">
                                <input type="hidden" name="command" value="cancel_order">
                                <input type="hidden" name="card_note_id" value="${note.getId()}">
                                <input type="hidden" name="user_id" value="${user.id}">
                                <c:if test="${note.isActive()}">
                                    <input type="submit" class="btn btn-secondary"
                                           value="<fmt:message bundle="${loc}" key="local.phrase.cancel"/>"/>
                                </c:if>
                                <c:if test="${!note.isActive()}">
                                    <input type="submit" class="btn btn-secondary" disabled
                                           value="<fmt:message bundle="${loc}" key="local.phrase.cancel"/>"/>
                                </c:if>
                            </form>
                        </td>
                        <td>
                            <form action="Controller" method="post">
                                <input type="hidden" name="command" value="delete_card_note">
                                <input type="hidden" name="card_note_id" value="${note.getId()}">
                                <input type="hidden" name="user_id" value="${user.id}">
                                <c:if test="${note.isActive()}">
                                    <input type="submit" class="btn btn-secondary" disabled
                                           value="<fmt:message bundle="${loc}" key="local.phrase.delete"/>"/>
                                </c:if>
                                <c:if test="${!note.isActive()}">
                                    <input type="submit" class="btn btn-secondary"
                                           value="<fmt:message bundle="${loc}" key="local.phrase.delete"/>"/>
                                </c:if>
                            </form>
                        </td>
                    </tr>
                    </tbody>
                </c:if>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
