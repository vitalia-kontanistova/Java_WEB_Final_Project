<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/bootstrap.jsp" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <title><fmt:message bundle="${loc}" key="local.phrase.new_book"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navigation.jsp" flush="true"/>
<div class="container">
    <br>
    <h2 align="center"><fmt:message bundle="${loc}" key="local.phrase.new_book"/>: </h2>
    <form method="post" action="Controller">
        <div class="row add-book-input">
            <div class="col-sm-1"></div>
            <div class="col-sm-3"><fmt:message bundle="${loc}" key="local.phrase.title"/>:</div>
            <div class="col-sm-5">
                <input class="form-control" required size="40" type="text" pattern=".{3,}" name="title">
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row add-book-input">
            <div class="col-sm-1"></div>
            <div class="col-sm-3">
                <label for="sel1"><fmt:message bundle="${loc}" key="local.phrase.year_up"/>: </label>
            </div>
            <div class="col-sm-5">
                <select class="form-control" id="sel1" name="book_year">
                    <c:forEach var="i" begin="1901" end="2020">
                        <option><c:out value="${i}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row add-book-input">
            <div class="col-sm-1"></div>
            <div class="col-sm-3">
                <label for="sel2"><fmt:message bundle="${loc}" key="local.phrase.lang"/>: </label>
            </div>
            <div class="col-sm-5">
                <select class="form-control" id="sel2" name="lang">
                    <option value="RU"><fmt:message bundle="${loc}" key="local.phrase.russian"/></option>
                    <option value="BE"><fmt:message bundle="${loc}" key="local.phrase.belarusian"/></option>
                    <option value="PL"><fmt:message bundle="${loc}" key="local.phrase.polish"/></option>
                    <option value="LV"><fmt:message bundle="${loc}" key="local.phrase.latvian"/></option>
                    <option value="EN"><fmt:message bundle="${loc}" key="local.phrase.english"/></option>
                    <option value="DE"><fmt:message bundle="${loc}" key="local.phrase.german"/></option>
                    <option value="FR"><fmt:message bundle="${loc}" key="local.phrase.french"/></option>
                    <option value="ES"><fmt:message bundle="${loc}" key="local.phrase.spanish"/></option>
                    <option value="EO"><fmt:message bundle="${loc}" key="local.phrase.esperanto"/></option>
                </select>
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row add-book-input">
            <div class="col-sm-1"></div>
            <div class="col-sm-3"><fmt:message bundle="${loc}" key="local.phrase.number_of_pages"/>:</div>
            <div class="col-sm-5">
                <input class="form-control" required type="number" pattern="[0-9]{4}" min="3" max="2000"
                       name="number_of_pages">
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row add-book-input">
            <div class="col-sm-1"></div>
            <div class="col-sm-3"><fmt:message bundle="${loc}" key="local.phrase.number_of_copies"/>:</div>
            <div class="col-sm-5">
                <input class="form-control" required type="number" pattern="[0-9]{2}" min="1" max="99" name="quantity">
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row add-book-input">
            <div class="col-sm-1"></div>
            <div class="col-sm-3">
                <label for="annotation"><fmt:message bundle="${loc}" key="local.phrase.annotation"/>: </label>
            </div>
            <div class="col-sm-5">
                <textarea class="form-control" required rows="5" cols="78" id="annotation" name="annotation"
                          maxlength="1200"
                          placeholder="<fmt:message bundle="${loc}" key="local.phrase.annotation"/>"></textarea>
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row add-book-input">
            <div class="col-sm-1"></div>
            <div class="col-sm-8"><fmt:message bundle="${loc}" key="local.phrase.authors"/>*:
                <small><fmt:message bundle="${loc}" key="local.phrase.enter_author_info"/></small>
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row add-book-input">
            <div class="col-sm-1"></div>
            <div class="col-sm-3"><fmt:message bundle="${loc}" key="local.phrase.surname"/></div>
            <div class="col-sm-3"><fmt:message bundle="${loc}" key="local.phrase.name"/></div>
            <div class="col-sm-3"><fmt:message bundle="${loc}" key="local.phrase.patronymic"/></div>
            <div class="col-sm-2"></div>
        </div>

        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col-sm-3"><label>
                <input class="form-control" required size="35" type="text" pattern="[a-zA-Zа-яА-Я'\- ]{2,}"
                       name="author_1_surname">
            </label></div>
            <div class="col-sm-3"><label>
                <input class="form-control" required size="35" type="text" pattern="[a-zA-Zа-яА-Я'\- ]{2,}"
                       name="author_1_name">
            </label></div>
            <div class="col-sm-3"><label>
                <input class="form-control" size="35" type="text" pattern="[a-zA-Zа-яА-Я'\- ]{2,}"
                       name="author_1_patronymic">
            </label></div>
            <div class="col-sm-2"></div>
        </div>
        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col-sm-3"><label>
                <input class="form-control" size="35" type="text" pattern="[a-zA-Zа-яА-Я'\- ]{2,}"
                       name="author_2_surname">
            </label></div>
            <div class="col-sm-3"><label>
                <input class="form-control" size="35" type="text" pattern="[a-zA-Zа-яА-Я'\- ]{2,}" name="author_2_name">
            </label></div>
            <div class="col-sm-3"><label>
                <input class="form-control" size="35" type="text" pattern="[a-zA-Zа-яА-Я'\- ]{2,}"
                       name="author_2_patronymic">
            </label></div>
            <div class="col-sm-2"></div>
        </div>
        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col-sm-3"><label>
                <input class="form-control" size="35" type="text" pattern="[a-zA-Zа-яА-Я'\- ]{2,}"
                       name="author_3_surname">
            </label></div>
            <div class="col-sm-3"><label>
                <input class="form-control" size="35" type="text" pattern="[a-zA-Zа-яА-Я'\- ]{2,}" name="author_3_name">
            </label></div>
            <div class="col-sm-3"><label>
                <input class="form-control" size="35" type="text" pattern="[a-zA-Zа-яА-Я'\- ]{2,}"
                       name="author_3_patronymic">
            </label></div>
            <div class="col-sm-2"></div>
        </div>
        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col-sm-6">
                <input type="hidden" name="command" value="add_book">
                <input type="submit" class="btn btn-secondary"
                       value="<fmt:message bundle="${loc}" key="local.phrase.add"/>">
            </div>
            <div class="col-sm-3"></div>
        </div>
    </form>
</div>
</body>
</html>
