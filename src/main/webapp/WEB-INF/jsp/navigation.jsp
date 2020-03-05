<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
</head>
<body>
<div class="container bg-light-gray rounded-bottom">
    <nav class="navbar navbar-expand-lg bg-light-gray navbar-light">
    </nav>
    <nav class="navbar navbar-expand-lg bg-light-gray navbar-light">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/Controller?command=go_to_profile">
            <img src="${pageContext.request.contextPath}/img/Logo_200.png" style="width:50px;">
        </a>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/Controller?command=go_to_profile" class="nav-link"
                   role="button"><fmt:message bundle="${loc}" key="local.phrase.profile"/></a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/Controller?command=take_books" class="nav-link"
                   role="button"><fmt:message bundle="${loc}" key="local.phrase.catalog"/></a>
            </li>
            <c:if test="${main_user.role.toString()==\"READER\"}">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/Controller?command=take_user_card" class="nav-link"
                       role="button"><fmt:message bundle="${loc}" key="local.phrase.card"/></a>
                </li>
            </c:if>
            <c:if test="${main_user.role.toString()!=\"READER\"}">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/Controller?command=take_users" class="nav-link"
                       role="button"><fmt:message bundle="${loc}" key="local.phrase.users"/></a>
                </li>
            </c:if>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <div class="dropdown">
                    <button type="button" class="btn dropdown-toggle" data-toggle="dropdown">
                        <img src="${pageContext.request.contextPath}/img/lang.png"
                             style="height: 22px; background-color: inherit;">
                    </button>
                    <div class="dropdown-menu">
                        <form method="post" action="Controller">
                            <input type="hidden" name="command" value="change_language"/>
                            <input type="hidden" name="local" value="ru"/>
                            <input type="hidden" name="path" value="" id="ru"/>
                            <input type="submit" class="btn btn-link"
                                   value="<fmt:message bundle="${loc}" key="local.phrase.russian"/>"/>
                        </form>
                        <form method="post" action="Controller">
                            <input type="hidden" name="command" value="change_language"/>
                            <input type="hidden" name="local" value="en"/>
                            <input type="hidden" name="path" value="" id="en"/>
                            <input type="submit" class="btn btn-link"
                                   value="<fmt:message bundle="${loc}" key="local.phrase.english"/>"/>
                        </form>
                        <script type="text/javascript">
                            document.getElementById("ru").value = window.location.href;
                            document.getElementById("en").value = window.location.href;
                        </script>
                    </div>
                </div>
            </li>
            <li class="nav-item">
                <input type="search" class="form-control mb-2 mr-sm-2"
                       placeholder="<fmt:message bundle="${loc}" key="local.phrase.find_book"/>..."
                       id="part_of_book_title" onchange="proc()" onclick="this.value=''">
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/Controller?command=take_book_by_part_of_title&part_of_book_title="
                   id="current_link" class="btn btn-warning mb-2 mr-sm-2">
                    <fmt:message bundle="${loc}" key="local.phrase.search"/> </a>

                <script type="text/javascript">
                    defaultHref = "${pageContext.request.contextPath}/Controller?command=take_book_by_part_of_title&part_of_book_title=";

                    function proc() {
                        document.getElementById('current_link').href = defaultHref +
                            document.getElementById('part_of_book_title').value;
                    }
                </script>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/Controller?command=sign_out" class="btn btn-secondary"
                   role="button"><fmt:message bundle="${loc}" key="local.phrase.exit"/></a>
            </li>
        </ul>
    </nav>
</div>
</body>
</html>