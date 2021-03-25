/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.daos;

import hungnp.utilities.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Win 10
 */
public class FeedbackDAO implements Serializable{
    
    public int getRating(String userId, String carId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select Rating "
                        + "from Feedback f "
                        + "where f.Email=? and f.CarID=? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, userId);
                statement.setString(2, carId);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getInt("Rating");
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return -1;
    }
    
    public boolean addRating(String userId, String carId, int rating) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "insert into Feedback(Email, CarID, Rating) "
                        + "values(?, ?, ?) ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, userId);
                statement.setString(2, carId);
                statement.setInt(3, rating);
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
    
    public boolean updateRating(String userId, String carId, int rating) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "update Feedback "
                        + "set Rating = ? "
                        + "where Email=? and CarID=? ";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, rating);
                statement.setString(2, userId);
                statement.setString(3, carId);
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
    
    
}
























