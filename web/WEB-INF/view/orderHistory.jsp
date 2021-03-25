<%-- 
    Document   : orderHistory
    Created on : Mar 6, 2021, 9:25:03 AM
    Author     : Win 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order History</title>
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
        <h1>Order History</h1>
        <br/>
        <div>
            <form action="searchOrderHistory" method="POST">
                <c:if test="${sessionScope.option==sessionScope.id}">
                    <input type="radio" name="option" value="id" id="x" checked=""/>
                    <label for="x">Order Id: </label>
                    <input type="text" name="orderId" value="${sessionScope.orderId}" />
                    <p style="color: red;">${sessionScope.emptyIdError}</p>
                </c:if>
                <c:if test="${sessionScope.option!=sessionScope.id}">
                    <input type="radio" name="option" value="id" id="x"/>
                    <label for="x">Order Id: </label>
                    <input type="text" name="orderId" value="${sessionScope.orderId}" />
                    <p style="color: red;">${sessionScope.emptyIdError}</p>
                </c:if>
                <br/>
                <c:if test="${sessionScope.option!=sessionScope.id}">
                    <input type="radio" name="option" value="date" id="y" checked=""/>
                    <label for="y">Order Date: </label>
                    <c:if test="${not empty sessionScope.dateCreatedOrder}">
                        <input type="date" name="dateCreatedOrder" value="${sessionScope.dateCreatedOrder}"/>
                    </c:if>
                    <c:if test="${empty sessionScope.dateCreatedOrder}">
                        <input type="date" name="dateCreatedOrder" value="${sessionScope.currentDate}"/>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.option==sessionScope.id}">
                    <input type="radio" name="option" value="date" id="y"/>
                    <label for="y">Order Date: </label>
                    <c:if test="${not empty sessionScope.dateCreatedOrder}">
                        <input type="date" name="dateCreatedOrder" value="${sessionScope.dateCreatedOrder}"/>
                    </c:if>
                    <c:if test="${empty sessionScope.dateCreatedOrder}">
                        <input type="date" name="dateCreatedOrder" value="${sessionScope.currentDate}"/>
                    </c:if>
                </c:if>
                <br/>
                <input type="submit" value="Search" />
            </form>
        </div>
        <br/>
        <table>
            <c:if test="${not empty sessionScope.listOrder}">
                <tr>
                    <th>Order Id</th>
                    <th>Total Price</th>
                    <th>Order Date</th>
                    <th>Delete</th>
                    <th>Detail</th>
                </tr>
                <c:forEach var="order" items="${sessionScope.listOrder}">
                    <form action="deleteOrder" method="POST">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.totalPrice}</td>
                            <td>${order.orderDate}</td>
                            <td>
                                <input type="submit" value="Delete" />
                                <input type="hidden" name="orderId" value="${order.orderId}" />
                            </td>
                            <td>
                                <a href="detailOrder?orderId=${order.orderId}">More Detail</a>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </c:if>
            <c:if test="${empty sessionScope.listOrder}">
                <h3 style="color: #3399ff">Order history is empty!!!</h3>
            </c:if>
        </table>
        <br/>
        <c:if test="${not empty sessionScope.amountInput}">
            <a href="search">Return show car page</a>
        </c:if>
        <c:if test="${empty sessionScope.amountInput}">
            <a href="showCar">Return show car page</a>
        </c:if>
    </body>
</html>
