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
public class UserDAO implements Serializable{
    
    public boolean checkLogin(String userId, String password) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null) {
                String sql = "select Email "
                        + "from [User] "
                        + "where Email=? and Password = ? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, userId);
                statement.setString(2, password);
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
    
    public String getUserName(String userId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null) {
                String sql = "select Name "
                        + "from [User] "
                        + "where Email=? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, userId);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    String userName = resultSet.getNString("Name");
                    return userName;
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return null;
    }
    
    public boolean checkNewUser(String userId) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null) {
                String sql = "select Email "
                        + "from [User] "
                        + "where Email=? and UserStatusID='US0' ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, userId);
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
    
    public boolean checkExistedEmail(String email) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null) {
                String sql = "select Email "
                        + "from [User] "
                        + "where Email=? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, email);
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
    
    public boolean registerAccount(String userId, String password, String name, String phone,
           String address ) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null) {
                String sql = "insert into [User](Email, Password, Name, Phone, Address, CreatedDate, UserRoleID, UserStatusID) "
                        + "values( ?, ?, ?, ?, ?, default, 'UR1', 'US0') ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, userId);
                statement.setString(2, password);
                statement.setNString(3, name);
                statement.setString(4, phone);
                statement.setNString(5, address);
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
    
    public boolean updateStatusUser(String email) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null) {
                String sql = "update [User] "
                        + "set UserStatusID='US1' "
                        + "where Email=? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, email);
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



























