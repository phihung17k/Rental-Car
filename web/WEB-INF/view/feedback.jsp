<%-- 
    Document   : feedback
    Created on : Mar 7, 2021, 8:02:07 PM
    Author     : Win 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback Page</title>
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
        <h1>Feedback Page</h1>
        <br/>
        <div>
            <c:if test="${not empty sessionScope.mapRating}">
            <table>
                <tr>
                    <th>Car Name</th>
                    <th>Rating</th>
                    <th>Update Rate</th>
                </tr>
                
                    <c:forEach var="map" items="${sessionScope.mapRating}">
                        <c:set var="car" value="${map.key}"/>
                        <form action="updateRating" method="POST">
                            <tr>
                                <td>${car.carName}</td>
                                <c:if test="${not empty map.value}">
                                    <td>${map.value}/10</td>
                                </c:if>
                                <c:if test="${empty map.value}">
                                    <td>Not yet rated</td>
                                </c:if>
                                <td>
                                    <select name="rating">
                                        <c:forEach begin="0" end="10" step="1" varStatus="counter">
                                            <c:if test="${not empty map.value}">
                                                <c:if test="${map.value == counter.index}">
                                                    <option value="${counter.index}" selected="">${counter.index}</option>
                                                </c:if>
                                                <c:if test="${map.value != counter.index}">
                                                    <option value="${counter.index}">${counter.index}</option>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${empty map.value}">
                                                <option value="${counter.index}">${counter.index}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <input type="submit" value="Update" />
                                    <input type="hidden" name="carId" value="${car.carId}" />
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
            </table>
            </c:if>
            <c:if test="${empty sessionScope.mapRating}">
                <p style="color: #3399ff">Don't have any feedback yet</p>
            </c:if>
        </div><br/>
        <div>
            <c:if test="${not empty sessionScope.amountInput}">
                <a href="search">Return show car page</a>
            </c:if>
            <c:if test="${empty sessionScope.amountInput}">
                <a href="showCar">Return show car page</a>
            </c:if>
        </div>
    </body>
</html>
