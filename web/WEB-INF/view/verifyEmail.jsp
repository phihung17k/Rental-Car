<%-- 
    Document   : activeAccount
    Created on : Feb 19, 2021, 11:02:48 AM
    Author     : Win 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Email</title>
    </head>
    <body>
        <h1>Verify Your Email</h1>
        <h4>Please check your email to get Code Verification</h4>
        <form action="verifyEmail" method="POST">
            Code Verification: <input type="text" name="codeInput" value="${sessionScope.codeInput}" />
            <span style="color: red;">${sessionScope.incorrectCode}</span>
            <br/>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
