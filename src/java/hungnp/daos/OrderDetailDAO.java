/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.daos;

import hungnp.dtos.CarDTO;
import hungnp.dtos.OrderDetailDTO;
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
public class OrderDetailDAO implements Serializable{
    
    public int getAmountCarByOrderDetailId(String orderDetailId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select Amount "
                        + "from OrderDetail "
                        + "where OrderDetailID=? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, orderDetailId);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getInt("Amount");
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return -1;
    }
    
    public boolean createOrderDetail(String orderId, String carId, 
            String rentalDate, String returnDate, int amount) throws SQLException, NamingException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "insert into OrderDetail(OrderID, CarID, RentalDate, ReturnDate, Amount) "
                        + "values(?, ?, ?, ?, ?) ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, orderId);
                statement.setString(2, carId);
                statement.setString(3, rentalDate);
                statement.setString(4, returnDate);
                statement.setInt(5, amount);
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
    
    public List<OrderDetailDTO> getListOrderDetailByOrderId(String orderId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<OrderDetailDTO> listOrderDetail = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select c.CarName, od.RentalDate, od.ReturnDate, od.Amount "
                        + "from [Order] o, OrderDetail od, Car c "
                        + "where o.OrderID=od.OrderID and od.CarID=c.CarID and o.OrderID=? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, orderId);
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    if(listOrderDetail == null){
                        listOrderDetail = new ArrayList<>();
                    }
                    String carName = resultSet.getString("CarName");
                    Date rentalDate = resultSet.getDate("RentalDate");
                    Date returnDate = resultSet.getDate("ReturnDate");
                    int amount = resultSet.getInt("Amount");
                    OrderDetailDTO orderDetail = new OrderDetailDTO(carName, amount, rentalDate, returnDate);
                    listOrderDetail.add(orderDetail);
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return listOrderDetail;
    }
    
    //for feedback
    public List<CarDTO> getListReturnedCar() throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CarDTO> listReturned = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select distinct od.CarID, c.CarName "
                        + "from OrderDetail od, Car c "
                        + "where getdate()-1 > ReturnDate and od.CarID=c.CarID ";
                statement = connection.prepareStatement(sql);
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    if(listReturned == null){
                        listReturned = new ArrayList<>();
                    }
                    String carID = resultSet.getString("CarID");
                    String carName = resultSet.getString("CarName");
                    CarDTO car = new CarDTO(carID, carName);
                    listReturned.add(car);
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return listReturned;
    }
    
    
    
}















































