<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        table, th, td {
            border: 1px solid black;
            text-align: center;
        }
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>
    <h4><a href="add_meal.jsp">Add meal</a></h4>
    <table>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Update meal</th>
            <th>Delete meal</th>
        </tr>
        <c:set var="list" value='${requestScope["mealsList"]}'/>
        <c:forEach var="meal" items="${list}">
            <tr style="color:${meal.excess ? 'red' : 'black'}">
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a href="meals?action=update&mealId=<c:out value="${meal.id}"/>">Update</a></td>
                <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>

<%--                    <form name="Update" method="post" action="meals">--%>
<%--                        <input type="hidden" name="param" value="update">--%>
<%--                        <input type="hidden" name="id" value="${meal.id}">--%>
<%--                        <a href="javascript:document.Update.submit()">Update</a>--%>
<%--                    </form>--%>

<%--                    <form name="Delete" method="post" action="meals">--%>
<%--                        <input type="hidden" name="param" value="delete">--%>
<%--                        <input type="hidden" name="id" value="${meal.id}">--%>
<%--                        <a href="javascript:document.Delete.submit()">Delete</a>--%>
<%--                    </form>--%>
