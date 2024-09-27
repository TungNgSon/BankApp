/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package obj;

import java.math.*;
public class User {
    private int id;
    private String username,password;
    private BigDecimal currentBalance;
    
    public User(int id, String username, String password, BigDecimal currentBalance)
    {
        this.id=id;
        this.username=username;
        this.password=password;
        this.currentBalance=currentBalance;
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
