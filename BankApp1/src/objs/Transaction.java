/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objs;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Moment
 */
public class Transaction {
    private int UserId;
    private String trans_type;
    private BigDecimal trans_amount;
    private Date trans_date;
    public Transaction(int UserId,String trans_type,BigDecimal trans_amount,Date trans_date)
    {
        this.UserId=UserId;
        this.trans_amount=trans_amount;
        this.trans_type=trans_type;
        this.trans_date=trans_date;
    }

    public int getUserId() {
        return UserId;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public BigDecimal getTrans_amount() {
        return trans_amount;
    }

    public Date getTrans_date() {
        return trans_date;
    }
}
