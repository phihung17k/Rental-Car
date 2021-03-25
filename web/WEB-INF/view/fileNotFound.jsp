<%-- 
    Document   : fileNotFound
    Created on : Feb 13, 2021, 7:42:26 AM
    Author     : Win 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Not Found</title>
    </head>
    <body>
        <h1>Not Found This Page</h1>
        <c:if test="${empty sessionScope.userName}">
            <a href="homePage">Return Home Page</a>
        </c:if>
        <c:if test="${not empty sessionScope.userName}">
            <a href="showCar">Return Home Page</a>
        </c:if>
    </body>
</html>
