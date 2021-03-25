<%-- 
    Document   : register
    Created on : Feb 18, 2021, 4:46:22 PM
    Author     : Win 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <style>
            span{
                color: red;
            }
        </style>
    </head>
    <body>
        <h1>Register Page</h1>
        <br/>
        <form action="register" method="POST">
            <c:set var="error" value="${sessionScope.registerError}"/>
            Email: <input type="text" name="userId" value="${sessionScope.email}" placeholder="Email"/>
            <span>${error.emailEmptyError}</span>
            <span>${error.emailFormatError}</span>
            <span>${error.emailExistedError}</span>
            <br/><br/>
            Phone: <input type="text" name="phone" value="${sessionScope.phone}" />
            <span>${error.phoneEmptyError}</span>
            <span>${error.phoneInvalidError}</span>
            <br/><br/>
            Name: <input type="text" name="name" value="${sessionScope.name}" />
            <span>${error.nameEmptyError}</span>
            <br/><br/>
            Address: <input type="text" name="address" value="${sessionScope.address}" />
            <span>${error.addressEmptyError}</span>
            <br/><br/>
            
            Password: <input type="password" name="password" value="${sessionScope.password}" />
            <span>${error.passwordEmptyError}</span>
            <br/><br/>
            Confirm: <input type="password" name="confirm" value="" />
            <span>${error.confirmNotMatchError}</span>
            <br/><br/>
            
            <input type="submit" value="Register"><br/><br/>
            <a href="loginPage">Have been the account? Login</a><br/>
            <a href="homePage">Return home</a>
        </form>
    </body>
</html>
