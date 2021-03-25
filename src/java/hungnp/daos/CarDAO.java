/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.daos;

import hungnp.dtos.CarDTO;
import hungnp.utilities.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author Win 10
 */
public class CarDAO implements Serializable{
    
    public Map<String, String> getAllCarCategory() throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<String, String> mapCarCategory = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select CarCategoryID, CarCategoryName "
                        + "from CarCategory ";
                statement = connection.prepareStatement(sql);
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    if(mapCarCategory == null){
                        mapCarCategory = new HashMap<>();
                    }
                    String categoryId = resultSet.getString("CarCategoryID");
                    String categoryName = resultSet.getString("CarCategoryName");
                    mapCarCategory.put(categoryId, categoryName);
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return mapCarCategory;
    }
    
    public List<CarDTO> searchAllCarByName(String name) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CarDTO> listAllCar = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select c.CarID, c.CarName, c.Color, c.YearOfManufacture, cc.CarCategoryName, c.Price, c.UnitPrice, c.Quantity "
                        + "from Car c, CarCategory cc "
                        + "where c.CarCategoryID=cc.CarCategoryID and c.CarName like ? "
                        + "order by c.CreatedDate asc ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, "%"+name+"%");
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    if(listAllCar == null){
                        listAllCar = new ArrayList<>();
                    }
                    String carId = resultSet.getString("CarID");
                    String carName = resultSet.getNString("CarName");
                    String color = resultSet.getString("Color");
                    String year = resultSet.getString("YearOfManufacture");
                    String category = resultSet.getString("CarCategoryName");
                    float price = resultSet.getFloat("Price");
                    String unitPrice = resultSet.getString("UnitPrice");
                    int quantity = resultSet.getInt("Quantity");
                    CarDTO car = new CarDTO(carId, carName, color, year, category, price, unitPrice, quantity);
                    listAllCar.add(car);
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return listAllCar;
    }
    
    public List<CarDTO> searchAllCarByCategory(String categoryId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CarDTO> listAllCar = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select c.CarID, c.CarName, c.Color, c.YearOfManufacture, cc.CarCategoryName, c.Price, c.UnitPrice, c.Quantity "
                        + "from Car c, CarCategory cc "
                        + "where c.CarCategoryID=cc.CarCategoryID and c.CarCategoryID = ? "
                        + "order by c.CreatedDate asc ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, categoryId);
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    if(listAllCar == null){
                        listAllCar = new ArrayList<>();
                    }
                    String carId = resultSet.getString("CarID");
                    String carName = resultSet.getNString("CarName");
                    String color = resultSet.getString("Color");
                    String year = resultSet.getString("YearOfManufacture");
                    String category = resultSet.getString("CarCategoryName");
                    float price = resultSet.getFloat("Price");
                    String unitPrice = resultSet.getString("UnitPrice");
                    int quantity = resultSet.getInt("Quantity");
                    CarDTO car = new CarDTO(carId, carName, color, year, category, price, unitPrice, quantity);
                    listAllCar.add(car);
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return listAllCar;
    }
    
    //key: carId, value: amount car in order detail
    public Map<String, Integer> getCarsConflictDateInDetailByName(String inputRentalDate, String inputReturnDate, String carName) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<String, Integer> mapCarConflict = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select od.CarID, od.Amount "
                        + "from Car c, OrderDetail od, [Order] o "
                        + "where c.CarID=od.CarID and o.OrderID = od.OrderID and o.OrderStatusID='OS0' "
                                                    + "and (? < od.RentalDate and ? >= od.RentalDate "
                                                    + "or ? > od.ReturnDate and ? <= od.ReturnDate "
                                                    + "or ? >= od.RentalDate and ? <= od.ReturnDate) "
                                                    + "and c.CarName like ? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, inputRentalDate);    
                statement.setString(2, inputReturnDate);
                statement.setString(3, inputReturnDate);
                statement.setString(4, inputRentalDate);
                statement.setString(5, inputRentalDate);
                statement.setString(6, inputReturnDate);
                statement.setString(7, "%"+carName+"%");
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    if(mapCarConflict == null){
                        mapCarConflict = new HashMap<>();
                    }
                    String carId = resultSet.getString("CarID");
                    int amount = resultSet.getInt("Amount");
                    if(mapCarConflict.containsKey(carId)){
                        int amountBefore = mapCarConflict.get(carId);
                        mapCarConflict.put(carId, amount+amountBefore);
                    }else{
                        mapCarConflict.put(carId, amount);
                    }
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return mapCarConflict;
    }
    
    //key: carId, value: amount car in order detail
    public Map<String, Integer> getCarsConflictDateInDetailByCategory(String inputRentalDate, String inputReturnDate, String categoryId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<String, Integer> mapCarConflict = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select od.CarID, od.Amount "
                        + "from Car c, OrderDetail od, [Order] o "
                        + "where c.CarID=od.CarID and o.OrderID = od.OrderID and o.OrderStatusID='OS0' "
                                                    + "and (? < od.RentalDate and ? >= od.RentalDate "
                                                    + "or ? > od.ReturnDate and ? <= od.ReturnDate "
                                                    + "or ? >= od.RentalDate and ? <= od.ReturnDate) "
                                                    + "and c.CarCategoryID = ? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, inputRentalDate);
                statement.setString(2, inputReturnDate);
                statement.setString(3, inputReturnDate);
                statement.setString(4, inputRentalDate);
                statement.setString(5, inputRentalDate);
                statement.setString(6, inputReturnDate);
                statement.setString(7, categoryId);
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    if(mapCarConflict == null){
                        mapCarConflict = new HashMap<>();
                    }
                    String carId = resultSet.getString("CarID");
                    int amount = resultSet.getInt("Amount");
                    if(mapCarConflict.containsKey(carId)){
                        int amountBefore = mapCarConflict.get(carId);
                        mapCarConflict.put(carId, amount+amountBefore);
                    }else{
                        mapCarConflict.put(carId, amount);
                    }
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return mapCarConflict;
    }
    
    public List getCarForCart(String carId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List listInfo = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select CarName, cc.CarCategoryName, Price "
                        + "from Car c, CarCategory cc "
                        + "where c.CarCategoryID = cc.CarCategoryID and c.CarID=? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, carId);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    if(listInfo ==null){
                        listInfo = new ArrayList();
                    }
                    String carName = resultSet.getString("CarName");
                    String carCategoryName = resultSet.getString("CarCategoryName");
                    float price = resultSet.getFloat("Price");
                    listInfo.add(carName);
                    listInfo.add(carCategoryName);
                    listInfo.add(price);
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return listInfo;
    }
    
    public int getQuantityById(String carId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select Quantity "
                        + "from Car  "
                        + "where CarID=? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, carId);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getInt("Quantity");
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return -1;
    }
    
    public int getAmountCarInOrderDetail(String inputRentalDate, String inputReturnDate, String carId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select sum(od.Amount) as sumAmount "
                        + "from Car c, OrderDetail od, [Order] o "
                        + "where c.CarID=od.CarID and o.OrderID = od.OrderID and o.OrderStatusID='OS0' "
                                                    + "and (? < od.RentalDate and ? >= od.RentalDate "
                                                    + "or ? > od.ReturnDate and ? <= od.ReturnDate "
                                                    + "or ? >= od.RentalDate and ? <= od.ReturnDate) "
                                                    + "and c.CarID = ? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, inputRentalDate);
                statement.setString(2, inputReturnDate);
                statement.setString(3, inputReturnDate);
                statement.setString(4, inputRentalDate);
                statement.setString(5, inputRentalDate);
                statement.setString(6, inputReturnDate);
                statement.setString(7, carId);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getInt("sumAmount");
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return -1;
    }
    
}
































































































































































