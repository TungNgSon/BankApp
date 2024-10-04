/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankapp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import obj.MyJDBC;
import obj.Transaction;
import obj.User;
/**
 *
 * @author Moment
 */

public class BankingAppDialog extends JDialog implements ActionListener
{
    private JLabel enterUserLabel;
    private JTextField enterUserField;
    private JLabel balanceLabel;
    private JLabel enterAmountLabel;
    private JTextField enterAmountField ;
    private JButton ActionButton;
    private User user;
    private BankingAppGui bankingAppGui;
    private JPanel pastTransactionPanel;
    private ArrayList<Transaction> pastTransactionList;
    public BankingAppDialog(BankingAppGui bankingAppGui, User user)
    {
        this.setSize(450,450);
        this.setModal(true);
        this.setLocationRelativeTo(bankingAppGui);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.bankingAppGui=bankingAppGui;
        this.user=user;
    }
    public void addCurrentBalanceandAmount()
    {
        // balance label
        balanceLabel = new JLabel("Balance: $" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);
        
         // enter amount label
        enterAmountLabel = new JLabel("Enter Amount:");
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        // enter amount field
        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 50, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountField);
        
    }
    public void addActionButton(String ActionType)
    {
        ActionButton = new JButton(ActionType);
        ActionButton.setBounds(15, 300, getWidth() - 50, 40);
        ActionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        ActionButton.setHorizontalAlignment(SwingConstants.CENTER);
        ActionButton.addActionListener(this);
        add(ActionButton);
    }
    public void addUserField()
    {
        //Enter User Label
        enterUserLabel = new JLabel("Enter User:");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);
        
        //Enter User Field;
        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserField);
    }
    public void addPastTransactionComponents()
    {
        // container where we will store each transaction
        pastTransactionPanel = new JPanel();

        // make layout 1x1
        pastTransactionPanel.setLayout(new BoxLayout(pastTransactionPanel, BoxLayout.Y_AXIS));

        // add scrollability to the container
        JScrollPane scrollPane = new JScrollPane(pastTransactionPanel);

        // displays the vertical scroll only when it is required
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 80);

        // perform db call to retrieve all of the past transaction and store into array list
        pastTransactionList = MyJDBC.getPastTransaction(user);

        // iterate through the list and add to the gui
        for(int i = 0; i < pastTransactionList.size(); i++){
            // store current transaction
            Transaction pastTransaction = pastTransactionList.get(i);

            // create a container to store an individual transaction
            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());

            // create transaction type label
            JLabel transactionTypeLabel = new JLabel(pastTransaction.getTrans_type());
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // create transaction amount label
            JLabel transactionAmountLabel = new JLabel(String.valueOf(pastTransaction.getTrans_amount()));
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // create transaction date label
            JLabel transactionDateLabel = new JLabel(String.valueOf(pastTransaction.getTrans_date()));
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // add to the container
            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST); // place this on the west side
            pastTransactionContainer.add(transactionAmountLabel, BorderLayout.EAST); // place this on the east side
            pastTransactionContainer.add(transactionDateLabel, BorderLayout.SOUTH); // place this on the south side

            // give a white background to each container
            pastTransactionContainer.setBackground(Color.WHITE);

            // give a black border to each transaction container
            pastTransactionContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // add transaction component to the transaction panel
            pastTransactionPanel.add(pastTransactionContainer);
        }

        // add to the dialog
        add(scrollPane);
    }
    public void handleTransaction(String trans_type,float amount)
    {
        Transaction transaction;
        if(trans_type.equals("Deposit"))
        {
            //add money to current balance
            user.setcurrentBalance(user.getCurrentBalance().add(new BigDecimal(amount)));
            transaction =new Transaction(user.getId(),trans_type,new BigDecimal(amount),null);
        }
        else
        {
            //case Withdraw so we subtract money
            user.setcurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amount)));
            //set the amount negative to show sign of withdraw
            transaction =new Transaction(user.getId(),trans_type,new BigDecimal(-amount),null);
        }
        if(MyJDBC.addTransaction(transaction)&&MyJDBC.updateBalance(user))
        {
            JOptionPane.showMessageDialog(this, trans_type+" Successfully");
            resetFieldandUpdate();
        }
        else
        {
            JOptionPane.showMessageDialog(this, trans_type+" Failed");
        }
    }
    public void resetFieldandUpdate()
    {
        //reset amount and user field
        enterAmountField.setText("");
        if(enterUserField!=null)
        {
            enterUserField.setText("");
        }
        
        //update balance
        balanceLabel.setText("Balance: $" + user.getCurrentBalance());
        bankingAppGui.getCurrentBalanceField().setText("Balance: $" + user.getCurrentBalance());
    }
    public void handleTransfer(User user,String transferUser,float amount)
    {
        if(MyJDBC.transfer(user, transferUser, amount))
        {
            JOptionPane.showMessageDialog(this, "Transfers Successfully");
            resetFieldandUpdate();
        }
        else
        {
             JOptionPane.showMessageDialog(this, "Transfers Failed");
        }
        
    }
    @Override public void actionPerformed(ActionEvent e)
    {
        String buttonPressed=e.getActionCommand();
        float amount=Float.parseFloat(enterAmountField.getText());
        if(buttonPressed.equals("Deposit"))
        {
            handleTransaction(buttonPressed,amount);
            resetFieldandUpdate();
        }
        else
        {
            //check if the current balance less than amount value
            int cmp=user.getCurrentBalance().compareTo(BigDecimal.valueOf(amount));
            if(cmp==-1)
            {
                JOptionPane.showMessageDialog(this, "Error: Current Balance is less than amount money");
                return;
            }
            if(buttonPressed.equals("Withdraw"))
            {
                handleTransaction(buttonPressed,amount);
            }
            else
            {
                String transferUser=enterUserField.getText();
                handleTransfer(user,transferUser,amount);
            }
            
            
        }
    }
    
}
