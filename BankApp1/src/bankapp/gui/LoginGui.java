/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankapp.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import obj.MyJDBC;
import obj.User;


public class LoginGui extends BaseFrame
{
    public LoginGui()
    {
        super("BankAppLogin");
    }
    @Override protected void addGuiComponents()
    {
        //BankAppilication Label
        JLabel bankApplication =new JLabel("Banking Application");
        bankApplication.setBounds(0, 20, super.getWidth(), 40);
        bankApplication.setFont(new Font("Dialog",Font.BOLD,32));
        bankApplication.setHorizontalAlignment(SwingConstants.CENTER);
        add(bankApplication);
        
        //Username lable
        JLabel username = new JLabel("Username:");
        username.setBounds(20, 120, super.getWidth()-30, 30);
        username.setFont(new Font("Dialog",Font.PLAIN,20));
        add(username);
        
        //UsernameField
        JTextField usernameField=new JTextField();
        usernameField.setBounds(20, 160, super.getWidth()-50, 40);
        usernameField.setFont(new Font("Dialog",Font.PLAIN,30));
        add(usernameField);
        
        //Password lable
        JLabel password = new JLabel("Password:");
        password.setBounds(20, 220, super.getWidth()-30, 30);
        password.setFont(new Font("Dialog",Font.PLAIN,20));
        add(password);
        
        //PasswordField
        JPasswordField passwordField=new JPasswordField();
        passwordField.setBounds(20, 260, super.getWidth()-50, 40);
        passwordField.setFont(new Font("Dialog",Font.PLAIN,30));
        add(passwordField);
        
        //Login Button
        JButton loginButton=new JButton("Login");
        loginButton.setBounds(super.getWidth()/2-120,super.getHeight()-200,250, 40);
        loginButton.setFont(new Font("Dialog",Font.BOLD,20));
        loginButton.setForeground(Color.green);
        loginButton.setBackground(Color.black);
        loginButton.addActionListener(new ActionListener()
        {
            @Override public void actionPerformed(ActionEvent e)
            {
                String username=usernameField.getText();
                String password=String.valueOf(passwordField.getPassword());
                User user =MyJDBC.validLogin(username, password);
                if(user!=null)
                {
                    LoginGui.this.dispose();
                    BankingAppGui bankingapp=new BankingAppGui(user);
                    JOptionPane.showMessageDialog(bankingapp, "Login Succesfully !");
                    bankingapp.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(LoginGui.this, "Login Failed !");
                }
            }
        });
        add(loginButton);
        
        //Register Label
        JLabel registerLabel=new JLabel("<html><a href=\"#\">If you do not have account, click here! ");
        registerLabel.setBounds(super.getWidth()/2-180,super.getHeight()-150,super.getWidth(), 40);
        registerLabel.setFont(new Font("Dialog",Font.BOLD,20));
        add(registerLabel);
    }
}
