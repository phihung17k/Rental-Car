<%-- 
    Document   : login
    Created on : Feb 16, 2021, 2:27:43 PM
    Author     : Win 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>

    </head>
    <body>
        <h1>Login Page</h1>
        <br/>
        <form action="login" method="POST">
            UserID: <input type="text" name="userId" value="${sessionScope.email}" placeholder="Email"/><br/><br/>
            Password: <input type="password" name="password" value="" placeholder="Password"/><br/><br/>
            <div class="g-recaptcha" data-sitekey="6LeaElwaAAAAAOl4LKicvH-WWYhOGOZbxQHHxsKo"></div>
            <br/>
            <p style="color: red;">${sessionScope.errorLogin}</p>
            <c:if test="${not empty sessionScope.emailNotConfirm}">
                <p style="color:red;">${sessionScope.emailNotConfirm}</p> 
                <a href="sendCodeVerification" style="text-decoration: none;">Active now</a>
            </c:if><br/>
            <input type="submit" value="Login"><br/><br/>
            <a href="registerPage">Haven't account yet? Register</a><br/>
            <a href="homePage">Return home</a>
        </form>
    </body>
</html>
