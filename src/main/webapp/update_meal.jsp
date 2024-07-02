<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Update meal</title>
    </head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <h2>Update meal</h2>
        <c:set var="meal" value='${requestScope["updatedMeal"]}'/>
        <form method="post" action="meals?action=update">
            <p>Meal ID:&ensp;<label><input type="number" name="id" readonly="readonly" value="${meal.id}"></label></p>
            <p>DateTime:&ensp;<label><input type="datetime-local" name="dateTime" value="${meal.dateTime}"></label></p>
            <p>Description:&ensp;<label><input type="text" name="description" value="${meal.description}"></label></p>
            <p>Calories:&ensp;<label><input type="number" name="calories" value="${meal.calories}"></label></p>
            <p>
                <input type="submit" value="save">
                <button onclick="window.history.back()" type="button">cancel</button>
            </p>
        </form>
    </body>
</html>
