/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objs;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Moment
 */
public class User {
    private int id;
    private String username,password;
    private BigDecimal currentBalance;
    private String CCCD;
    private String OTP;
    private String phoneNumber;
    private String STK;
    public User(int id, String username, String password, BigDecimal currentBalance,String CCCD,String OTP,String phoneNumer,String STK)
    {
        this.id=id;
        this.username=username;
        this.password=password;
        this.currentBalance=currentBalance;
        this.CCCD=CCCD;
        this.OTP=OTP;
        this.phoneNumber=phoneNumer;
        this.STK=STK;
    }

    public String getSTK() {
        return STK;
    }
    public String getCCCD()
    {
        return this.CCCD;
    }
    public String getOTP()
    {
        return this.OTP;
    }
    public int getId()
    {
        return this.id;
    }
    public String getUsername()
    {
        return this.username;
    }
    public String password()
    {
        return this.password;
    }
    public BigDecimal getCurrentBalance()
    {
        return this.currentBalance;
    }
    public void setcurrentBalance(BigDecimal newBalance)
    {
        this.currentBalance=newBalance.setScale(2, RoundingMode.FLOOR);
    }
}
