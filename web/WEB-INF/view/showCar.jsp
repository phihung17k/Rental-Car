<%-- 
    Document   : showCar
    Created on : Feb 21, 2021, 4:33:06 PM
    Author     : Win 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Car</title>
        <style>
            .parent{
                width:100%;
            }

            .car{
                width:30%;
                margin:15px;
                float:left;
            }

            .car img{
                width:100%;
            }

            .car p{
                font-size:17px;
            }

            .add{
                width:100%;
                height:2em;
                background-color:#3399ff;
                border-color:#3399ff;
            }
            .paging{
                clear: both; padding: 20px; font-size: 20px; text-align: center;
            }
            a{
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <c:if test="${not empty sessionScope.userName}">
            <div>
                <p style="color:red;">Welcome, ${sessionScope.userName}</p>
                <form action="logout" method="POST">
                    <input type="submit" value="Logout" />
                </form>
            </div>
        </c:if>
        <c:if test="${empty sessionScope.userName}">
            <div>
                <a href="loginPage">Login</a>
                <a href="registerPage">Register</a>
            </div>
        </c:if>
        <h1>Car Rental</h1>
        <div>
            <div>
                <form action="search" method="POST">
                    <c:set var="error" value="${sessionScope.searchError}"/>
                    <input type="radio" name="option" value="carName" id="name" checked=""/>
                    <label for="name">Name: </label>
                    <input type="text" name="name" value="${sessionScope.carName}" />
                    <p style="color: red">${error.nameEmptyError}</p>
                    <br/>
                    <c:if test="${sessionScope.option == sessionScope.carCategory}">
                        <input type="radio" name="option" value="carCategory" id="category" checked=""/>
                    </c:if>
                    <c:if test="${sessionScope.option != sessionScope.carCategory}">
                        <input type="radio" name="option" value="carCategory" id="category" />
                    </c:if>
                    <label for="category">Category: </label>
                    <select name="category">
                        <c:forEach var="cate" items="${sessionScope.mapCarCategory}">
                            <c:if test="${sessionScope.categoryId == cate.key}">
                                <option value="${cate.key}" selected="">${cate.value}</option>
                            </c:if>
                            <c:if test="${sessionScope.categoryId != cate.key}">
                                <option value="${cate.key}">${cate.value}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <br/><br/>
                    <c:if test="${not empty sessionScope.rentalDate and not empty sessionScope.returnDate}">
                        Rental Date: <input type="date" min="${sessionScope.minDate}" name="rentalDate" value="${sessionScope.rentalDate}"/>
                        Return Date: <input type="date" min="${sessionScope.minDate}" name="returnDate" value="${sessionScope.returnDate}"/>
                    </c:if>
                    <c:if test="${empty sessionScope.rentalDate}">
                        Rental Date: <input type="date" min="${sessionScope.minDate}" name="rentalDate" value="${sessionScope.minDate}"/>
                        Return Date: <input type="date" min="${sessionScope.minDate}" name="returnDate" value="${sessionScope.minDate}"/>
                    </c:if>
                    <p style="color: red">${error.rentalLessThanReturnDateError}</p>
                    Amount of car: <input type="text" value="${sessionScope.amountInput}" name="amountInput"/>
                    <p style="color: red">${error.amountEmptyError}</p>
                    <p style="color: red">${error.amountInvalidError}</p>
                    <p style="color: red">${sessionScope.addError}</p>

                    <br/>
                    <input type="submit" value="Search" />
                </form>
            </div><br/>
            <div>
                <c:if test="${not empty sessionScope.userName}">
                    <div>
                        <form action="viewCart" method="POST">
                            <input type="submit" value="View your Cart" />
                        </form>
                    </div><br/>
                    <div>
                        <form action="orderHistory" method="POST">
                            <input type="submit" value="View order history" />
                        </form>
                    </div><br/>
                    <div>
                        <form action="viewFeedback" method="POST">
                            <input type="submit" value="Feedback" />
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="parent">
            <c:forEach var="car" items="${sessionScope.listCar}">
                <div class="car">
                    <form action="addCar" method="POST">
                        <img src="https://images.unsplash.com/photo-1494976388531-d1058494cdd8?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"/>
                        <p style="font-size:20px; font-weight: bold">${car.carName}</p>
                        <p>Color: ${car.color}</p>
                        <p>Year of manufacture: ${car.year}</p>
                        <p>Category: ${car.category}</p>
                        <p>Price: ${car.price}/${car.unitPrice}</p>
                        <p>Remain: ${car.quantity}</p>
                        <c:if test="${not empty sessionScope.userName}">
                            <input type="submit" value="Add" class="add" name="add"/>
                            <input type="hidden" name="carId" value="${car.carId}" />
                        </c:if>
                    </form>
                </div>
            </c:forEach>
            <c:if test="${empty sessionScope.listCar}">
                <p style="color: #3399ff; font-size: 20px;">Not found any result</p>
            </c:if>
        </div>
        <div class="paging">
            <c:if test="${not empty sessionScope.listCar}">
                <c:if test="${not empty sessionScope.amountInput}">
                    <span>Page:</span>
                    <c:forEach begin="1" end="${sessionScope.numOfPage}" varStatus="counter">
                        <a href="search?page=${counter.count}">${counter.count}</a>
                    </c:forEach>
                </c:if>
                <c:if test="${empty sessionScope.amountInput}">
                    <span>Page:</span>
                    <c:forEach begin="1" end="${sessionScope.numOfPage}" varStatus="counter">
                        <a href="showCar?page=${counter.count}">${counter.count}</a>
                    </c:forEach>
                </c:if>
            </c:if>
        </div>
    </body>
</html>
