/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package obj;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Moment
 */
public class MyJDBC {
    private static String DB_URL="jdbc:mysql://127.0.0.1:3306/bankapp";
    private static String DB_Username="root";
    private static String DB_Password="itptit2725";    
    public static User validLogin(String username,String password)
    {
        try
        {
            Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
            PreparedStatement ppst =cnt.prepareStatement("SELECT * FROM customer WHERE username= ? and pass= ? ");
            ppst.setString(1, username);
            ppst.setString(2, password);
            ResultSet rs= ppst.executeQuery();
            if(rs.next())
            {
                int userId=rs.getInt("id");
                BigDecimal currentBalance =rs.getBigDecimal("balance");
                return new User(userId,username,password,currentBalance);
            }
           
        }
        catch(SQLException e)
        {
            
        }
        return null;
    }
    public static boolean register(String username,String password)
    {
        
        try
        {
            if(!checkUser(username))
            {
                int curr=countCus();
                Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
                PreparedStatement ppst= cnt.prepareStatement("INSERT INTO customer(id,username,pass) VALUES(?,?,?)");
                ppst.setInt(1, curr+1);
                ppst.setString(2, username);
                ppst.setString(3, password);
                int rowsAffected = ppst.executeUpdate(); // Sửa thành executeUpdate()
                System.out.println("Rows affected: " + rowsAffected); // In ra số dòng bị ảnh hưởng
                return true;
            }
        }
        catch(SQLException e)
        {
             System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();  // In toàn bộ lỗi SQL để xem chi tiết
        }
        return false;
    }
    public static boolean checkUser(String username)
    {
        try
        {
            Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
            PreparedStatement ppst = cnt.prepareStatement("SELECT * FROM customer WHERE username=?");
            ppst.setString(1, username);
            ResultSet rs=ppst.executeQuery();
            if(!rs.next())
            {
                return false;
            }
            
               
            
        }
        catch(SQLException e)
        {
            
        }
        return true;
    }
    public static int countCus()
    {
        int cnt=0;
        try
        {
            Connection connection=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
            PreparedStatement ppst=connection.prepareStatement("SELECT COUNT(*) FROM customer");
            ResultSet rs=ppst.executeQuery();
            if(rs.next())
            {
                cnt=rs.getInt(1);
            }
        }
        catch(SQLException e)
        {   
            
        }
        return cnt;
    }
    
}
