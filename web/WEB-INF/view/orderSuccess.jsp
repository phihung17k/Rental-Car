<%-- 
    Document   : orderSuccess
    Created on : Mar 6, 2021, 9:17:01 AM
    Author     : Win 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Success</title>
    </head>
    <body>
        <h1>Order Successfully</h1><br/>
        <c:if test="${not empty sessionScope.amountInput}">
            <a href="search">Return show car page</a>
        </c:if>
        <c:if test="${empty sessionScope.amountInput}">
            <a href="showCar">Return show car page</a>
        </c:if>
    </body>
</html>
