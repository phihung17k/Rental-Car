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
public class DiscountDAO implements Serializable{
    
    public int getDiscountValue(String discountCode) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "select DiscountValue "
                        + "from Discount "
                        + "where DiscountCode=? and ExpiredDate>=getdate() and DiscountStatusID='DS0' ";
                statement= connection.prepareStatement(sql);
                statement.setString(1, discountCode);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return resultSet.getInt("DiscountValue");
                }
            }
        }finally{
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
        return -1;
    }
    
    public boolean deleteDiscountCode(String discountCode) throws NamingException, SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = MyConnection.makeConnection();
            if(connection!=null){
                String sql = "update Discount "
                        + "set DiscountStatusID='DS1' "
                        + "where DiscountCode=? ";
                statement= connection.prepareStatement(sql);
                statement.setString(1, discountCode);
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


























