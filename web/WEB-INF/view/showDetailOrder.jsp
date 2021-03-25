<%-- 
    Document   : showDetailOrder
    Created on : Mar 6, 2021, 10:24:39 AM
    Author     : Win 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Detail Order</title>
        <style>
            td{
                padding: 20px;
            }
            table, tr, th, td{
                border: 1px solid lightslategrey;
                border-collapse: collapse;
            }
        </style>
    </head>
    <body>
        <div>
            <p style="color:red;">Welcome, ${sessionScope.userName}</p>
            <form action="logout" method="POST">
                <input type="submit" value="Logout" />
            </form>
        </div>
        <h1>Detail Order</h1>
        <br/>
        <table>
            <tr>
                <th>Car ID</th>
                <th>Rental Date</th>
                <th>Return Date</th>
                <th>Amount</th>
            </tr>
            <c:forEach var="orderDetail" items="${sessionScope.listOrderDetail}">
                <tr>
                    <td>${orderDetail.carName}</td>
                    <td>${orderDetail.rentalDate}</td>
                    <td>${orderDetail.returnDate}</td>
                    <td>${orderDetail.amount}</td>
                </tr>
            </c:forEach>
        </table>
        <br/>
        <div>
            <a href="orderHistoryPage">Return order history</a>
        </div>
    </body>
</html>
