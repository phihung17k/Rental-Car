/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.daos;

import hungnp.dtos.OrderDTO;
import hungnp.utilities.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Win 10
 */
public class OrderDAO implements Serializable{
    
    public int countAmountOfOrder() throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select count(OrderID) as countOrderID "
                        + "from [Order] ";
                statement = connection.prepareStatement(sql);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getInt("countOrderID");
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return -1;
    }
    
    public boolean createOrder(String orderId, String userId, double totalPrice, String discountCode) throws SQLException, NamingException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "insert into [Order](OrderID, Email, TotalPrice, DiscountCode) "
                        + "values(?, ?, ?, ?) ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, orderId);
                statement.setString(2, userId);
                statement.setDouble(3, totalPrice);
                statement.setString(4, discountCode);
                int row = statement.executeUpdate();
                if(row>0){
                    return true;
                }
            }
        }finally{
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return false;
    }
    
    public boolean createOrder(String orderId, String userId, double totalPrice) throws SQLException, NamingException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "insert into [Order](OrderID, Email, TotalPrice) "
                        + "values(?, ?, ?) ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, orderId);
                statement.setString(2, userId);
                statement.setDouble(3, totalPrice);
                int row = statement.executeUpdate();
                if(row>0){
                    return true;
                }
            }
        }finally{
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return false;
    }
    
    public List<OrderDTO> getListOrder(String userId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<OrderDTO> listOrder = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select OrderID, TotalPrice, OrderDate "
                        + "from [Order] "
                        + "where Email=? and OrderStatusID='OS0' "
                        + "order by OrderDate ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, userId);
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    if(listOrder == null){
                        listOrder = new ArrayList<>();
                    }
                    String orderId = resultSet.getString("OrderID");
                    double totalPrice = resultSet.getDouble("TotalPrice");
                    Date orderDate = resultSet.getDate("OrderDate");
                    OrderDTO order = new OrderDTO(orderId, totalPrice, orderDate);
                    listOrder.add(order);
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return listOrder;
    }
    
    public boolean checkExistedOrder(String orderId, String userId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select OrderID "
                        + "from [Order] "
                        + "where OrderID=? and OrderStatusID='OS0' and Email=? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, orderId);
                statement.setString(2, userId);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return true;
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return false;
    }
    
    public boolean deleteOrder(String orderId) throws SQLException, NamingException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "update [Order] "
                        + "set OrderStatusID='OS1' "
                        + "where OrderID=? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, orderId);
                int row = statement.executeUpdate();
                if(row>0){
                    return true;
                }
            }
        }finally{
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return false;
    }
    
    public List<OrderDTO> searchOrderById(String searchValue) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<OrderDTO> listOrder= null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select OrderID, TotalPrice, OrderDate "
                        + "from [Order] "
                        + "where OrderStatusID='OS0' and OrderID like ? "
                        + "order by OrderDate ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, "%"+searchValue+"%");
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    if(listOrder == null){
                        listOrder = new ArrayList<>();
                    }
                    String orderId = resultSet.getString("OrderID");
                    double totalPrice = resultSet.getDouble("TotalPrice");
                    Date orderDate = resultSet.getDate("OrderDate");
                    OrderDTO order = new OrderDTO(orderId, totalPrice, orderDate);
                    listOrder.add(order);
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return listOrder;
    }
    
    public List<OrderDTO> searchOrderByDate(String searchValue) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<OrderDTO> listOrder= null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select OrderID, TotalPrice, OrderDate "
                        + "from [Order] "
                        + "where OrderStatusID='OS0' and OrderDate=? "
                        + "order by OrderDate ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, searchValue);
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    if(listOrder == null){
                        listOrder = new ArrayList<>();
                    }
                    String orderId = resultSet.getString("OrderID");
                    double totalPrice = resultSet.getDouble("TotalPrice");
                    Date orderDate = resultSet.getDate("OrderDate");
                    OrderDTO order = new OrderDTO(orderId, totalPrice, orderDate);
                    listOrder.add(order);
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return listOrder;
    }
}

















































































