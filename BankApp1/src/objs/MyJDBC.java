/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objs;

/**
 *
 * @author Moment
 */
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import objs.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class MyJDBC {
    private static String DB_URL="jdbc:mysql://127.0.0.1:3306/bankjava";
    private static String DB_Username="root";
    private static String DB_Password="itptit2725";    
    public static void register(String username,String password,String CCCD,String OTP,String phoneNumber,String STK) throws SQLException
    {
        if(!checkUser(username,CCCD,phoneNumber))
       {
        Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
        PreparedStatement ppst=cnt.prepareStatement("INSERT INTO user(username,password,CCCD,OTP,phoneNumber,STK) VALUES(?,?,?,?,?,?)");
        ppst.setString(1, username);
        ppst.setString(2, password);
        ppst.setString(3, CCCD);
        ppst.setString(4, OTP);
        ppst.setString(5, phoneNumber);
        ppst.setString(6, STK);
        ppst.executeUpdate();
       }
        else
        {
           
        }
    }
    public static boolean checkUser(String username,String CCCD,String phoneNumber) throws SQLException
    {
        Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
        PreparedStatement ppst=cnt.prepareStatement("SELECT * FROM user WHERE username=? OR CCCD=? OR phoneNumber=?");
        ppst.setString(1, username);
        ppst.setString(2, CCCD);
        ppst.setString(3, phoneNumber);
        ResultSet rs=ppst.executeQuery();
        if(rs.next())
        {
            return true;
        }
        return false;
    }
    public static User validLogin(String username,String password) throws SQLException
    {
        Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
        PreparedStatement ppst=cnt.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
        ppst.setString(1, username);
        ppst.setString(2, password);
        ResultSet rs=ppst.executeQuery();
        if(rs.next())
        {
            int id=rs.getInt("id");
            String CCCD=rs.getString("CCCD");
            String OTP=rs.getString("OTP");
            String phoneNumber=rs.getString("phoneNumber");
            BigDecimal currentBalance=rs.getBigDecimal("balance");
            String STK=rs.getString("STK");
            return new User(id,username,password,currentBalance,CCCD,OTP,phoneNumber,STK);
        }
        else
        {
            return null;
        }
    }
    public static boolean resetPassword(String CCCD,String phoneNumber,String newPassword) throws SQLException
    {
        Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
        PreparedStatement ppst=cnt.prepareStatement("SELECT * FROM user WHERE CCCD=? AND phoneNumber=?");
        ppst.setString(1, CCCD);
        ppst.setString(2, phoneNumber);
        ResultSet rs=ppst.executeQuery();
        if(!rs.next())
        {
            return false;
        }
        int id=rs.getInt("id");
        PreparedStatement ppst1=cnt.prepareStatement("UPDATE user SET password=? WHERE id=?");
        ppst1.setString(1, newPassword);
        ppst1.setInt(2, id);
        ppst1.executeUpdate();
        return true;
        
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
    public static boolean addTransaction(Transaction transaction)
    {
        try
        {
            Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
//            int Trans=countTrans();
            PreparedStatement ppst=cnt.prepareStatement("INSERT INTO transaction(user_id,trans_type,trans_amount,trans_date) VALUES(?,?,?,NOW())");
//            ppst.setInt(1, Trans+1);
            ppst.setInt(1, transaction.getUserId());
            ppst.setString(2,transaction.getTrans_type());
            ppst.setBigDecimal(3, transaction.getTrans_amount());
            ppst.executeUpdate();
            

            return true;
            
        
        }
        
        catch(SQLException e)
        {
            
        }
        
        return false;
    }
    public static boolean updateBalance(User user)
    {
        try
        {
            Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
            PreparedStatement ppst=cnt.prepareStatement("UPDATE user Set balance=? WHERE id = ?");
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
            PreparedStatement ppst=cnt.prepareStatement("SELECT * FROM user WHERE username=?");
            ppst.setString(1, transferUsername);
            ResultSet rs=ppst.executeQuery();
            while(rs.next())
            {
                int userId=rs.getInt("id");
                String password=rs.getString("password");
                BigDecimal balance=rs.getBigDecimal("balance");
                String CCCD=rs.getString("CCCD");
                String phoneNumber=rs.getString("phoneNumber");
                String STK=rs.getString("STK");
                String OTP=rs.getString("OTP");
                User transferUser=new User(userId,transferUsername,password,balance,CCCD,OTP,phoneNumber,STK);
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
    public static boolean checkOTP(User user,String OTP)
    {
        try
        {
            int userId=user.getId();
            Connection cnt=DriverManager.getConnection(DB_URL,DB_Username,DB_Password);
            PreparedStatement ppst=cnt.prepareStatement("SELECT * from user WHERE id=?");
            ppst.setInt(1, userId);
            ResultSet rs=ppst.executeQuery();
            if(rs.next())
            {
                String otpUser=rs.getString("ÖTP");
                if(OTP.equals(otpUser))
                {
                    return true;
                }
                else
                {
                    
                    return false;
                }
            }
        }
        catch(SQLException e)
        {
            
        }
        return false;
    }
    
    
}
