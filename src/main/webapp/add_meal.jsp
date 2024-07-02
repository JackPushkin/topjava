<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Create meal</title>
    </head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <h2>Create meal</h2>
        <form method="post" action="meals?action=create">
            <p>DateTime:&ensp;<label><input type="datetime-local" name="dateTime"></label></p>
            <p>Description:&ensp;<label><input type="text" name="description"></label></p>
            <p>Calories:&ensp;<label><input type="number" name="calories"></label></p>
            <p><input type="submit" value="save">
            <button onclick="window.history.back()" type="button">Cancel</button></p>
        </form>
    </body>
</html>
