<%-- 
    Document   : viewCart
    Created on : Mar 4, 2021, 8:43:46 PM
    Author     : Win 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
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
        <h1>Your cart</h1>
        <c:if test="${not empty sessionScope.listCarInCart}">
            <table>
                <tr>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Rental Date</th>
                    <th>Return Date</th>
                    <th>Amount</th>
                    <th>Price</th>
                    <th>Total</th>
                    <th>Delete</th>
                </tr>
                <c:forEach var="car" items="${sessionScope.listCarInCart}">
                    <c:set var="carInCart" value="${car.key}"/>
                    <form action="updateAmount" method="POST">
                        <tr>
                            <td>${carInCart.carName}</td>
                            <td>${carInCart.carCategory}</td>
                            <td>${carInCart.rentalDate}</td>
                            <td>${carInCart.returnDate}</td>
                            <td>
                                
                                <c:if test="${sessionScope.carIdUpdateError != carInCart.carId ||  
                                              sessionScope.rentalDateUpdateCar != carInCart.rentalDate ||
                                              sessionScope.returnDateUpdateCar != carInCart.returnDate}">
                                    <input type="text" name="amountInput" value="${car.value}" />
                                    <input type="submit" value="Update" />
                                </c:if>
                                <c:if test="${sessionScope.carIdUpdateError == carInCart.carId &&
                                              sessionScope.rentalDateUpdateCar == carInCart.rentalDate &&
                                              sessionScope.returnDateUpdateCar == carInCart.returnDate}">
                                    <input type="text" name="amountInput" value="${sessionScope.amountUpdateError}" />
                                    <p style="color:red;">${sessionScope.updateAmountError}</p>
                                    <input type="submit" value="Update" />
                                </c:if>
                                <input type="hidden" name="carId" value="${carInCart.carId}" />
                                <input type="hidden" name="rentalDate" value="${carInCart.rentalDate}" />
                                <input type="hidden" name="returnDate" value="${carInCart.returnDate}" />

                            </td>
                            <td>${carInCart.price}</td>
                            <td>${car.value * carInCart.price}</td>
                            <td>
                                <a href="delete?carId=${carInCart.carId}&&rentalDate=${carInCart.rentalDate}&&returnDate=${carInCart.returnDate}" 
                                   onclick="return confirm('Do you want remove this car in your cart?')">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
                <tr>
                    <td colspan="8">
                        Total Price: ${sessionScope.totalPrice}
                    </td> 
                </tr>
            </table>
        </c:if>
        <c:if test="${empty sessionScope.listCarInCart}">
            <h3 style="color: #3399ff">Your cart is empty!!!</h3>
        </c:if><br/>
        <c:if test="${not empty sessionScope.amountInput}">
            <a href="search">Return show car page</a>
        </c:if>
        <c:if test="${empty sessionScope.amountInput}">
            <a href="showCar">Return show car page</a>
        </c:if>
        <c:if test="${not empty sessionScope.listCarInCart}">
            <div >
                <c:if test="${not empty sessionScope.discountValue}">
                    Discount: ${sessionScope.discountValue}%
                </c:if>
                <c:if test="${empty sessionScope.discountValue}">
                    <form action="confirmDiscount" style="text-align: center;">
                        <input type="checkbox" name="optionInputDiscount" value="InputDiscount" id="d" checked=""/>
                        <label for="d">Discount Code: </label>
                        <input type="text" name="discountCode" value="${sessionScope.discountCode}"/>
                        <p style="color: red;">${sessionScope.discountInputError}</p>
                        <input type="submit" value="Enter" />
                    </form>
                </c:if>
            </div><br/>
            <div style="width: 100%;">
                <form action="confirmOrder" style="text-align: center;">
                    <input type="submit" value="Confirm Order" />
                </form>
            </div>
        </c:if>
    </body>
</html>
