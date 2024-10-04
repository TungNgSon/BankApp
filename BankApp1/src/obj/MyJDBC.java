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
                PreparedStatement ppst= cnt.prepareStatement("INSERT INTO customer(id,username,pass,balance) VALUES(?,?,?,?)");
                ppst.setInt(1, curr+1);
                ppst.setString(2, username);
                ppst.setString(3, password);
                ppst.setBigDecimal(4, BigDecimal.ZERO);
                int rowsAffected = ppst.executeUpdate(); // Sửa thành executeUpdate()
                
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
    public static boolean addTransaction(Transaction transaction)
    {
        try
        {
            Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
            int Trans=countTrans();
            PreparedStatement ppst=cnt.prepareStatement("INSERT INTO transaction(trans_id,user_id,trans_type,trans_amount,trans_date) VALUES(?,?,?,?,NOW())");
            ppst.setInt(1, Trans+1);
            ppst.setInt(2, transaction.getUserId());
            ppst.setString(3,transaction.getTrans_type());
            ppst.setBigDecimal(4, transaction.getTrans_amount());
            ppst.executeUpdate();
            return true;
        
        }
        
        catch(SQLException e)
        {
            
        }
        return false;
    }
    public static int countTrans()
    {
        int cnt=0;
        try
        {
            Connection connection=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
            PreparedStatement ppst=connection.prepareStatement("SELECT COUNT(*) FROM transaction");
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
    public static boolean updateBalance(User user)
    {
        try
        {
            Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
            PreparedStatement ppst=cnt.prepareStatement("UPDATE customer Set balance=? WHERE id = ?");
            ppst.setBigDecimal(1, user.getCurrentBalance());
            ppst.setInt(2, user.getId());
            ppst.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            
        }
        return false;
    }
    public static boolean transfer(User user,String transferUsername,float amount)
    {
        try
        {
            Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
            PreparedStatement ppst=cnt.prepareStatement("SELECT * FROM customer WHERE username=?");
            ppst.setString(1, transferUsername);
            ResultSet rs=ppst.executeQuery();
            while(rs.next())
            {
                int userId=rs.getInt("id");
                String password=rs.getString("pass");
                BigDecimal balance=rs.getBigDecimal("balance");
                User transferUser=new User(userId,transferUsername,password,balance);
                Transaction sentTrans=new Transaction(user.getId(),"Transfer",BigDecimal.valueOf(-amount),null);
                Transaction reTrans=new Transaction(userId,"Transfer",BigDecimal.valueOf(amount),null);
                //update receiver
                transferUser.setcurrentBalance(transferUser.getCurrentBalance().add(BigDecimal.valueOf(amount)));
                updateBalance(transferUser);
                //update sendUser
                user.setcurrentBalance(user.getCurrentBalance().subtract(BigDecimal.valueOf(amount)));
                updateBalance(user);
                //add these Transaction to Database
                addTransaction(sentTrans);
                addTransaction(reTrans);
                return true;
            }
        }
        catch(SQLException e)
        {
            
        }
        return false;
    }
    public static ArrayList<Transaction> getPastTransaction(User user)
    {
        ArrayList<Transaction> pastList=new ArrayList<>();
        try
        {
            Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
            PreparedStatement ppst=cnt.prepareStatement("SELECT * FROM transaction WHERE user_id=?");
            ppst.setInt(1, user.getId());
            ResultSet rs=ppst.executeQuery();
            while(rs.next())
            {
                Transaction transaction=new Transaction(user.getId(),rs.getString("trans_type"),rs.getBigDecimal("trans_amount"),rs.getDate("trans_date"));
                pastList.add(transaction);
            }
        }
        catch(SQLException e)
        {
            
        }
        return pastList;
    }
    
}
