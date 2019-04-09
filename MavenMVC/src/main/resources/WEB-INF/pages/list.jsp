<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<html>
    <head>
        <title>Prog.kiev.ua</title>
    </head>
    <body>
        <div align="center">
            <form action="/multidel", method="post">
                <table border="1">
                    <c:forEach items="${photos}" var="photo">
                        <tr><td>${photo.id}</td><td><a href="/photo/${photo.id}">${photo.name}</a></td><td><input type="checkbox", name="check", value="${photo.id}"></td></tr>
                    </c:forEach>
                </table>
                <input type="submit" value="Delete"/>
            </form>
            <input type="submit" value="Upload New" onclick="window.location='/';" />
        </div>
    </body>
</html>
