<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/bootstrap.jsp" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <title><fmt:message bundle="${loc}" key="local.phrase.registration"/></title>
    <meta charset="UTF-8"/>
</head>
<body>
<div class="container">
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="registration">
        <input type="hidden" name="role" value="reader">
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-6" align="center">
                <h2><fmt:message bundle="${loc}" key="local.phrase.registration_of_the_new_user"/></h2>
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-2"><fmt:message bundle="${loc}" key="local.phrase.surname"/>:</div>
            <div class="col-sm-4"><label>
                <input required type="text" size="45" pattern="[a-zA-Zа-яА-Я']{2,}" name="user_surname">
            </label></div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-2"><fmt:message bundle="${loc}" key="local.phrase.name"/>:</div>
            <div class="col-sm-4"><label>
                <input required type="text" size="45" pattern="[a-zA-Zа-яА-Я']{2,}" name="user_name">
            </label></div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-2"><fmt:message bundle="${loc}" key="local.phrase.patronymic"/>:</div>
            <div class="col-sm-4"><label>
                <input type="text" size="45" pattern="[a-zA-Zа-яА-Я']{2,}" name="user_patronymic">
            </label></div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-2"><fmt:message bundle="${loc}" key="local.phrase.birth_date"/>:</div>
            <div class="col-sm-4"><fmt:message bundle="${loc}" key="local.phrase.year"/>:
                <label>
                    <select name="year">
                        <c:forEach var="i" begin="1940" end="2015">
                            <option><c:out value="${i}"/></option>
                        </c:forEach>
                    </select>
                </label>
                <fmt:message bundle="${loc}" key="local.phrase.month"/>:
                <label>
                    <select name="month">
                        <option value="1"><fmt:message bundle="${loc}" key="local.phrase.january"/></option>
                        <option value="2"><fmt:message bundle="${loc}" key="local.phrase.february"/></option>
                        <option value="3"><fmt:message bundle="${loc}" key="local.phrase.march"/></option>
                        <option value="4"><fmt:message bundle="${loc}" key="local.phrase.april"/></option>
                        <option value="5"><fmt:message bundle="${loc}" key="local.phrase.may"/></option>
                        <option value="6"><fmt:message bundle="${loc}" key="local.phrase.june"/></option>
                        <option value="7"><fmt:message bundle="${loc}" key="local.phrase.july"/></option>
                        <option value="8"><fmt:message bundle="${loc}" key="local.phrase.august"/></option>
                        <option value="9"><fmt:message bundle="${loc}" key="local.phrase.september"/></option>
                        <option value="10"><fmt:message bundle="${loc}" key="local.phrase.october"/></option>
                        <option value="11"><fmt:message bundle="${loc}" key="local.phrase.november"/></option>
                        <option value="12"><fmt:message bundle="${loc}" key="local.phrase.december"/></option>
                    </select>
                </label>
                <fmt:message bundle="${loc}" key="local.phrase.day"/>:
                <label>
                    <select name="day">
                        <c:forEach var="j" begin="1" end="31">
                            <option><c:out value="${j}"/></option>
                        </c:forEach>
                    </select>
                </label>
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-2"><fmt:message bundle="${loc}" key="local.phrase.phone"/>:</div>
            <div class="col-sm-4"> +375 <label>
                <select name="code">
                    <option value="17">17</option>
                    <option value="25">25</option>
                    <option value="29">29</option>
                    <option value="33">33</option>
                    <option value="44">44</option>
                </select></label>
                <label><input required type="tel" pattern="[0-9]{7}" name="number"></label>
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-2">E-mail:</div>
            <div class="col-sm-4"><label>
                <input required type="email" size="45" pattern="[\S]{2,}@[a-z]{3,}\.[a-z]{2,}" name="email">
            </label></div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-2"><fmt:message bundle="${loc}" key="local.phrase.password"/>:</div>
            <div class="col-sm-4"><label>
                <input required type="password" size="45" pattern=".{9,}" name="password">
            </label></div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row">
            <div class="col-sm-7"></div>
            <div class="col-sm-1"><input type="submit" class="btn btn-secondary"
                                         value="<fmt:message bundle="${loc}" key="local.phrase.register"/>"/>
            </div>
            <div class="col-sm-4"></div>
        </div>
        <c:if test="${registration_message!=null}">
            <div class="row">
                <div class="col-sm-3"></div>
                <div class="col-sm-6" align="center">
                    <p class="text-danger">
                        <c:out value="${registration_message}"/>
                    </p>
                    <c:remove var="registration_message" scope="session"/>
                </div>
                <div class="col-sm-3"></div>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>



